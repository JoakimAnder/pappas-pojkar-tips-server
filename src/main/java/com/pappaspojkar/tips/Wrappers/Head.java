package com.pappaspojkar.tips.Wrappers;

public class Head {

    private String token;

    public Head() {
    }

    public Head(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Head setToken(String token) {
        this.token = token;
        return this;
    }

}