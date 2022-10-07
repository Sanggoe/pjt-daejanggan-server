package com.sanggoe.pjtdaejanggan.controller;

import com.sanggoe.pjtdaejanggan.dto.LoginInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class ExampleController {

    private List<LoginInfo> list = new ArrayList<LoginInfo>();
    private List<String> items = new ArrayList<>();
    private LoginInfo loginInfo = new LoginInfo("sanggoe", "1234");
    private int num1 = 0;
    private int num2 = 0;
    private int num3 = 0;


    public ExampleController() {
        items.add("LOA");
        items.add("LOC");
        items.add("60구절");
        items.add("DEP");
        items.add("180");
        items.add("OYO");

        list.add(new LoginInfo("sanggoe", "1234"));
        list.add(new LoginInfo("erjin", "4567"));
    }


    @PostMapping("/getObj")
    public LoginInfo getObj() {
        System.out.println("getObj 호출 되었음" + num1);
        num1++;
        return loginInfo;
    }

    @PostMapping("/getList")
    public List<LoginInfo> getList() {
        System.out.println("getList 호출 되었음" + num2);
        num2++;
        return list;
    }

    @PostMapping("/loginData/add")
    public void dataAdd(@RequestBody LoginInfo loginInfo) {
        System.out.println("react >> add");
        System.out.println(loginInfo);
        list.add(loginInfo);
    }

    @GetMapping("/menu")
    public List<String> test() {
        System.out.println("/menu 호출 되었음" + num3);
        num3++;
        return items;
    }
}