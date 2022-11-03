package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

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
    private String subhead; // DB 찾기용

    @NotNull
    private String inputTitle;

    @NotNull
    private String inputContents;

    @NotNull
    private ArrayList<Integer> hintIndexes;

    @NotNull
    private ArrayList<Integer> verifiedIndexes;

    @NotNull
    private int currentHint;

    @NotNull
    private int currentMinus;

    @NotNull
    private int currentScore;
}