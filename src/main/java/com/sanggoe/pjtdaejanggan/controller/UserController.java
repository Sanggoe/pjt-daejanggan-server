package com.sanggoe.pjtdaejanggan.controller;

import com.sanggoe.pjtdaejanggan.dto.CheckingContentsRequestDto;
import com.sanggoe.pjtdaejanggan.dto.CheckingChapverseRequestDto;
import com.sanggoe.pjtdaejanggan.dto.*;
import com.sanggoe.pjtdaejanggan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// @CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ***************************** Test Requests *****************************
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }
    // **************************************************************************

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

    // UserDto 객체를 파라미터로 받아서 userService의 changePassword 메서드를 수행
    @PostMapping("/change-password")
    public ResponseEntity<Integer> changePassword(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.changePassword(userDto));
    }

    // 선택한 headListDto 객체를 파라미터로 받아 해당하는 Verse들을 DB에서 꺼내 반환하는 getVersesForPractice 메서드 실행
    @PostMapping("/practice-verses")
    public ResponseEntity<PracticeResponseDto> getPracticeVerses(@Valid @RequestBody PracticeRequestDto practiceRequestDto) {
        return ResponseEntity.ok(userService.getPracticeVerses(practiceRequestDto));
    }

    // CheckingInfoResponseDto 객체를 파라미터로 받아서 userService의 getCheckingVerses 메서드를 수행한다
    // 점검 정보를 request로 받아 점검 구절 선별 결과를 response로 보내는 것
    @PostMapping("/checking-verses")
    public ResponseEntity<CheckingInfoResponseDto> getCheckingVerses(@Valid @RequestBody CheckingInfoRequestDto checkingInfoRequestDto) {
        return ResponseEntity.ok(userService.getCheckingVerses(checkingInfoRequestDto));
    }


    // CheckingChapverseRequestDto 객체를 파라미터로 받아서 userService의 getChapterCheckingResult 메서드를 수행한다
    // 장절 입력 정보를 request로 받아 정답 채점 결과를 response로 보내는 것
    @PostMapping("/checking-chapverse")
    public ResponseEntity<CheckingChapverseResponseDto> getChapverseCheckingResult(@Valid @RequestBody CheckingChapverseRequestDto checkingChapverseRequestDto) {
        return ResponseEntity.ok(userService.getChapverseCheckingResult(checkingChapverseRequestDto));
    }

    // CheckingContentsRequestDto 객체를 파라미터로 받아서 userService의 getChapterCheckingResult 메서드를 수행한다
    // 내용 입력 정보를 request로 받아 정답 채점 결과를 response로 보내는 것
    @PostMapping("/checking-contents")
    public ResponseEntity<CheckingContentsResponseDto> getContentCheckingResult(@Valid @RequestBody CheckingContentsRequestDto checkingContentsRequestDto) {
        return ResponseEntity.ok(userService.getContentCheckingResult(checkingContentsRequestDto));
    }

    @PostMapping("/checking-hint")
    public ResponseEntity<CheckingContentsResponseDto> getHintResult(@Valid @RequestBody CheckingContentsRequestDto checkingContentsRequestDto) {
        if (checkingContentsRequestDto.getCurrentHint() == 9) {
            return ResponseEntity.ok(userService.getContentCheckingResult(checkingContentsRequestDto));
        }
        return ResponseEntity.ok(userService.getHintResult(checkingContentsRequestDto));
    }

    // 좀 마무리 되면... '결과'를 전부 서버로 보내 DB에 저장하는 request mapping 도 필요함.

//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')") // PreAuthorize 어노테이션을 통해서, ADMIN 권한만 호출할 수 있는 API이다.
//    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
//    }
}