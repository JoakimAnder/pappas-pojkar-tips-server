package com.pappaspojkar.tips.Wrappers;

public class Response<E> {

    public static <F> Response<F> createSuccessfulResponse(F data) {
        return new Response<>(
                new ResponseHead(
                        200,
                        "Success",
                        true
                ),
                data
        );
    }
    public static <F> Response<F> createResponse(int statusCode, String message, boolean successful, F data) {
        return new Response<>(
                new ResponseHead(
                        statusCode,
                        message,
                        successful
                ),
                data
        );
    }

    private ResponseHead head;
    private E data;

    private Response() {
    }

    private Response(ResponseHead head, E data) {
        this.data = data;
        this.head = head;
    }

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
