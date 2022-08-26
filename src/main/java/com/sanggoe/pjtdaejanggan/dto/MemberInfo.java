package com.sanggoe.pjtdaejanggan.dto;

public class MemberInfo {
    private String id;
    private String pw;
    private String name;
    private int gender; // 1:man, 2:woman
    private int group; // id로 그룹 구분?

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "MemberInfo [" + "id='" + id + ", pw='" + pw + ", name='" + name + ", gender=" + gender + ", group=" + group + "]";
    }
}
