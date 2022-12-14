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
public class CheckingInfoResponseDto {

    @NotNull
    private List<CurrentVerseDto> verses = new ArrayList<>();

    public static CheckingInfoResponseDto from(List<Verse> verseList, int chapverseNum) {
        if (verseList == null)
            return null;

        int[] index = {0};
        List<CurrentVerseDto> versesDto = (List<CurrentVerseDto>) verseList.stream()
                .map(verse -> CurrentVerseDto.builder()
                        .index(index[0])
                        .verseType(index[0]++ < chapverseNum ? "장절" : "내용")
                        .chapverse(verse.getChapverse())
                        .theme(verse.getTheme())
                        .head(verse.getHead())
                        .subhead(verse.getSubhead())
                        .title(verse.getTitle())
                        .contents(verse.getContents())
                        .build())
                .collect(Collectors.toList());

        return CheckingInfoResponseDto.builder()
                .verses(versesDto).build();
    }
}