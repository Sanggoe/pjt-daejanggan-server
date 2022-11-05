package com.sanggoe.pjtdaejanggan.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    // implements 해서 아래 메서드 오버라이딩 한 이유는 빈이 생성 되고,
    // 주입 받은 후 secret 값을 Base64 Decode 해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // Authentication 객체에 포함되어 있는 권한 정보를 담은 토큰을 생성하는 메서드
    public String createToken(Authentication authentication) { //인증 객체를 파라미터로 받아서
        String authorities = authentication.getAuthorities().stream() // 권한들
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds); // properties에서 설정한 토큰 만료시간을 설정

        return Jwts.builder()   // JWT 토큰을 생성해서 return
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    // 토큰을 파라미터로 받아 토큰에 담겨있는 권한 정보를 이용해 인증 객체를 return하는 메서드
    public Authentication getAuthentication(String token) { // token을 파라미터로 받아 클레임 만들어줌
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // 클레임을 이용해서 User 객체 만들고 최종적으로 인증객체를 리턴
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // token을 파라미터로 받아 토큰의 유효성을 검사해주는 메서드
    public boolean validateToken(String token) {
        try {   // 파싱해보고
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;    // 나오는 Exception들을 캐치 해보고 문제가 없으면 true를 return
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info(">>>>>>> 잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info(">>>>>>> 만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info(">>>>>>> 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info(">>>>>>> JWT 토큰이 잘못되었습니다.");
        }
        return false;   // 문제가 있으면 false를 return
    }
}