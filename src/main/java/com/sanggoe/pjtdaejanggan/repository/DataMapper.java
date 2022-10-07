package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.dto.VerseInfo;

import java.util.List;

public interface DataMapper {

    void upsertVerse(VerseInfo verseInfo);

    List<VerseInfo> findByTheme(String theme);

    List<VerseInfo> findByHead(String head);
}
