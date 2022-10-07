package com.sanggoe.pjtdaejanggan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {
    private String id;
    private String password;

    public LoginInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginInfo{\n" +
                "id:" + id +
                ",\npassword:" + password +
                "\n}";
    }
}
