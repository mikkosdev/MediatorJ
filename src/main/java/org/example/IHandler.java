package org.example;

public interface IHandler<T extends IRequest> {

    public void handle(T request);
}


