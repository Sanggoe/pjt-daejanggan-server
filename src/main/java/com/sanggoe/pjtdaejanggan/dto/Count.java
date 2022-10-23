package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Count {

    @NotNull
    private int chapterNums;

    @NotNull
    private int contentsNums;
}
