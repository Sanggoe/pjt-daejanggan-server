package com.sanggoe.pjtdaejanggan.controller;

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

@CrossOrigin(origins = "http://fg.nh.myds.me", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    //    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
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

//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')") // PreAuthorize 어노테이션을 통해서, ADMIN 권한만 호출할 수 있는 API이다.
//    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
//    }
}