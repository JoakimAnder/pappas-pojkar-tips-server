package com.pappaspojkar.tips.wrapper;

public class ResponseQuery<E> extends Query<E> {
    private ResponseHead head;

    public ResponseQuery() {
    }

    public ResponseQuery(E data, ResponseHead head) {
        super(data);
        this.head = head;
    }
}
