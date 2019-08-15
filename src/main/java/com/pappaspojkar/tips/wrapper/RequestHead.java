package com.pappaspojkar.tips;

public class RequestHead extends Head{

    private Integer userId;
    private String token;

    public RequestHead(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
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
