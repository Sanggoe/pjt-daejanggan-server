package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weight {

    @NotNull
    private String weightType;

    @NotNull
    private int in73ChapterNums;

    @NotNull
    private int in73ContentsNums;

    @NotNull
    private int out73ChapterNums;

    @NotNull
    private int out73ContentsNums;
}
