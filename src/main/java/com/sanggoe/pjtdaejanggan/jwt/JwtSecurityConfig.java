package com.sanggoe.pjtdaejanggan.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    // SecurityConfigAdapter를 상속받는다.
    private TokenProvider tokenProvider;

    public JwtSecurityConfig(TokenProvider tokenProvider) { // TokenProvider를 DI받는다.
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) { // configure 메서드 오버라이드 해서,
        http.addFilterBefore(   // JwtFilter를 Security 로직에 필터로 등록하는 역할을 한다!!
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}