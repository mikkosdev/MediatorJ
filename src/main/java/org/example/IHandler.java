package org.example;

/**
 * Every handler class must implement this interface.
 *
 * @param <T>
 * @see Handler
 */
public interface IHandler<T extends IRequest> {

    public void handle(T request);
}


