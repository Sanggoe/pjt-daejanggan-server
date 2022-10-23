package com.sanggoe.pjtdaejanggan.entity;

import lombok.*;

import javax.persistence.*;

@Entity // DB와 1대1 매핑되는 객체를 특함
@Table(name = "verses") // 테이블명 'verses'로 지정하기 위해.
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Verse {

    @Id
    @Column(name = "key_id") // 자동 증가되는 primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    @Column(name = "chapverse", length = 50)
    private String chapverse;

    @Column(name = "theme", length = 50)
    private String theme;

    @Column(name = "head", length = 50)
    private String head;

    @Column(name = "subhead")
    private String subhead;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;
}