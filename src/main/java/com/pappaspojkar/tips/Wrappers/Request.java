package com.pappaspojkar.tips.Wrappers;

public class Request<E> {

    private RequestHead head;
    private E data;

    public Request() {
    }

    public Request(RequestHead head, E data) {
        this.data = data;
        this.head = head;
    }

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
