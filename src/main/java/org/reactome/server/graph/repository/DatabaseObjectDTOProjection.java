package org.reactome.server.graph.repository;

public class DatabaseObjectDTOProjection<T> {
    private T m;
    private int n;

    public T getM() {
        return m;
    }

    public void setM(T m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

