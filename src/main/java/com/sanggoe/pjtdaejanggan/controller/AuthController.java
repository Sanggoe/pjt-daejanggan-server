package com.sanggoe.pjtdaejanggan.controller;

import com.sanggoe.pjtdaejanggan.dto.LoginDto;
import com.sanggoe.pjtdaejanggan.dto.TokenDto;
import com.sanggoe.pjtdaejanggan.jwt.JwtFilter;
import com.sanggoe.pjtdaejanggan.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// 마지막으로, API 호출에 대해 다뤄줄 컨트롤러!!
// @CrossOrigin // 개발하는 로컬 와이파이 변경시 반드시 변경!! 배포할 때도 변경
@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // tokenProvider와 AuthenticationManagerBuilder를 주입받는다.
    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate") // 로그인 API는 post 요청으로 path는 "/api/authenticate" 지정
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) { // LoginDto의 username, password로 파라미터 받고

        UsernamePasswordAuthenticationToken authenticationToken = // username, password 파라미터를 통해서 authenticationToken 객체를 생성!!
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authenticationToken을 이용해서 authenticate 메서드가 실행이 될 때 CustomUserDetailsService에서 오버라이드 한 loadUserByUsername이 실행된다.
        // 실행 된 결과값을 가지고 인증 객체를 생성하게 된다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 그 객체를 Security context에 저장한다.

        String jwt = tokenProvider.createToken(authentication);
        // 그 저장된 인증 정보를 기준으로 해서 tokenProvider의 createToken 메서드로 jwt 토큰을 생성하게 된다!!

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt); // jwt 토큰을 Response Header에도 넣어주고..

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
        // 토큰 DTO를 이용해 Response Body에도 넣어 return한다.
    }
}