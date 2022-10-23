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
    private String title;

    @NotNull
    private String chapterName;

    @NotNull
    private String chapter;

    @NotNull
    private String verse;
}

/*
  title: inputTitle,
      chapterName: inputChapterName,
      chapter: inputChapter,
      verse: inputVerse,
*/