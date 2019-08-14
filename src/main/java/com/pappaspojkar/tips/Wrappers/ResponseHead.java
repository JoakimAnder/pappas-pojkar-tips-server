package com.pappaspojkar.tips.Wrappers;

public class ResponseHead extends Head {

    private Integer statusCode;
    private Boolean successful;
    private String message;

    public ResponseHead() {
    }

    public ResponseHead(Integer statusCode, String message, boolean successful, String token) {
        super(token);
        this.statusCode = statusCode;
        this.message = message;
        this.successful = successful;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
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