package com.pappaspojkar.tips.Wrappers;

public class ResponseQuery<E> extends Query<E> {

    private ResponseHead head;

    public ResponseQuery() {
    }

    public ResponseQuery(ResponseHead head, E data) {
        super(data);
        this.head = head;
    }

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }
}
