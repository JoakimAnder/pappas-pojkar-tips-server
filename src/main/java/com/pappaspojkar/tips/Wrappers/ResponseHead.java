package com.pappaspojkar.tips.Wrappers;

public class ResponseHead extends Head {

    private Integer statusCode;
    private String message;

    public ResponseHead() {
    }

    public ResponseHead(Integer statusCode, String message, String token) {
        super(token);
        this.statusCode = statusCode;
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public ResponseHead setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseHead setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public ResponseHead setToken(String token) {
        super.setToken(token);
        return this;
    }

    
    
}