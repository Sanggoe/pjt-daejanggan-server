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
public class CheckingInfoRequestDto {

    @NotNull
    private String checkingType;

    @NotNull
    private List<String> headList = new ArrayList<>();

    @NotNull
    private String orderType;

    @NotNull
    private String verseType;

    @NotNull
    private Count count;

    @NotNull
    private Weight weight;
}
