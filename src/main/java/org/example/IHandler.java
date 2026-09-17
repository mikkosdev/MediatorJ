package org.example;

/**
 * Every handler class must implement this interface.
 *
 * @param <T>
 * @see Handler
 */
public interface IHandler<T extends IRequest, U> {

    U handle(T request);
}


