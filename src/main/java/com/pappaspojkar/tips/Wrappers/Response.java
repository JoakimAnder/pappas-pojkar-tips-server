package com.pappaspojkar.tips.Wrappers;

public class Response<E> {

    /** Constructor of new Response object
     *
     * @param data
     * @return A new Response object with a header of statusCode: 200, message:"Success", successful: true. Data is given.
     */
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

    /** Constructor of new Response object
     *
     * @param statusCode statusCode to be given to Head.
     * @param message message to be given to Head.
     * @param successful successful to be given to Head.
     * @param data is given.
     * @return A new Response object with a new Head with given fields.
     */
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
