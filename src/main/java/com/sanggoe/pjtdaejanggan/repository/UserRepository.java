package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User 엔티티에 매핑되는 User 리포지토리. 나중에 필요하면 mapper
public interface UserRepository extends JpaRepository<User, Long> {
    // JPA 리포지토리를 상속받아 findAll이나 save같은 메서드 들을 기본적으로 사용할 수 있다.

    @EntityGraph(attributePaths = "authorities")
    // 위 어노테이션은, 쿼리 수행 시 Lazy 조회가 아닌 Eager 조회로 즉시 수행.
    // 되도록 Lazy 조회를 사용하는 편이 낫다고는 하는데.. 지금은 많은 데이터 호출이 아니니까 상관 없을 듯.
    Optional<User> findOneWithAuthoritiesByUsername(String username); // username기준으로, user 정보를 가져올 때 권한 정보도 같이 가져온다.

    Optional<User> findVersesWithAuthoritiesByUsername(String username); // username기준으로, user 정보를 가져올 때 권한 정보도 같이 가져온다.

}