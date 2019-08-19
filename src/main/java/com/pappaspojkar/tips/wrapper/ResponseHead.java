package com.pappaspojkar.tips.wrapper;

public class ResponseHead {

    private Integer  statusCode;
    private boolean successful;
    private String message;

    public ResponseHead( Integer statusCode, String message, boolean successful) {

        this.statusCode = statusCode;
        this.message = message;
        this.successful = successful;
    }

    public ResponseHead() {
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
