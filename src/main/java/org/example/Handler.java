package org.example;

/**
 * This abstract class is used as superclass for all those classes that handle requests.
 *
 * @param <T>
 * @see org.example.IRequest
 */
public abstract class Handler<T> implements IHandler {
    private final Class<T> clazz;

    public Handler(Class<T> request) {
        this.clazz = request;
    }

    public Class<T> getClazz() {
        return this.clazz;
    }
}
