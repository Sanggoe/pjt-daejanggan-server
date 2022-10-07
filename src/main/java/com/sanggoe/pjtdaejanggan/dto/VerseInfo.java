package com.sanggoe.pjtdaejanggan.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerseInfo {
    private int id;
    private String chapverses;
    private String theme;
    private String head;
    private String subhead;
    private String title;
    private String contents;

    @Override // 수정 필요
    public String toString() {
        return "VerseInfo {\n" +
                "id:" + id +
                ",\nchapverses:'" + chapverses +
                ",\ntheme:'" + theme +
                ",\nhead:'" + head +
                ",\nsubhead:'" + subhead +
                ",\ntitle:'" + title +
                ",\ncontents:'" + contents +
                "\n}";
    }

    @Builder
    public VerseInfo(int id, String chapverses, String theme, String head, String subhead, String title, String contents) {
        this.id = id;
        this.chapverses = chapverses;
        this.theme = theme;
        this.head = head;
        this.subhead = subhead;
        this.title = title;
        this.contents = contents;
    }
}