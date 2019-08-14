package com.pappaspojkar.tips.Wrappers;

public class Query<E> {

    private E data;

    public Query() {}

    public Query(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    
}