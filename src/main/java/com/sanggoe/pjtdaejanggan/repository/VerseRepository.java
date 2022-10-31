package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.Verse;

import java.util.List;
import java.util.Optional;


public interface VerseRepository {

    Verse save(Verse verse);

    Optional<List<Verse>> findByTheme(String theme);

    Optional<List<Verse>> findByHead(List<String> head);

    Optional<List<Verse>> findSomeByHead(List<String> head, int n);

    Optional<Verse> findByChapverseWithThemeAndSubhead(String chapverse, String theme, String subhead);
}
