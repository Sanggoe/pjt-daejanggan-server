package com.sanggoe.pjtdaejanggan.service;

import com.sanggoe.pjtdaejanggan.entity.User;
import com.sanggoe.pjtdaejanggan.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    // UserDetailsService를 implements 하여 UserRepo를 DI 받는다.
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) { // 오버라이드 해서, 로그인 시 DB에서 유저정보와 권한정보를 가져오는 역할
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // DB에서 가져온 정보를 기반으로..
    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) { // 해당 유저가 활성화 상태라면
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream() // 그 유저의 권한 정보들하고
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), // username, password를 가지고 user 객체를 return 해주게 된다.
                grantedAuthorities);
    }
}