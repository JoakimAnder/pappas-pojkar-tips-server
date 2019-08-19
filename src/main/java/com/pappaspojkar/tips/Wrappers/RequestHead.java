package com.pappaspojkar.tips.Wrappers;

public class RequestHead {

    private Integer userId;
    private String token;

    public RequestHead() {
    }

    public RequestHead(String token, Integer userId) {
        this.token = token;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}