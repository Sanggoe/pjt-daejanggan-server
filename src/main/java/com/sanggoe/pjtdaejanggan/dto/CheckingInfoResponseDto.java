package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingInfoResponseDto {
    /**
     * 수정 중.. 어떻게 해야 할까요??
     */

    @NotNull
    private List<CurrentVerseDto> verses = new ArrayList<>();

}