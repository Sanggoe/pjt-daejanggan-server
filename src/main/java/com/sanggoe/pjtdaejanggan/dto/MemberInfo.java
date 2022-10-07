package com.sanggoe.pjtdaejanggan.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfo {
    private String id;
    private String password;
    private String name;
    private int gender; // 1:man, 2:woman
    private int group; // id로 그룹 구분?

    @Override
    public String toString() {
        return "MemberInfo{\n" +
                "id:'" + id +
                ",\npassword:'" + password +
                ",\nname:'" + name +
                ",\ngender:" + gender +
                ",\ngroup:" + group +
                "\n}";
    }

    @Builder
    public MemberInfo(String id, String password, String name, int gender, int group) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.group = group;
    }
}
