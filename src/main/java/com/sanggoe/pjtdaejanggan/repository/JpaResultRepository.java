package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.CheckRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaResultRepository implements ResultRepository {

    private final EntityManager em;
//    private static final Logger logger = LoggerFactory.getLogger(JpaVerseRepository.class);

    public JpaResultRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public CheckRecord save(CheckRecord checkRecord) {
        em.persist(checkRecord);
        return checkRecord;
    }

    @Override
    public Optional<List<CheckRecord>> findByUsername(String username) {
        return Optional.ofNullable(em.createQuery("select cr from CheckRecord cr where cr.username in (:username)", CheckRecord.class)
                .setParameter("username", username)
                .getResultList());
    }
}
