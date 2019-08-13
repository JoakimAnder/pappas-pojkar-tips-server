package com.pappaspojkar.tips.Wrappers;

public class RequestHead extends Head {

    private Integer userId;

    public RequestHead() {
    }

    public RequestHead(String token, Integer userId) {
        super(token);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public RequestHead setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public RequestHead setToken(String token) {
        super.setToken(token);
        return this;
    }
    


    

    
}