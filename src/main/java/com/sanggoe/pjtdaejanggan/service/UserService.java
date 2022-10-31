package com.sanggoe.pjtdaejanggan.service;

import com.sanggoe.pjtdaejanggan.dto.*;
import com.sanggoe.pjtdaejanggan.entity.Authority;
import com.sanggoe.pjtdaejanggan.entity.User;
import com.sanggoe.pjtdaejanggan.entity.Verse;
import com.sanggoe.pjtdaejanggan.exception.DuplicateMemberException;
import com.sanggoe.pjtdaejanggan.exception.NotFoundMemberException;
import com.sanggoe.pjtdaejanggan.exception.NotFountVerseException;
import com.sanggoe.pjtdaejanggan.repository.JpaUserRepository;
import com.sanggoe.pjtdaejanggan.repository.JpaVerseRepository;
import com.sanggoe.pjtdaejanggan.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// 회원가입 및 유저 정보 조회 등의 기능을 위한 클래스
@Service
public class UserService {
    private final JpaUserRepository userRepository;
    private final JpaVerseRepository verseRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // userRepository와 passwordEncoder를 주입받는다.
    public UserService(JpaUserRepository userRepository, JpaVerseRepository verseRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.verseRepository = verseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 가입을 위한 메서드
    @Transactional
    public UserDto signup(UserDto userDto) { // 파라미터로 받은 UserDto 안에, username기준으로 DB에 존재여부를 먼저 찾아본다.
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다."); // 있으면 Exception 발생
        }

        Authority authority = Authority.builder() // DB에 존재하지 않으면 권한 정보를 만들고
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder() // 권한 정보를 포함한 User 정보도 만들어서
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user)); // userRepository 내부의 save를 통해 user정보와 권한 정보를 DB에 저장

        // 여기서 생각할 점은, 이렇게 회원 가입한 계정은 'User' 권한만 가지고 있고,
        // DB 생성시 처음에 만들어진 Admin 계정은 'User'와 'Admin' 권한을 두 개 가지고 있다.
        // 추후 권한 검증 추가 가능.
    }


    // 비밀번호 변경을 위한 메서드
    @Transactional
    public int changePassword(UserDto userDto) { // 파라미터로 받은 UserDto 안에, username기준으로 DB에 존재여부를 먼저 찾아본다.
        User user = userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null);

        if (user != null) {
            User newUser = User.builder() // 기존 정보에 password만 바꿔서 다시 User 정보를 생성
                    .username(user.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .nickname(user.getNickname())
                    .authorities(user.getAuthorities())
                    .activated(true)
                    .build();

            return userRepository.update(newUser); // userRepository 내부의 save를 통해 user정보와 권한 정보를 DB에 update
        }
        throw new NotFoundMemberException("존재하지 않는 유저입니다."); // 있으면 Exception 발생
    }

    // username을 파라미터로 받아서 어떤 username이든 그에 해당하는 user객체와 권한 정보를 가져올 수 있는 메서드. 사용 안하는 중
    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    // 현재 Security context에 저장되어있는 username에 해당하는 user 정보와 권한 정보를 받아갈 수 있는 메서드
    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
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

    // 내용 점검 정답을 비교하는 메서드
    private CheckingContentsResponseDto compareContentsAnswer(CheckingContentsRequestDto input, Verse correct) {
        boolean correctTitle = compareTitle(input.getInputTitle(), correct.getTitle());

        logger.debug(">>>>>>>>>>>>>> request contents");
        logger.debug(input.getInputContents());

        String correctContents = compareContents(input.getInputContents(), correct.getContents());
        return CheckingContentsResponseDto.builder() // 임시 더미데이터 반환
                .mode("result")
                .correctTitle(correct.getTitle())
                .inputTitleIsCorrect(correctTitle) // boolean
                .correctContents(correct.getContents())
                .inputContents(input.getInputContents())
                .hintIndexes(input.getHintIndexes())
                .currentHint(input.getCurrentHint())
                .currentMinus(input.getCurrentMinus())
                .currentScore(10 - input.getCurrentMinus())
                .build();
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


    // 정규식으로 parsing 하여 제목을 비교한 결과를 반환하는 메서드
    private boolean compareTitle(String input, String correct) {
        return input.replaceAll("[0-9. ]", "").equals(correct.replaceAll("[0-9. ]", ""));
    }

    // 정규식으로 parsing 하여 성경이름을 비교한 결과를 반환하는 메서드
    private boolean compareChapterName(String input, String correct) {
        return input.replace(" ", "").equals(correct);
    }

    // 정규식으로 parsing 하여 장을 비교한 결과를 반환하는 메서드
    private boolean compareChapter(String input, String correct) {
        return input.replaceAll("[장편]", "").equals(correct);
    }

    // 정규식으로 parsing 하여 절을 비교한 결과를 반환하는 메서드
    private boolean compareVerse(String input, String correct) {
        return input.replaceAll("[-~, 반절]", "").equals(correct.replaceAll("[~, ]", ""));
    }

    // 성경장절 정보를 성경이름, 장, 절로 token 분리한 결과 정보인 ParsedChapverseDto 객체를 반환하는 메서드
    private ParsedChapverseDto parsingChapverse(String str) {
        String[] correct = str.split("[ :]");

        return ParsedChapverseDto.builder()
                .correctChapterName(correct[0])
                .correctChapter(correct[1])
                .correctVerse(correct[2])
                .build();
    }

    // 내용 점검에 대한 메인 로직 메서드!!
    private String compareContents(String input, String correct) {
        /***************************************************/

        /***************************************************/
        return null; //Arrays.stream(split).collect(Collectors.toList()).subList(0, 3).toString();
    }
}