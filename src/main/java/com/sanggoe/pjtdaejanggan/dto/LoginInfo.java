package com.sanggoe.pjtdaejanggan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {
    private String id;
    private String pw;

    public LoginInfo(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "LoginInfo [" + "id='" + id + ", pw='" + pw + "]";
    }
}
