package com.sanggoe.pjtdaejanggan.service;

import com.sanggoe.pjtdaejanggan.dto.UserDto;
import com.sanggoe.pjtdaejanggan.entity.Authority;
import com.sanggoe.pjtdaejanggan.entity.User;
import com.sanggoe.pjtdaejanggan.exception.DuplicateMemberException;
import com.sanggoe.pjtdaejanggan.exception.NotFoundMemberException;
import com.sanggoe.pjtdaejanggan.repository.UserRepository;
import com.sanggoe.pjtdaejanggan.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

// 회원가입 및 유저 정보 조회 등의 기능을 위한 클래스
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // userRepository와 passwordEncoder를 주입받는다.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 가입을 위한 메서드
    @Transactional
    public UserDto signup(UserDto userDto) { // 파라미터로 받은 UserDto 안에, username기준으로 DB에 존재여부를 먼저 찾아본다.
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다."); // 있으면 Exception 발생
        }

        Authority authority = Authority.builder() // DB에 존재하지 않으면 권한 정보를 만들고
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder() // 권한 정보를 포함한 User 정보도 만들어서
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user)); // userRepository 내부의 save를 통해 user정보와 권한 정보를 DB에 저장

        // 여기서 생각할 점은, 이렇게 회원 가입한 계정은 'User' 권한만 가지고 있고,
        // DB 생성시 처음에 만들어진 Admin 계정은 'User'와 'Admin' 권한을 두 개 가지고 있다.
        // 추후 권한 검증 추가 가능.
    }


    // 비밀번호 변경을 위한 메서드
    @Transactional
    public UserDto changePassword(UserDto userDto) { // 파라미터로 받은 UserDto 안에, username기준으로 DB에 존재여부를 먼저 찾아본다.
        User user = userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null);

        if (user != null) {
            user = User.builder() // 권한 정보를 포함한 User 정보도 만들어서
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();

            return UserDto.from(userRepository.save(user)); // userRepository 내부의 save를 통해 user정보와 권한 정보를 DB에 update
        }
        throw new NotFoundMemberException("존재하지 않는 유저입니다."); // 있으면 Exception 발생
    }


    // username을 파라미터로 받아서 어떤 username이든 그에 해당하는 user객체와 권한 정보를 가져올 수 있는 메서드
    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    // 현재 Security context에 저장되어있는 username에 해당하는 user 정보와 권한 정보를 받아갈 수 있는 메서드
    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}

// 위 두 메서드의 허용 권한을 다르게 하여 권한검증을 다루는 것!!