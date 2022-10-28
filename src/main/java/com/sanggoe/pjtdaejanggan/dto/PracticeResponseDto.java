package com.sanggoe.pjtdaejanggan.dto;

import com.sanggoe.pjtdaejanggan.entity.Verse;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PracticeResponseDto {

    @NotNull
    private List<VerseDto> verses;

    public static PracticeResponseDto from(List<Verse> verseList) {
        if (verseList == null)
            return null;

        List<VerseDto> versesDto = verseList.stream()
                .map(verse -> VerseDto.builder()
                        .chapverse(verse.getChapverse())
                        .theme(verse.getTheme())
                        .head(verse.getHead())
                        .subhead(verse.getSubhead())
                        .title(verse.getTitle())
                        .contents(verse.getContents())
                        .build())
                .collect(Collectors.toList());

        return PracticeResponseDto.builder()
                .verses(versesDto).build();
    }
}
