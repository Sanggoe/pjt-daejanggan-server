package com.sanggoe.pjtdaejanggan.dto;

import com.sanggoe.pjtdaejanggan.entity.Verse;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PracticeResponseDto {

    @NotNull
    private List<VerseDto> verses = new ArrayList<>();

    public static PracticeResponseDto from(List<Verse> verses) {
        if (verses == null)
            return null;

        List<VerseDto> versesDto = new ArrayList<>();
        verses.stream().map(verseDto -> {
            return versesDto.add(VerseDto.builder()
                    .chapverse(verseDto.getChapverse())
                    .theme(verseDto.getTheme())
                    .head(verseDto.getHead())
                    .subhead(verseDto.getSubhead())
                    .title(verseDto.getTitle())
                    .contents(verseDto.getContents())
                    .build()
            );
        });

        return PracticeResponseDto.builder().verses(versesDto).build();
    }
}
