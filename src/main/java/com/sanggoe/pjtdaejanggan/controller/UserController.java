package com.sanggoe.pjtdaejanggan.controller;

import com.sanggoe.pjtdaejanggan.dto.*;
import com.sanggoe.pjtdaejanggan.entity.User;
import com.sanggoe.pjtdaejanggan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

// @CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*********************************/
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
    /*********************************/

    /*********************************/
    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }
    /*********************************/

    // UserDto 객체를 파라미터로 받아서 userService의 signup 메서드를 수행한다
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // UserDto 객체를 파라미터로 받아서 userService의 changePassword 메서드를 수행한다
    @PostMapping("/change-password") // 아직 수정 요함. insert 말고 Update 할 방법 찾기
    public ResponseEntity<UserDto> changePassword(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.changePassword(userDto));
    }


    // CheckingInfoDto 객체를 파라미터로 받아서 userService의 getCheckingVerses 메서드를 수행한다
    // 점검 정보를 request로 받아 점검 구절 선별 결과를 response로 보내는 것
    @PostMapping("/checking-verses")
    public ResponseEntity<Object> getCheckingVerses(@Valid @RequestBody CheckingInfoDto checkingInfoDto) {
        return null;
        // ResponseEntity.ok(userService.getCheckingVerses() Service에서 구현해야 할 듯 .get());
        // 그리고 get 함수는 CheckingVersesDto 형태의 JSON 데이터를 반환함
    }

    // ChapterVerseCheckingDto 객체를 파라미터로 받아서 userService의 getChapterCheckingResult 메서드를 수행한다
    // 장절 입력 정보를 request로 받아 정답 채점 결과를 response로 보내는 것
    @PostMapping("/chapter-checking")
    public ResponseEntity<Object> getChapterCheckingResult(@Valid @RequestBody ChapterVerseCheckingDto chapterVerseCheckingDto) {
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


    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')") // PreAuthorize 어노테이션을 통해서, 두 권한을 모두 호출할 수 있는 API이다.
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')") // PreAuthorize 어노테이션을 통해서, ADMIN 권한만 호출할 수 있는 API이다.
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}