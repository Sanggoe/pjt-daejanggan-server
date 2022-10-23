package com.sanggoe.pjtdaejanggan.dto;

import com.sanggoe.pjtdaejanggan.entity.Verse;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PracticeResponseDto { /** 수정 중.. 어떻게 해야 할까요?? */

    @NotNull
    private List<VerseDto> verses = new ArrayList<>();

    public static PracticeResponseDto from(Verse verse) {
//        if (verse == null)
            return null;
//         return PracticeResponseDto.builder()
//                 .verse(verse.getVerses().stream()
//                         .map.build()).collect(Collectors.toSet())).build();

        //return UserDto.builder()
        //       .authorityDtoSet(user.getAuthorities().stream()
        //          .map(authority -> AuthorityDto.builder()
        //          .authorityName(authority.getAuthorityName()).build())
        //          .collect(Collectors.toSet()))
        //        .build();
    }
}
