package com.pappaspojkar.tips.wrapper;

public class RequestHead {

    private Integer userId;
    private String token;


    public RequestHead(Integer userId, String token) {
        this.token = token;
        this.userId = userId;

    }

    public RequestHead() {
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
