package org.example.exception;

import org.example.IRequest;

/**
 * Exception class for cases where a request was sent but no handler was found.
 * 
 * @see org.example.IRequest
 * @see org.example.Handler
 */
public class HandlerNotFoundException extends Exception {
    /**
     * The request object for which no handler was found.
     */
    public IRequest request;

    public HandlerNotFoundException(IRequest request) {
        super("No handler found for request type: " + request.getClass());
        this.request = request;
    }
}
