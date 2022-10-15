package com.sanggoe.pjtdaejanggan.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {  // GenericFilterBean을 상속 받아서 doFilter를 오버라이드 한다.

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private TokenProvider tokenProvider;
    public JwtFilter(TokenProvider tokenProvider) {    // 이전에 만들었던 토큰 Provider를 DI 받는다.
        this.tokenProvider = tokenProvider;
    }

    // 실제 필터링 로직이 수행되는 부분이 바로 doFilter.
    // 역할은, JWT 토큰의 인증 정보를 현재 실행중인 SecurityContext에 저장하는 역할을 수행한다.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);  // Request에서 토큰을 받아서 jwt에 임시로 저장하고
        String requestURI = httpServletRequest.getRequestURI();

        /*********************************/
        logger.debug("servletRequest : " + servletRequest);
        logger.debug("jwt : " + jwt);
        logger.debug("requestURI" + requestURI);
        /*********************************/

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { // 이 jwt를 유효성 검사를 하고, 정상적이면
            Authentication authentication = tokenProvider.getAuthentication(jwt); // 객체를 받아와서
            SecurityContextHolder.getContext().setAuthentication(authentication); // Security Context에 저장해준다.
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 필터링을 위해서는 토큰 정보가 필요하다!!
    // request의 헤더에서 토큰 정보를 꺼내오는 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        /*********************************/
        logger.debug("bearerToken : " + bearerToken);
        /*********************************/
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}