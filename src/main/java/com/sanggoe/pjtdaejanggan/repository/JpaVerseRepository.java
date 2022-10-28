package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.Verse;
import com.sanggoe.pjtdaejanggan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaVerseRepository implements VerseRepository {

    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(JpaVerseRepository.class);


    public JpaVerseRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Verse save(Verse verse) {
        em.persist(verse);
        return verse;
    }

    @Override
    public Optional<List<Verse>> findByTheme(String theme) {
        return Optional.ofNullable(em.createQuery("select v from Verse v where v.theme = :theme", Verse.class)
                .setParameter("theme", theme)
                .getResultList());
    }

    @Override
    public Optional<List<Verse>> findByHead(List<String> head) {
        return Optional.ofNullable(em.createQuery("select v from Verse v where v.head in (:head)", Verse.class)
                .setParameter("head", head)
                .getResultList());
    }

    @Override
    public Optional<List<Verse>> findSomeByHead(List<String> head, int n) {
        return Optional.ofNullable(em.createNativeQuery("select * from verses where head in (:head) order by RAND() limit :n", Verse.class)
                .setParameter("head", head)
                .setParameter("n", n)
                .getResultList());
        // n개
    }

    @Override
    public Optional<Verse> findByChapverseWithThemeAndSubhead(String chapverse, String theme, String subhead) {
        return Optional.ofNullable(em.createQuery("select v from Verse v where v.chapverse = :chapverse and v.theme = :theme and v.subhead = :subhead", Verse.class)
                .setParameter("chapverse", chapverse)
                .setParameter("theme", theme)
                .setParameter("subhead", subhead)
                .getSingleResult());
    }
}