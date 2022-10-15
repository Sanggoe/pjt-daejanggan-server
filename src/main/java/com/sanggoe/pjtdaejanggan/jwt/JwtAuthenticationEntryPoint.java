package com.sanggoe.pjtdaejanggan.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증 토큰 만료 및 잘못된 토큰 접근 시도 등 유효한 자격 증명을 제공하지 않고 접근을 시도할 때 401 Unauthorized Error를 return할 클래스
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // AuthenticationEntryPoint를 상속 받아서,
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        // 인증되지 않은 사용자가 접근을 시도할 때 401 Unauthorized error를 응답으로 보내는 클래스
    }
}