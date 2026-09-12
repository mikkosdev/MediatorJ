package org.example;

public abstract class Handler<T> implements IHandler {
    private Class<T> clazz;

    public Handler(Class<T> request) {
        this.clazz = request;
    }

    public Class<T> getClazz() {
        return this.clazz;
    }
}
