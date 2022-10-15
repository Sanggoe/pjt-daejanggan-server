package com.sanggoe.pjtdaejanggan.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {  // AccessDeniedHandler를 implements !!!
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 필요한 권한이 존재하지 않는데 접근하려 할 때 403 Forbidden Error를 return하기 위한 클래스
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}