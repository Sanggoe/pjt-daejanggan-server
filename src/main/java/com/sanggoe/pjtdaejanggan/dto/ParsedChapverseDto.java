package com.sanggoe.pjtdaejanggan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParsedChapverseDto {

    private String correctChapterName;
    private String correctChapter;
    private String correctVerse;
}
