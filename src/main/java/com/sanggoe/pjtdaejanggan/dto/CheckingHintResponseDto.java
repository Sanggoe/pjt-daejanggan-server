package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingHintResponseDto {

    @NotNull
    private String mode;

    @NotNull
    private String correctTitle;

    @NotNull
    private boolean inputTitleIsCorrect;

    @NotNull
    private String correctContents;

    @NotNull
    private String inputContents;

    @NotNull
    private List<Integer> hintIndexes;

    @NotNull
    private List<Integer> verifiedIndexes;

    @NotNull
    private int currentHint;

    @NotNull
    private int currentMinus;

    @NotNull
    private int currentScore;
}
