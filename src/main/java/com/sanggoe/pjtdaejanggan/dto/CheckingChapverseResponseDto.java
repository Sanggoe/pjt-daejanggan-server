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
    private String correctTitle;

    @NotNull
    private boolean inputTitleIsCorrect;

    @NotNull
    private String correctChapterName;

    @NotNull
    private boolean inputChapterNameIsCorrect;

    @NotNull
    private String correctChapter;

    @NotNull
    private boolean inputChapterIsCorrect;

    @NotNull
    private String correctVerse;

    @NotNull
    private boolean inputVerseIsCorrect;

    @NotNull
    private int currentMinus;

    @NotNull
    private int currentScore;
}

/*

 */