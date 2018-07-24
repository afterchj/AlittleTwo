package com.example.after.alittletwo.entity;

/**
 * Created by hongjian.chen on 2018/6/13.
 */

public enum Constant {
    HEAD("http://www.uichange.com/ums3-share/user/icon.png"),
    WEB_SSM("http://192.168.51.75:8080/web-ssm/file/"),
    UMS3_CLIENT2("http://www.uichange.com/ums3-client2/heads/");

    private String baseUrl;

    Constant(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}
