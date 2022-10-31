package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCheckingResultDto {

    @Column(name = "username")
    private String username;

    @Column(name = "check_time")
    private String check_time;

    @Column(name = "count_total")
    private int count_total;

    @Column(name = "count_selected")
    private int count_selected;

    @Column(name = "score_total")
    private int score_total;

    @Column(name = "score_transform")
    private float score_transform;

    @Column(name = "check_chapverses")
    private String check_chapverses; // |로 구분

}
