package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentVerseDto {

    @NotNull
    private int index;

    @NotNull
    private int verseType;

    @NotNull
    private String chapverse;

    @NotNull
    private String theme;

    @NotNull
    private String head;

    @NotNull
    private String subhead;

    @NotNull
    private String title;

    @NotNull
    private String contents;

}
