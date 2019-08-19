package com.pappaspojkar.tips.wrapper;

public class Query<E> {

    private E data;

    public Query(E data) {
        this.data = data;
    }

    public Query() {
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
