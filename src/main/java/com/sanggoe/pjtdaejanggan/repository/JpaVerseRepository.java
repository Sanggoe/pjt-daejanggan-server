package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.Verse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaVerseRepository implements VerseRepository {

    private final EntityManager em;

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
    public Optional<List<Verse>> findByChapverseWithTheme(String chapverse, String theme) {
        return Optional.ofNullable(em.createQuery("select v from Verse v where v.chapverse = :chapverse and v.theme = :theme", Verse.class)
                .setParameter("chapverse", chapverse)
                .setParameter("theme", theme)
                .getResultList());
    }
}