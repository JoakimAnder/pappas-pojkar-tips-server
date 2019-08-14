package com.pappaspojkar.tips.Wrappers;

public class RequestQuery<E> extends Query<E> {

    private RequestHead head;

    public RequestQuery() {
    }

    public RequestQuery(RequestHead head, E data) {
        super(data);
        this.head = head;
    }

    public <F> ResponseQuery<F> createSuccessfulResponseQuery(String token, F data) {
        return createResponseQuery(200, "Successful", true, token, data);
    }
    public <F> ResponseQuery<F> createSuccessfulResponseQuery(F data) {
        String token = "";
        if (this.head != null && this.head.getToken() != null)
            token = this.head.getToken();

        return createSuccessfulResponseQuery(token, data);
    }
    public <F> ResponseQuery<F> createResponseQuery(
            int statusCode,
            String message,
            boolean successful,
            String token,
            F data) {
        return new ResponseQuery<>(
                new ResponseHead(
                        statusCode,
                        message,
                        successful,
                        token
                ),
                data
        );
    }

    public <F> ResponseQuery<F> createResponseQuery(
            int statusCode,
            String message,
            boolean successful,
            F data) {
        String token = "";
        if (this.head != null && this.head.getToken() != null)
            token = this.head.getToken();

        return createResponseQuery(
                statusCode,
                message,
                successful,
                token,
                data
        );
    }

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }
}
