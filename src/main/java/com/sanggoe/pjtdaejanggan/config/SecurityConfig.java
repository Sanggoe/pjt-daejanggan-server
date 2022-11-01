package com.sanggoe.pjtdaejanggan.config;

import com.sanggoe.pjtdaejanggan.jwt.JwtAccessDeniedHandler;
import com.sanggoe.pjtdaejanggan.jwt.JwtAuthenticationEntryPoint;
import com.sanggoe.pjtdaejanggan.jwt.JwtSecurityConfig;
import com.sanggoe.pjtdaejanggan.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // PreAuthorize라고 하는 어노테이션을 메서드 단위로 사용하기 위해
public class SecurityConfig {
    /*

    원래는 WebSecurityConfigurerAdapter를 상속받아서 구현해야 하는데, 스프링 2.7 ver 이후로는 사용을 중단하게 되었다.
    보안 설정으로 변경된다는 이슈로..

    대신에 WebSecurity를 파라미터로 받는 configure 메서드를 오버라이드 하는 대신에 webSecurityCustomizer()로,
    HttpSecurity를 파라미터로 받는 configure 메서드를 오버라이드 하는 대신에 filterChain 메서드로 하여 각각 Bean에 등록하고
    build()해서 반환하는 형태로 바뀌게 되었다.

    */
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // 401 Unauthorized Error
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler; // 403 Forbidden Error

    public SecurityConfig( // 토큰 제공자, 유효하지 않은 자격증명 접근 401 Error, 권한이 없는 403 Error 반환 위한 객체들 DI
                           TokenProvider tokenProvider,
                           CorsFilter corsFilter,
                           JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                           JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 패스워드 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(); // "/h2-console/**", "/favicon.ico", "/error");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable한다. (사이트 간 요청 위조 (Cross-site request forgery))
                .csrf().disable()

                // cors 옵션 활성화를 위한 코드
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic().disable()
                .cors()

                // 예외처리 핸들러는 직접 만들어준 핸들러로 설정
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 401 Unauthorized Error
                .accessDeniedHandler(jwtAccessDeniedHandler) // 403 Forbidden Error

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 토큰을 받기 위한 로그인 API, 회원가입 API는 토큰이 없는 상태에서 들어오기 때문에 모두 접근 가능으로 바꿔준다.
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/authenticate").permitAll()
                .antMatchers("/api/user/signup").permitAll()
                .antMatchers("/api/user/hello").permitAll()
                .anyRequest().authenticated() // 그 외 요청은 모두 인증이 되어야 가능

                // jwt 필터를 addFilterBefore로 등록했던 Jwt Security config 클래스에 토큰 제공자를 주입시켜 적용!!
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }
}
