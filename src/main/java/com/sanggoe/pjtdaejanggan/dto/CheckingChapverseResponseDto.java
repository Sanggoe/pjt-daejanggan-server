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
    private boolean correctTitle;

    @NotNull
    private String resultChapterName;

    @NotNull
    private boolean correctChapterName;

    @NotNull
    private String resultChapter;

    @NotNull
    private boolean correctChapter;

    @NotNull
    private String resultVerse;

    @NotNull
    private boolean correctVerse;
}

/*

 */