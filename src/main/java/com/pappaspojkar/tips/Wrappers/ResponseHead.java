package com.pappaspojkar.tips.Wrappers;

public class ResponseHead {

    private Integer statusCode;
    private Boolean successful;
    private String message;

    public ResponseHead() {
    }

    public ResponseHead(Integer statusCode, String message, boolean successful) {
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