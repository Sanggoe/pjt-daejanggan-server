package com.sanggoe.pjtdaejanggan.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "check_record") // 테이블명 'check_record'로 지정하기 위해.
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckRecord {

    @Id
    @Column(name = "key_id") // 자동 증가되는 primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "check_time", length = 50)
    private String check_time;

    @Column(name = "count_total", length = 10)
    private int count_total;

    @Column(name = "count_selected", length = 10)
    private int count_selected;

    @Column(name = "score_total", length = 10)
    private int score_total;

    @Column(name = "score_transform", length = 10)
    private float score_transform;

    @Column(name = "check_chapverses")
    private String check_chapverses; // |로 구분
}