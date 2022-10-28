package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    int update(User user);

    Optional<User> findOneWithAuthoritiesByUsername(String username);
}