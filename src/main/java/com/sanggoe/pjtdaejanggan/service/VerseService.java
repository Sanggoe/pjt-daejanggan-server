package com.sanggoe.pjtdaejanggan.service;

import com.sanggoe.pjtdaejanggan.dto.*;
import com.sanggoe.pjtdaejanggan.entity.Verse;
import com.sanggoe.pjtdaejanggan.exception.NotFountVerseException;
import com.sanggoe.pjtdaejanggan.repository.JpaVerseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VerseService {

    private final JpaVerseRepository verseRepository;
    private static final Logger logger = LoggerFactory.getLogger(VerseService.class);

    public VerseService(JpaVerseRepository verseRepository) {
        this.verseRepository = verseRepository;
    }

    // HeadList 정보를 파라미터로 받아와서 암송 연습을 위한 구절들을 DB에서 찾아 반환하는 메서드
    @Transactional(readOnly = true)
    public PracticeResponseDto getPracticeVerses(PracticeRequestDto practiceRequestDto) {
        List<Verse> verses = verseRepository.findByHead(practiceRequestDto.getHeadList()).orElse(null);
        if (verses == null) {
            throw new NotFountVerseException("존재하지 않는 구절입니다."); // 있으면 Exception 발생
        }
        return PracticeResponseDto.from(verses);
    }

    // Checking Verse 선별 정보를 파라미터로 받아와서 암송 점검을 위한 구절들을 DB에서 찾아 반환하는 메서드
    @Transactional(readOnly = true)
    public CheckingInfoResponseDto getCheckingVerses(CheckingInfoRequestDto checkingInfoRequestDto) {

        String checkingType = checkingInfoRequestDto.getCheckingType();
        List<Verse> verses = new ArrayList<>();
        int chapverseNum = 0;

//        checkingInfoRequestDto.getHeadList().stream().forEach(logger::debug); head 리스트 쫙 출력해보기

        if (checkingType.equals("전체 점검")) {
            verses = verseRepository.findByHead(checkingInfoRequestDto.getHeadList()).orElse(null);

            if (checkingInfoRequestDto.getOrderType().equals("랜덤")) { // 섞기
                Collections.shuffle(verses);
            }
            if (checkingInfoRequestDto.getVerseType().equals("장절")) { // 장절 개수 저장 (나머지는 내용)
                chapverseNum = verses.toArray().length;
            }

        } else if (checkingType.equals("일부 점검")) {
            Count count = checkingInfoRequestDto.getCount();

            verses = verseRepository.findSomeByHead(checkingInfoRequestDto.getHeadList(), count.getChapterNums() + count.getContentsNums()).orElse(null);
            chapverseNum = count.getChapterNums();

        } else if (checkingType.equals("체급별 점검")) {
            int len = checkingInfoRequestDto.getHeadList().toArray().length;
            Weight weight = checkingInfoRequestDto.getWeight();

            if (len == 1) { // 구절수 5개
                verses = verseRepository.findByHead(checkingInfoRequestDto.getHeadList()).orElse(null);
                Collections.shuffle(verses); // 섞기
            } else if (len < 7) { // 총 구절수 73 미만 => 10구절 선택
                verses = verseRepository.findSomeByHead(checkingInfoRequestDto.getHeadList(), weight.getIn73ContentsNums()).orElse(null);
            } else { // 총 구절수 73 이상 => 73이내, 73이외 체급별로 계산해서
                int in73ChapterNums = weight.getIn73ChapterNums();
                int in73ContentsNums = weight.getIn73ContentsNums();
                int out73ChapterNums = weight.getOut73ChapterNums();
                int out73ContentsNums = weight.getOut73ContentsNums();

                List<Verse> versesIn73 = verseRepository.findSomeByHead(checkingInfoRequestDto.getHeadList().subList(0, 7).stream().toList(), in73ChapterNums + in73ContentsNums).orElse(null);
                List<Verse> versesOut73 = null;
                if (out73ChapterNums + out73ContentsNums != 0) {
                    versesOut73 = verseRepository.findSomeByHead(checkingInfoRequestDto.getHeadList().subList(7, len).stream().toList(), out73ChapterNums + out73ContentsNums).orElse(null);
                }
                verses.addAll(versesIn73.subList(0, in73ChapterNums).stream().toList());
                if (versesOut73 != null) {
                    verses.addAll(versesOut73.subList(0, out73ChapterNums).stream().toList());
                }
                verses.addAll(versesIn73.subList(in73ChapterNums, in73ChapterNums + in73ContentsNums).stream().toList());
                if (versesOut73 != null) {
                    verses.addAll(versesOut73.subList(out73ChapterNums, out73ChapterNums + out73ContentsNums).stream().toList());
                }
                chapverseNum = in73ChapterNums + out73ChapterNums;
            }
        } else {
            throw new NullPointerException("점검 방법이 선택되지 않았습니다.");
        }
        if (verses == null) {
            throw new NotFountVerseException("존재하지 않는 구절입니다."); // 있으면 Exception 발생
        }

        return CheckingInfoResponseDto.from(verses, chapverseNum);
    }


    // 장절 점검 요청에 대한 메서드
    @Transactional(readOnly = true)
    public CheckingChapverseResponseDto getChapverseCheckingResult(CheckingChapverseRequestDto checkingChapverseRequestDto) {
        Verse verse = verseRepository.findByChapverseWithThemeAndSubhead(checkingChapverseRequestDto.getChapverse(), checkingChapverseRequestDto.getTheme(), checkingChapverseRequestDto.getSubhead()).orElse(null);
        return compareChapverseAnswer(checkingChapverseRequestDto, verse);
    }

    // 내용 점검 요청에 대한 메서드
    @Transactional(readOnly = true)
    public CheckingContentsResponseDto getContentCheckingResult(CheckingContentsRequestDto checkingContentsRequestDto) {
        Verse verse = verseRepository.findByChapverseWithThemeAndSubhead(checkingContentsRequestDto.getChapverse(), checkingContentsRequestDto.getTheme(), checkingContentsRequestDto.getSubhead()).orElse(null);
        return compareContentsAnswer(checkingContentsRequestDto, verse);
    }

    // 내용 점검의 힌트 요청에 대한 메서드
    @Transactional(readOnly = true)
    public CheckingContentsResponseDto getHintResult(CheckingContentsRequestDto checkingContentsRequestDto) {
        Verse verse = verseRepository.findByChapverseWithThemeAndSubhead(checkingContentsRequestDto.getChapverse(), checkingContentsRequestDto.getTheme(), checkingContentsRequestDto.getSubhead()).orElse(null);
        return compareContentsHint(checkingContentsRequestDto, verse);
    }

    // 장절 점검 정답을 비교하는 메서드
    private CheckingChapverseResponseDto compareChapverseAnswer(CheckingChapverseRequestDto input, Verse correct) {
        ParsedChapverseDto correctChapverse = parsingChapverse(correct.getChapverse());

        boolean[] corrects = new boolean[4];
        corrects[0] = compareTitle(input.getInputTitle(), correct.getTitle());
        corrects[1] = compareChapterName(input.getInputChapterName(), correctChapverse.getCorrectChapterName());
        corrects[2] = compareChapter(input.getInputChapter(), correctChapverse.getCorrectChapter());
        corrects[3] = compareVerse(input.getInputVerse(), correctChapverse.getCorrectVerse());

        int minus = 0;
        for (boolean c : corrects) {
            if (!c) {
                minus++;
            }
        }

        return CheckingChapverseResponseDto.builder()
                .correctTitle(correct.getTitle())
                .inputTitleIsCorrect(corrects[0])
                .correctChapterName(correctChapverse.getCorrectChapterName())
                .inputChapterNameIsCorrect(corrects[1])
                .correctChapter(correctChapverse.getCorrectChapter())
                .inputChapterIsCorrect(corrects[2])
                .correctVerse(correctChapverse.getCorrectVerse())
                .inputVerseIsCorrect(corrects[3])
                .currentMinus(minus)
                .currentScore(10 - minus)
                .build();
    }

    // 내용 점검 정답을 비교하는 메인 로직 메서드
    private CheckingContentsResponseDto compareContentsAnswer(CheckingContentsRequestDto input, Verse correct) {
        final int TOTAL_SCORE = 10;

        String inputTitle = input.getInputTitle();
        String correctTitle = correct.getTitle();
        String inputContent = input.getInputContents();
        String correctContent = correct.getContents();  // 어절633h 어절633h 어절 어절 어절 어절633h .. 인 상태
        List<Integer> hintIndexes = input.getHintIndexes();

        //        int i=0;
//        for (String word: correct.getContents().split(" ")) {
//            if (hintIndexes.indexOf(i) != -1) {
//                correctContent += word + "633h ";
//            } else {
//                correctContent += word + " ";
//            }
//            i++;
//        }

        int hintCnt = input.getCurrentHint();
        int lostPoint = input.getCurrentMinus();
        int resultPoint;

        // 제목 점검 및 결과
        boolean inputTitleIsCorrect = compareTitle(inputTitle, correctTitle);

        // Whole Check - 기본적으로 다 맞춘 경우. (특수문자, 공백, 영문 제거 -> 한글 글자만 점검)
        if (correctContent.replaceAll("[^\\uAC00-\\uD7A3]", "").equals(inputContent.replaceAll("[^\\uAC00-\\uD7A3]", ""))) {

            if (!inputTitleIsCorrect) lostPoint += 1;
            resultPoint = TOTAL_SCORE - lostPoint;

            return CheckingContentsResponseDto.builder() // whole Check 완료 후, 기존 힌트 감점 및 제목 채점결과를 반영하여 결과창으로 return
                    .mode("result")
                    .correctTitle(correctTitle)
                    .inputTitleIsCorrect(inputTitleIsCorrect)
                    .correctContents(correctContent)
                    .inputContents(correctContent) //hint indexes 정보 결합 필요 ?
                    .hintIndexes(input.getHintIndexes())
                    .currentHint(hintCnt)
                    .currentMinus(lostPoint)
                    .currentScore(resultPoint)
                    .build();

        } else { // Part Check - 부분 점검으로 들어가야 하는 경우. 어절점검은 input을 기준으로 correct를 비교

            // Content -> ArrayList 어절별 파싱, 채점용 변수
            List<Word> arrInput = parseToArrlist(inputContent);
            List<Word> arrCorrect = parseToArrlist(correctContent);

            // Pointer init
            int pi = 0; // pointer for arrInput
            int pc = 0; // pointer for arrCorrect

            // 부분채점용 변수
            boolean flagFor2Words = false;        // 두어절 틀리면 true로 변경
            int idxFor2Words = -1;    // 두어절 hint가 제공될 arrCorrect의 index값을 저장 = 양수면 두어절hint가 발생한 것
            int tempLostPoint = lostPoint;        // 부분채점 중 임시로 사용할 감점점수

            // 채점 및 marking
            while (pi < arrInput.size() && pc < arrCorrect.size()) {

                // 어절 비교 맞으면 r로 마킹 후 각 포인터 증가
                if (arrInput.get(pi).getWord().equals(arrCorrect.get(pc).getWord())) {
                    arrInput.get(pi).setMarking('r');
                    arrCorrect.get(pc).setMarking('r');
                    pi++;
                    pc++;
                } else { // 어절 비교 틀리면 띄어쓰기 보정 작업
                    arrInput = spaceRevision(arrCorrect.get(pc).getWord(), arrInput, pi);


                    /*****************************************************
                     * 채점 marking 수행에 대한 부분 정리
                     * 대문자 알파벳 = Input word, 소문자 알파벳 = Correct word 각각의 위치 의미
                     *
                     * mode = check 인 경우를 위해
                     * 두어절 여부를 표기하는 boolean flagFor2Words,
                     * 두어절 h 발생한 위치를 저장하는 idxFor2Words를 별도로 관리해서 불필요한 재점검 없앰
                     *
                     * 기본은 A와 a를 비교하는 것  // (띄어쓰기 보정이 되면서 A=a가 가능성 다시 생김)
                     * B = input word(pi+1)      : 없는 경우 있을 수 있음
                     * b = correct word(pc+1)    : 없는 경우 있을 수 있음
                     *
                     *
                     * B, b 모두 있는 경우 (가장 빈도가 높음)
                     * A  B
                     * a  b
                     * => 맞음r, 틀림w, 누락m, 추가a, 도치i, 두어절h 케이스 가능
                     *
                     * B, b 둘 다 없는 경우
                     * A  [out]
                     * a  [out]
                     * => 맞음r, 틀림w 케이스 가능
                     *
                     * B만 없는 경우
                     * A  [out]
                     * a  b (c  d .....)
                     * => 맞음r, 누락m, 두어절h 케이스 가능
                     * => A=a인 경우, A,a 맞음r
                     * => A!=a && A!=b 인 경우, a를 두어절h 처리
                     * => A!=a && A=b 인 경우, a를 누락m, A, b는 r처리
                     *
                     * b만 없는 경우
                     * A  B
                     * a  [out]
                     * => 맞음r, 틀림w, 추가a 케이스 가능
                     * => A=a인 경우, A,a 맞음r
                     * => A!=a && B=a인 경우, A가 추가a가 됨, B, a는 r처리
                     * => A!=a && B!=a인 경우, A, a가 틀림w
                     *
                     *
                     *****************************************************/


                    boolean existB = true, existb = true;
                    if ((pi + 1) >= arrInput.size()) existB = false;
                    if ((pc + 1) >= arrCorrect.size()) existb = false;

                    if (existB && existb) {
                        logger.debug("B, b 둘 다 있는 경우에 해당할거고..");
                        // B b 모두 있는 경우
                        arrInput = spaceRevision(arrCorrect.get(pc + 1).getWord(), arrInput, pi + 1);

                        boolean Aa = arrInput.get(pi).getWord().equals(arrCorrect.get(pc).getWord());
                        boolean Ab = arrInput.get(pi).getWord().equals(arrCorrect.get(pc + 1).getWord());
                        boolean Ba = arrInput.get(pi + 1).getWord().equals(arrCorrect.get(pc).getWord());
                        boolean Bb = arrInput.get(pi + 1).getWord().equals(arrCorrect.get(pc + 1).getWord());

                        if (Aa) {
                            logger.debug("A랑 a가 맞아요");
                            arrInput.get(pi).setMarking('r');
                            arrCorrect.get(pc).setMarking('r');
                            pi++;
                            pc++;
                        } else if (Bb) {    // 틀림
                            logger.debug("B랑 b가 맞아요. A랑 a는 틀려요");
                            arrInput.get(pi).setMarking('w');
                            arrCorrect.get(pc).setMarking('w');
                            arrInput.get(pi + 1).setMarking('r');
                            arrCorrect.get(pc + 1).setMarking('r');
                            pi++;
                            pc++;
                            pi++;
                            pc++;
                            tempLostPoint++;
                        } else if (Ab && Ba) { // 도치
                            logger.debug("A랑 b가, B랑 a가 맞아요. 도치");
                            arrInput.get(pi).setMarking('i');
                            arrInput.get(pi + 1).setMarking('i');
                            arrCorrect.get(pc).setMarking('i');
                            arrCorrect.get(pc + 1).setMarking('i');
                            pi++;
                            pc++;
                            pi++;
                            pc++;
                            tempLostPoint++;
                        } else if (Ab) { // 누락
                            logger.debug("A랑 b가 맞아요. input에 a가 없네요?");
                            arrInput.get(pi).setMarking('r');
                            arrCorrect.get(pc).setMarking('m');
                            arrCorrect.get(pc + 1).setMarking('r');
                            pi++;
                            pc++;
                            pc++;
                            tempLostPoint++;
                        } else if (Ba) { // 추가
                            logger.debug("B랑 a가 맞아요. A는 왜쓴거죠?");
                            arrInput.get(pi).setMarking('a');
                            arrInput.get(pi + 1).setMarking('r');
                            arrCorrect.get(pc).setMarking('r');
                            pi++;
                            pi++;
                            pc++;
                            tempLostPoint++;
                        } else if (!Aa && !Ab && !Ba && !Bb) { // 두 어절 틀림
                            logger.debug("A,a,B,b 모두 달라요.");
                            arrCorrect.get(pc).setMarking('h');
                            flagFor2Words = true;
                            idxFor2Words = pc;
                            break;
                        } else {
                            // 혹시 예상 못한 케이스가 나올까 싶어 남겨둔 else
                            logger.debug("에러 : ExistBb : 부분채점 예외 발생");
                        }

                    } else if (!existB && !existb) {
                        boolean Aa = arrInput.get(pi).getWord().equals(arrCorrect.get(pc).getWord());
                        if (Aa) {
                            arrInput.get(pi).setMarking('r');
                            arrCorrect.get(pc).setMarking('r');
                            pi++;
                            pc++;
                        } else {
                            arrInput.get(pi).setMarking('w');
                            arrCorrect.get(pc).setMarking('w');
                            pi++;
                            pc++;
                            tempLostPoint++;
                        }

                    } else if (!existB && existb) {
                        boolean Aa = arrInput.get(pi).getWord().equals(arrCorrect.get(pc).getWord());
                        boolean Ab = arrInput.get(pi).getWord().equals(arrCorrect.get(pc + 1).getWord());

                        if (Aa) {
                            arrInput.get(pi).setMarking('r');
                            arrCorrect.get(pc).setMarking('r');
                            pi++;
                            pc++;
                        } else if (Ab) { // 누락
                            arrInput.get(pi).setMarking('r');
                            arrCorrect.get(pc).setMarking('m');
                            arrCorrect.get(pc + 1).setMarking('r');
                            pi++;
                            pc++;
                            pc++;
                            tempLostPoint++;
                        } else {
                            arrCorrect.get(pc).setMarking('h');
                            flagFor2Words = true;
                            idxFor2Words = pc;
                            break;
                        }

                    } else if (existB && !existb) {
                        boolean Aa = arrInput.get(pi).getWord().equals(arrCorrect.get(pc).getWord());
                        boolean Ba = arrInput.get(pi + 1).getWord().equals(arrCorrect.get(pc).getWord());

                        if (Aa) {
                            arrInput.get(pi).setMarking('r');
                            arrCorrect.get(pc).setMarking('r');
                            pi++;
                            pc++;
                        } else if (Ba) { // 추가
                            arrInput.get(pi).setMarking('a');
                            arrInput.get(pi + 1).setMarking('r');
                            arrCorrect.get(pc).setMarking('r');
                            pi++;
                            pi++;
                            pc++;
                            tempLostPoint++;
                        } else { //틀림
                            arrInput.get(pi).setMarking('w');
                            arrCorrect.get(pc).setMarking('w');
                            pi++;
                            pc++;
                            tempLostPoint++;
                        }

                    } else {
                        logger.debug("에러 : existBb 존재여부 분기 예외 발생");
                    }

                } // part 채점 else 끝

            } // 채점 while => pi, pc가 동시에 모두 존재할 때, marking처리가 완료됨


            // 남은 pi, pc의 처리에 대한 부분
            // pi가 남은 경우 : 불필요 데이터이므로 모두 remove
            // pc가 남은 경우 : 남은게 1개 word면 누락m 처리 or 2개 이상이면 첫번째word가 두어절h 처리
            // 두어절 flag가 TRUE면 처리 안해도 됨

            boolean restPi = ((pi + 1) >= arrInput.size()) ? false : true;      // pi가 남은경우 true, 남은게 없으면 false
            boolean restPc = ((pc + 1) >= arrCorrect.size()) ? false : true;    // pc가 남은경우 true, 남은게 없으면 false

            if (restPi && !restPc) {
                while (pi < arrInput.size()) {
                    arrInput.remove(pi);
                    pi++;
                }
            } else if (!restPi && restPc && !flagFor2Words) {
                int gap = arrCorrect.size() - (pc + 1);
                if (gap == 1) {
                    arrCorrect.get(pc).setMarking('m');
                    tempLostPoint++;
                } else if (gap >= 2) {
                    arrCorrect.get(pc).setMarking('h');
                    flagFor2Words = true;
                    idxFor2Words = pc;
                } else {
                    logger.debug("에러 : restPc gap 계산 예외 발생");
                }
            } else {
                logger.debug("에러 : restPi restPc 분기 예외 발생");
            }


            // partCheck return 처리
            // 두어절 flag 확인 후 두어절 처리
            // lostPoint, totalPoint 계산

            if (flagFor2Words && lostPoint <= 8) {
                // check mode로 리턴하며 힌트1개로 인한 1점 감점만 추가됨
                // 제목 채점결과도 반영하지 않음
                // 기존힌트 + 추가한 1개의 힌트
                // 그 사이는 정답을 보장하지 않는 입력문장
                lostPoint++;
                hintCnt++;

                String hintedCorrectContent = "";
                for (Word w : arrCorrect) {
                    hintedCorrectContent = hintedCorrectContent + w.toHintString();
                }

                if (idxFor2Words != -1) input.getHintIndexes().add(Integer.valueOf(idxFor2Words));
                hintIndexes = input.getHintIndexes().stream().distinct().collect(Collectors.toList());
                Collections.sort(hintIndexes);

                return CheckingContentsResponseDto.builder()
                        .mode("check")
                        .correctTitle(correctTitle)
                        .inputTitleIsCorrect(inputTitleIsCorrect)
                        .correctContents(hintedCorrectContent)      // 요걸 어떻게 Return을 해줄것이냐?? 에 대한 부분 좀 더 고민 해보자.
                        .inputContents(inputContent)                // 사실상 사용하지 않을 정보
                        .hintIndexes(hintIndexes)
                        .currentHint(hintCnt)
                        .currentMinus(lostPoint)
                        .currentScore(TOTAL_SCORE)
                        .build();

            } else if (flagFor2Words && lostPoint >= 9) {
                // result mode로 리턴함, h 마킹한 단어를 힌트 1개 추가 제공하면서 10점 됨
                // 제목이 틀린 경우도 반영해줌 -> 반영은 하지만 최대 감점은 10점

                if (lostPoint >= 9) lostPoint = 10;
                hintCnt++;

                String markedCorrectContent = "";
                for (Word w : arrCorrect) {
                    markedCorrectContent = markedCorrectContent + w.toMarkedString();
                }
                String markedInputContent = "";
                for (Word w : arrInput) {
                    markedInputContent = markedInputContent + w.toMarkedString();
                }

                if (idxFor2Words != -1) input.getHintIndexes().add(Integer.valueOf(idxFor2Words));
                hintIndexes = input.getHintIndexes().stream().distinct().collect(Collectors.toList());
                Collections.sort(hintIndexes);

                return CheckingContentsResponseDto.builder()
                        .mode("result")
                        .correctTitle(correctTitle)
                        .inputTitleIsCorrect(inputTitleIsCorrect)
                        .correctContents(markedCorrectContent)
                        .inputContents(markedInputContent)
                        .hintIndexes(input.getHintIndexes())
                        .currentHint(hintCnt)
                        .currentMinus(lostPoint)
                        .currentScore(TOTAL_SCORE - lostPoint)
                        .build();

            } else {
                // 정상적으로 result 창으로 전달되는 경우
                lostPoint = tempLostPoint;

                if (!inputTitleIsCorrect) lostPoint += 1;
                if (lostPoint >= 10) lostPoint = 10;

                String markedCorrectContent = "";
                String markedInputContent = "";
                for (Word w : arrCorrect) {
                    markedCorrectContent = markedCorrectContent + w.toMarkedString();
                }
                for (Word w : arrInput) {
                    markedInputContent = markedInputContent + w.toMarkedString();
                }

                if (idxFor2Words != -1) input.getHintIndexes().add(Integer.valueOf(idxFor2Words));
                hintIndexes = input.getHintIndexes().stream().distinct().collect(Collectors.toList());
                Collections.sort(hintIndexes);

                return CheckingContentsResponseDto.builder()
                        .mode("result")
                        .correctTitle(correctTitle)
                        .inputTitleIsCorrect(inputTitleIsCorrect)
                        .correctContents(markedCorrectContent)
                        .inputContents(markedInputContent)
                        .hintIndexes(hintIndexes)
                        .currentHint(hintCnt)
                        .currentMinus(lostPoint)
                        .currentScore(TOTAL_SCORE - lostPoint)
                        .build();
            }

        } // part Check else
    }

    private List<Word> parseToArrlist(String str) {
        List<Word> arr = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        String word;
        char marking;

        while (st.hasMoreTokens()) {
            word = st.nextToken();
            marking = 'u';
            int checkedIdx = word.indexOf("633");
            if (checkedIdx != -1) {
                marking = word.charAt(checkedIdx + 3);
                word = word.substring(0, checkedIdx).replaceAll("[^\\uAC00-\\uD7A3]", "");
            }
            arr.add(new Word(word, marking));
        }
        return arr;
    }

    // 띄어쓰기 보정 함수
    private List<Word> spaceRevision(String correctWord, List<Word> arrInput, int pi) {
        String word1, word2, word3;
        String inputWord = arrInput.get(pi).getWord();
        int inputWordLength = inputWord.length();
        int correctWordLength = correctWord.length();

        int idxForInput = arrInput.get(pi).getWord().indexOf(correctWord);
        logger.debug(">>>>>>>>> 띄어쓰기 진입");
        logger.debug("inputWord : [" + inputWord + "] 띄어쓰기 점검할 word는 이거구요");
        logger.debug("correctWord : [" + correctWord + "] 띄어쓰기 점검할 word는 이거구요");
        logger.debug("띄어쓰기 추가해야할까? : " + idxForInput + "임.");
        // Case1. 입력에 정답이 포함된 경우 -> 띄어쓰기를 넣어줌
        if (idxForInput != -1) {
            logger.debug("Case1. 띄어쓰기 추가 : " + idxForInput);
            if (idxForInput == 0) {
                // 두 어절이 붙어있는 경우
                // 첫 글자에 정답이 있을 때 정답 어절 길이 만큼 띄어쓰기 해줌
                word1 = inputWord.substring(0, correctWordLength);
                word2 = inputWord.substring(correctWordLength, inputWordLength);
                arrInput.remove(pi);
                arrInput.add(pi, new Word(word2, 'u'));
                arrInput.add(pi, new Word(word1, 'u'));
            } else if (idxForInput + correctWordLength == inputWordLength) {
                // 두 어절이 붙어있는 경우
                // 어절의 뒤쪽에 붙어서 정답이 있을 때 정답 어절 길이 만큼 띄어쓰기 해줌
                word1 = inputWord.substring(0, idxForInput);
                word2 = inputWord.substring(idxForInput, inputWordLength);
                arrInput.remove(pi);
                arrInput.add(pi, new Word(word2, 'u'));
                arrInput.add(pi, new Word(word1, 'u'));
            } else {
                // 세 어절이 붙어있는 경우
                // 중간 어딘가 인덱스에 정답이 끼어 있을 때 정답 존재 인덱스 기준으로 어절 길이 만큼 앞 뒤로 띄어쓰기 해줌
                word1 = inputWord.substring(0, idxForInput);
                word2 = inputWord.substring(idxForInput, idxForInput + correctWordLength);
                word3 = inputWord.substring(idxForInput + correctWordLength, inputWordLength);
                arrInput.remove(pi);
                arrInput.add(pi, new Word(word3, 'u'));
                arrInput.add(pi, new Word(word2, 'u'));
                arrInput.add(pi, new Word(word1, 'u'));
            }

        } else {
            // Case2. 입력을 합치면 정답포함 -> 입력에 띄어쓰기를 없애줌, 최대 띄어쓰기 2개까지 확인
            String tempJoinWord = arrInput.get(pi).getWord();
            int gap = arrInput.size() - (pi + 1); // 뒤에 남은 어절이 몇개인지 확인하는 gap 변수
            if (gap == 0) {
                // input 문장이 끝났으므로 합칠 어절 없음
            } else if (gap == 1) {
                // 1인 경우 1개 어절 합침
                tempJoinWord = tempJoinWord + arrInput.get(pi + 1).getWord();
            } else {
                // 2이상인 경우 2회 까지 합침
                gap = 2;
                for (int i = 1; i < gap + 1; i++) {
                    tempJoinWord = tempJoinWord + arrInput.get(pi + i).getWord();
                }
            }

            idxForInput = tempJoinWord.indexOf(correctWord);
            logger.debug("띄어쓰기 제거해야할까? : " + idxForInput + "임.");

            // tempJoinWord에 CorrectWord가 포함됨 -> 띄어쓰기 넣어서 수정해줌
            if (idxForInput != -1) {
                logger.debug("Case2. 띄어쓰기 제거 : " + idxForInput);
                // 합친 어절 수 만큼 arrInput arrayList에서 제거
                for (int i = 0; i <= gap; i++) {
                    arrInput.remove(pi);
                }

                int tempJoinWordLength = tempJoinWord.length();

                if (idxForInput == 0) {
                    word1 = tempJoinWord.substring(0, correctWordLength);
                    word2 = tempJoinWord.substring(correctWordLength, tempJoinWordLength);
                    arrInput.add(pi, new Word(word2, 'u'));
                    arrInput.add(pi, new Word(word1, 'u'));
                } else if (idxForInput + correctWordLength == tempJoinWordLength) {
                    word1 = tempJoinWord.substring(0, idxForInput);
                    word2 = tempJoinWord.substring(idxForInput, tempJoinWordLength);
                    arrInput.add(pi, new Word(word2, 'u'));
                    arrInput.add(pi, new Word(word1, 'u'));
                } else {
                    word1 = tempJoinWord.substring(0, idxForInput);
                    word2 = tempJoinWord.substring(idxForInput, idxForInput + correctWordLength);
                    word3 = tempJoinWord.substring(idxForInput + correctWordLength, tempJoinWordLength);
                    arrInput.add(pi, new Word(word3, 'u'));
                    arrInput.add(pi, new Word(word2, 'u'));
                    arrInput.add(pi, new Word(word1, 'u'));
                }
            }
        }
        logger.debug(">>>>>>>>> 띄어쓰기 끝");
        return arrInput; // 띄어쓰기 보정 완료 및 return
    }


    // 내용 점검 힌트를 수행하는 메서드
    private CheckingContentsResponseDto compareContentsHint(CheckingContentsRequestDto input, Verse correct) {
        List<String> inputList = Arrays.stream(input.getInputContents().split(" ")).toList();
        List<String> correctList = Arrays.stream(correct.getContents().split(" ")).toList();

        int len = correctList.size();

        if (input.getHintIndexes().indexOf(len - 1) == -1) { // 정답의 마지막 구절까지 힌트가 이미 나간 경우가 아니라면
            int i = 0;

            while (i < len) { // 힌트 인덱스에 이미 나간 힌트의 인덱스가 없고 && (내가 입력한 구절보다 크거나 || 보내온 구절의 지금 인덱스 어절이 정답 어절과 다르면)
                if (input.getHintIndexes().indexOf(i) == -1 && (i >= inputList.size() || !correctList.get(i).equals(inputList.get(i)))) {
                    break;
                }
                i++;
            }
            input.getHintIndexes().add(Integer.valueOf(i));
        }

        List<Integer> hintIndexes = input.getHintIndexes().stream().distinct().collect(Collectors.toList());
        Collections.sort(hintIndexes);

        return CheckingContentsResponseDto.builder() // 임시 더미데이터 반환
                .mode("check")
                .correctTitle(correct.getTitle())
                .inputTitleIsCorrect(false) // boolean
                .correctContents(correct.getContents())
                .inputContents(input.getInputContents()) // 데이터 객체로 차라리 반환하는걸 코드 바꿔보자. 스트링보다 객체가 더 나을듯?
                .hintIndexes(hintIndexes)
                .currentHint(input.getCurrentHint() + 1)
                .currentMinus(input.getCurrentMinus() + 1)
                .currentScore(10 - input.getCurrentMinus())
                .build();
    }


    // 정규식으로 특수문자, 공백, 영문 제거 -> 한글 글자만 남기고 제목을 비교한 결과를 반환하는 메서드
    private boolean compareTitle(String inputTitle, String correctTitle) {
        return inputTitle.replaceAll("[^\\uAC00-\\uD7A3]", "").equals(correctTitle.replaceAll("[^\\uAC00-\\uD7A3]", ""));
    }

    // 정규식으로 parsing 하여 성경이름을 비교한 결과를 반환하는 메서드
    private boolean compareChapterName(String inputChapterName, String correctChapterName) {
        return inputChapterName.replaceAll("[^\\uAC00-\\uD7A3]", "").equals(correctChapterName);
    }

    // 정규식으로 parsing 하여 장을 비교한 결과를 반환하는 메서드
    private boolean compareChapter(String inputChapter, String correctChapter) {
        return inputChapter.replaceAll("[^0-9]", "").equals(correctChapter);
    }

    // 정규식으로 parsing 하여 절을 비교한 결과를 반환하는 메서드
    private boolean compareVerse(String inputVerse, String correctVerse) {
        return inputVerse.replaceAll("[^0-9]", "").equals(correctVerse.replaceAll("[^0-9]", ""));
    }

    // 성경장절 정보를 성경이름, 장, 절로 token 분리한 결과 정보인 ParsedChapverseDto 객체를 반환하는 메서드
    private ParsedChapverseDto parsingChapverse(String str) {
        String[] correctWords = str.split("[ :]");

        return ParsedChapverseDto.builder()
                .correctChapterName(correctWords[0])
                .correctChapter(correctWords[1])
                .correctVerse(correctWords[2])
                .build();
    }

}
