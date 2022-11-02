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

    @Column(name = "username", length = 50) // 유저 이름
    private String username;

    @Column(name = "check_time", length = 50) // 점검 시간
    private String check_time;

    @Column(name = "count_total", length = 10) // 점검 총 구절 수
    private int count_total;

    @Column(name = "count_selected", length = 10) // 선택된 구절 수
    private int count_selected;

    @Column(name = "score_total", length = 10) // 총 점수
    private int score_total;

    @Column(name = "score_transform", length = 10) // 100점 환산 점수
    private float score_transform;

    @Column(name = "check_chapverses", columnDefinition = "TEXT") // 점검한 구절들
    private String check_chapverses; // &로 구분

    @Column(name = "check_type", length = 20) // 점검 방법 - 전체, 일부, 체급
    private String check_type;

    @Column(name = "verse_type", length = 10) // 장절, 내용
    private String verse_type;

}