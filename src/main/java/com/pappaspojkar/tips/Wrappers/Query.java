package com.pappaspojkar.tips.Wrappers;

public class Query<E> {

    private Head head;
    private E data;

    public Query() {}
    public Query(Head head) {
        this.head = head;
        this.data = null;
    }
    public Query(Head head, E data) {
        this.head = head;
        this.data = data;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    
}