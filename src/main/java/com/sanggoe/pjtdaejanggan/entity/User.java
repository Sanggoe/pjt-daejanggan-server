package com.sanggoe.pjtdaejanggan.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity // DB와 1대1 매핑되는 객체를 특함
@Table(name = "users")
// 테이블명 'users'로 지정하기 위해. 에러나서 검색해보니 Postgresql 에서 user라는 명칭은 예약어기 때문에 Users로 변경해야한다고 나옴. sql문도 users 테이블로 변경하여 해결하였음
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id") // 자동 증가되는 primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority", // 권한에 대한 관계
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}