package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingContentsRequestDto {

    @NotNull
    private String theme; // DB 찾기용

    @NotNull
    private String chapverse; // DB 찾기용

    @NotNull
    private String title;

    @NotNull
    private String contents;

    @NotNull
    private String hintWord;

    @NotNull
    private int currentHint;

    @NotNull
    private int currentMinus;

    @NotNull
    private int currentScore;
}