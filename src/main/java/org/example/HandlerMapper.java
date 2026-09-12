package org.example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class HandlerMapper<T extends IHandler> {
    private Class<T> handler;

    public HandlerMapper(Class<T> handler) {
        this.handler = handler;
    }

    public Class<T> getClazz() {
        return handler;
    }
}
