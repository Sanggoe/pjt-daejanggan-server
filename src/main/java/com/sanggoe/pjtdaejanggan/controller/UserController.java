package com.sanggoe.pjtdaejanggan.controller;

import com.sanggoe.pjtdaejanggan.dto.CheckingChapverseRequestDto;
import com.sanggoe.pjtdaejanggan._will_add_to_dto.ContentVerseCheckingDto;
import com.sanggoe.pjtdaejanggan.dto.*;
import com.sanggoe.pjtdaejanggan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

// @CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * ******** Test Requests ********
     *
     * @GetMapping("/hello") public ResponseEntity<String> hello() {
     * return ResponseEntity.ok("hello");
     * }
     * @PostMapping("/test-redirect") public void testRedirect(HttpServletResponse response) throws IOException {
     * response.sendRedirect("/api/user");
     * }
     * *******************************
     */

    // UserDto 객체를 파라미터로 받아서 userService의 signup 메서드를 수행
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // user 정보를 요청하는 getMyUserInfo 메서드 수행
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')") // PreAuthorize 어노테이션을 통해서, 두 권한을 모두 호출할 수 있는 API이다.
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    /**
     * 아직 수정 요함. insert 말고 Update 할 방법 찾기
     */
    // UserDto 객체를 파라미터로 받아서 userService의 changePassword 메서드를 수행
    @PostMapping("/change-password")
    public ResponseEntity<UserDto> changePassword(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.changePassword(userDto));
    }
    /**
     * **********************************
     */

    // 선택한 headListDto 객체를 파라미터로 받아 해당하는 Verse들을 DB에서 꺼내 반환하는 getVersesForPractice 메서드 실행
    @PostMapping("/practice-verses")
    public ResponseEntity<PracticeResponseDto> getVersesForPractice(@Valid @RequestBody PracticeRequestDto practiceRequestDto) {

        /**
         * 이제 이걸 Service로 구현해야 합니다~~
        */
        VerseDto verseDto1 = VerseDto.builder()
                .chapverse("야고보서 1:19")
                .theme("180")
                .head("2. 사랑 안에서 자라감")
                .subhead("A. 사랑으로 말함")
                .title("5.경청함")
                .contents("내 사랑하는 형제들아 너희가 알거니와 사람마다 듣기는 속히 하고 말하기는 더디 하며 성내기도 더디 하라")
                .build();

        VerseDto verseDto2 = VerseDto.builder()
                .chapverse("요한복음 15:5")
                .theme("DEP")
                .head("2. Quiet Time")
                .subhead("2-1. 왜 Quiet Time을 가져야하는가?")
                .title("3. 축복의 약속")
                .contents("나는 포도나무요 너희는 가지니 저가 내 안에 내가 저 안에 있으면 이 사람은 과실을 많이 맺나니 나를 떠나서는 너희가 아무것도 할 수 없음이라")
                .build();

        VerseDto verseDto3 = VerseDto.builder()
                .chapverse("요한복음 16:24")
                .theme("LOA")
                .head("그리스도인의 확신")
                .subhead("")
                .title("2. 기도응답의 확신")
                .contents("지금까지는 너희가 내 이름으로 아무것도 구하지 아니하였으나 구하라 그리하면 받으리니 너희 기쁨이 충만하리라")
                .build();

        VerseDto verseDto4 = VerseDto.builder()
                .chapverse("로마서 5:8")
                .theme("60구절")
                .head("B - 그리스도를 전파함")
                .subhead("")
                .title("3. 그리스도가 형벌을 받음")
                .contents("우리가 아직 죄인 되었을 때에 그리스도께서 우리를 위하여 죽으심으로 하나님께서 우리에게 대한 자기의 사랑을 확증하셨느니라")
                .build();

        VerseDto verseDto5 = VerseDto.builder()
                .chapverse("베드로전서 2:9")
                .theme("180")
                .head("5. 그리스도를 증거함")
                .subhead("3. 그리스도 안에 있는 신자의 위치")
                .title("6.제사장이 됨")
                .contents("오직 너희는 택하신 족속이요 왕 같은 제사장들이요 거룩한 나라요 그의 소유된 백성이니 이는 너희를 어두운 데서 불러내어 그의 기이한 빛에 들어가게 하신 자의 아름다운 덕을 선전하게 하려 하심이라")
                .build();

        VerseDto verseDto6 = VerseDto.builder()
                .chapverse("요한일서 5:11~12")
                .theme("LOA")
                .head("그리스도인의 확신")
                .subhead("")
                .title("1. 구원의 확신")
                .contents("또 증거는 이것이니 하나님이 우리에게 영생을 주신것과 이 생명이 그의 아들안에 있는 그것이니라 아들이 있는 자 에게는 생명이 있고 하나님의 아들이 없는 자에게는 생명이 없느니라")
                .build();


        List<VerseDto> versesDto = new ArrayList<>();
        versesDto.add(verseDto1);
        versesDto.add(verseDto2);
        versesDto.add(verseDto3);
        versesDto.add(verseDto4);
        versesDto.add(verseDto5);
        versesDto.add(verseDto6);

        return ResponseEntity.ok(PracticeResponseDto.builder().verses(versesDto).build());
//        return ResponseEntity.ok(userService.getVerses(practiceRequestDto));
    }

    // CheckingInfoDto 객체를 파라미터로 받아서 userService의 getCheckingVerses 메서드를 수행한다
    // 점검 정보를 request로 받아 점검 구절 선별 결과를 response로 보내는 것
    @PostMapping("/checking-verses")
    public ResponseEntity<CheckingInfoResponseDto> getCheckingVerses(@Valid @RequestBody CheckingInfoRequestDto checkingInfoRequestDto) {
        /**
         * 이제 이걸 Service로 구현해야 합니다~~
         */
        CurrentVerseDto currentVerseDto1 = CurrentVerseDto.builder()
                .index(0)
                .verseType(1)
                .chapverse("야고보서 1:19")
                .theme("180")
                .head("2. 사랑 안에서 자라감")
                .subhead("A. 사랑으로 말함")
                .title("5.경청함")
                .contents("내 사랑하는 형제들아 너희가 알거니와 사람마다 듣기는 속히 하고 말하기는 더디 하며 성내기도 더디 하라")
                .build();

        CurrentVerseDto currentVerseDto2 = CurrentVerseDto.builder()
                .index(1)
                .verseType(1)
                .chapverse("요한복음 15:5")
                .theme("DEP")
                .head("2. Quiet Time")
                .subhead("2-1. 왜 Quiet Time을 가져야하는가?")
                .title("3. 축복의 약속")
                .contents("나는 포도나무요 너희는 가지니 저가 내 안에 내가 저 안에 있으면 이 사람은 과실을 많이 맺나니 나를 떠나서는 너희가 아무것도 할 수 없음이라")
                .build();

        CurrentVerseDto currentVerseDto3 = CurrentVerseDto.builder()
                .index(2)
                .verseType(0)
                .chapverse("요한복음 16:24")
                .theme("LOA")
                .head("그리스도인의 확신")
                .subhead("")
                .title("2. 기도응답의 확신")
                .contents("지금까지는 너희가 내 이름으로 아무것도 구하지 아니하였으나 구하라 그리하면 받으리니 너희 기쁨이 충만하리라")
                .build();

        CurrentVerseDto currentVerseDto4 = CurrentVerseDto.builder()
                .index(3)
                .verseType(0)
                .chapverse("로마서 5:8")
                .theme("60구절")
                .head("B - 그리스도를 전파함")
                .subhead("")
                .title("3. 그리스도가 형벌을 받음")
                .contents("우리가 아직 죄인 되었을 때에 그리스도께서 우리를 위하여 죽으심으로 하나님께서 우리에게 대한 자기의 사랑을 확증하셨느니라")
                .build();

        CurrentVerseDto currentVerseDto5 = CurrentVerseDto.builder()
                .index(4)
                .verseType(0)
                .chapverse("베드로전서 2:9")
                .theme("180")
                .head("5. 그리스도를 증거함")
                .subhead("3. 그리스도 안에 있는 신자의 위치")
                .title("6.제사장이 됨")
                .contents("오직 너희는 택하신 족속이요 왕 같은 제사장들이요 거룩한 나라요 그의 소유된 백성이니 이는 너희를 어두운 데서 불러내어 그의 기이한 빛에 들어가게 하신 자의 아름다운 덕을 선전하게 하려 하심이라")
                .build();

        CurrentVerseDto currentVerseDto6 = CurrentVerseDto.builder()
                .index(5)
                .verseType(0)
                .chapverse("요한일서 5:11~12")
                .theme("LOA")
                .head("그리스도인의 확신")
                .subhead("")
                .title("1. 구원의 확신")
                .contents("또 증거는 이것이니 하나님이 우리에게 영생을 주신것과 이 생명이 그의 아들안에 있는 그것이니라 아들이 있는 자 에게는 생명이 있고 하나님의 아들이 없는 자에게는 생명이 없느니라")
                .build();


        List<CurrentVerseDto> versesCheckingDto = new ArrayList<>();
        versesCheckingDto.add(currentVerseDto1);
        versesCheckingDto.add(currentVerseDto2);
        versesCheckingDto.add(currentVerseDto3);
        versesCheckingDto.add(currentVerseDto4);
        versesCheckingDto.add(currentVerseDto5);
        versesCheckingDto.add(currentVerseDto6);

        return ResponseEntity.ok(CheckingInfoResponseDto.builder().verses(versesCheckingDto).build());

        // ResponseEntity.ok(userService.getCheckingVerses() Service에서 구현해야 할 듯 .get());
        // 그리고 get 함수는 CheckingVersesDto 형태의 JSON 데이터를 반환함
    }





    // CheckingChapverseRequestDto 객체를 파라미터로 받아서 userService의 getChapterCheckingResult 메서드를 수행한다
    // 장절 입력 정보를 request로 받아 정답 채점 결과를 response로 보내는 것
    @PostMapping("/chapter-checking")
    public ResponseEntity<Object> getChapterCheckingResult(@Valid @RequestBody CheckingChapverseRequestDto checkingChapverseRequestDto) {
        return null;
        // ResponseEntity.ok(userService.getChapterCheckingResult() Service에서 구현해야 할 듯 .get());
        // 그리고 get 함수는 ChapterVerseCheckingResultDto 형태의 JSON 데이터를 반환함
    }

    // ContentVerseCheckingDto 객체를 파라미터로 받아서 userService의 getChapterCheckingResult 메서드를 수행한다
    // 내용 입력 정보를 request로 받아 정답 채점 결과를 response로 보내는 것
    @PostMapping("/content-checking")
    @PreAuthorize("hasAnyRole('USER','ADMIN')") // PreAuthorize 어노테이션을 통해서, 두 권한을 모두 호출할 수 있는 API이다.
    public ResponseEntity<Object> getContentCheckingResult(@Valid @RequestBody ContentVerseCheckingDto contentVerseCheckingDto) {
        return null;
        // ResponseEntity.ok(userService.getContentCheckingResult() Service에서 구현해야 할 듯 .get());
        // 그리고 get 함수는 ContentVerseCheckingResultDto 형태의 JSON 데이터를 반환함
    }

    // 좀 마무리 되면... '결과'를 전부 서버로 보내 DB에 저장하는 request mapping 도 필요함.


    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')") // PreAuthorize 어노테이션을 통해서, ADMIN 권한만 호출할 수 있는 API이다.
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}