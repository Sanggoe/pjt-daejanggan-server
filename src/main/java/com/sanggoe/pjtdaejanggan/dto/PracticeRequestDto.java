package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PracticeRequestDto {

    @NotNull
    private ArrayList<String> headList = new ArrayList<>();

}
