package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingChapverseRequestDto {

    @NotNull
    private String theme; // DB 찾기용

    @NotNull
    private String chapverse; // DB 찾기용

    @NotNull
    private String subhead; // DB 찾기용

    @NotNull
    private String inputTitle;

    @NotNull
    private String inputChapterName;

    @NotNull
    private String inputChapter;

    @NotNull
    private String inputVerse;
}