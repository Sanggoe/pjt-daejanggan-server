package com.sanggoe.pjtdaejanggan.service;

import com.sanggoe.pjtdaejanggan.dto.CheckingChapverseResponseDto;
import com.sanggoe.pjtdaejanggan.dto.ParsedChapverseDto;
import com.sanggoe.pjtdaejanggan.entity.Verse;
import com.sanggoe.pjtdaejanggan.repository.JpaVerseRepository;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private final JpaVerseRepository verseRepository;

    UserServiceTest(JpaVerseRepository verseRepository) {
        this.verseRepository = verseRepository;
    }

    @Test
    CheckingChapverseResponseDto getChapverseCheckingResult() {
        Verse verse = verseRepository.findByChapverseWithThemeAndSubhead("요한일서 5:11~12", "LOA", "").orElse(null);
        String answer = "요한일서 5:11~12";

        return compareChapverseAnswer(answer, verse);
    }

    @Test
    private CheckingChapverseResponseDto compareChapverseAnswer(String answer, Verse result) {
        System.out.println(parsingChapverse());
        return null;
    }

    String str = "요한일서 5:11~12";
    @Test
    private ParsedChapverseDto parsingChapverse() {
        String[] result = "요한일서 5:11~12".split("[ :]");

        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);

        return ParsedChapverseDto.builder()
                .resultChapterName(result[0])
                .resultChapter(result[1])
                .resultVerse(result[2])
                .build();
    }
}