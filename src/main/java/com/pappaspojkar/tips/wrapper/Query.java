package com.pappaspojkar.tips;

import com.pappaspojkar.tips.wrapper.Head;

public class Query {
    private Head head;
    private Data data;


    public Query(Head head) {
        this.head = head;
    }

    public Query() {
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
