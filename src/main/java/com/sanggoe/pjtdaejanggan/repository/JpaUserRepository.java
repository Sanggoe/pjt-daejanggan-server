package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public int update(User user) {
        return em.createQuery("update User u set u.password = :password where u.username = :username")
                .setParameter("username", user.getUsername())
                .setParameter("password", user.getPassword()).executeUpdate();
    }

    @Override
    public Optional<User> findOneWithAuthoritiesByUsername(String username) {
        return Optional.ofNullable(em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList().stream().findAny().orElse(null));
    }
}