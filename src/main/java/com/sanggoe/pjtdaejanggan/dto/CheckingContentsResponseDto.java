package com.sanggoe.pjtdaejanggan.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingContentsResponseDto {

    @NotNull
    private String mode;

    @NotNull
    private String resultTitle;

    @NotNull
    private boolean correctTitle;

    @NotNull
    private String resultContents;

    @NotNull
    private String inputContents;

    @NotNull
    private int hint;

    @NotNull
    private int minus;

    @NotNull
    private int score;
}
/*
    mode: {},
    resultTitle: {},
    isCorrectTitle: {},
    resultContents: {},
    inputContents: {},
    hint: {},
    minus: {},
    score: {},
*/