package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCheckingResultDto {

    @NotNull
    private String username;

    @NotNull
    private String check_time;

    @NotNull
    private int count_total;

    @NotNull
    private int count_selected;

    @NotNull
    private int score_total;

    @NotNull
    private float score_transform;

    @NotNull
    private String check_chapverses; // &로 구분

    @NotNull
    private String check_type;

    @NotNull
    private String verse_type;

}
