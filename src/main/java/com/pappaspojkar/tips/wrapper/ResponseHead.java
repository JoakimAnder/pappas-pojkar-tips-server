package com.pappaspojkar.tips;

public class ResponseHead extends Head{
    private String token;
    private Integer  statusCode;
    private String message;

    public ResponseHead(String token, Integer statusCode, String message) {
        this.token = token;
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseHead() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
