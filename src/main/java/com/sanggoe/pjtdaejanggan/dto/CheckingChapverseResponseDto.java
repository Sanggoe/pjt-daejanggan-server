package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingChapverseResponseDto {

    @NotNull
    private String resultTitle;

    @NotNull
    private boolean isCorrectTitle;

    @NotNull
    private String resultChapterName;

    @NotNull
    private boolean isCorrectChapterName;

    @NotNull
    private String resultChapter;

    @NotNull
    private boolean isCorrectChapter;

    @NotNull
    private String resultVerse;

    @NotNull
    private boolean isCorrectVerse;
}

/*
      title: {
        result: "기도응답의 확신",
        correct: false,
      },
      chapterName: {
        result: "요한복음",
        correct: true,
      },
      chapter: {
        result: 16,
        correct: true,
      },
      verse: {
        result: 24,
        correct: true,
      },
 */