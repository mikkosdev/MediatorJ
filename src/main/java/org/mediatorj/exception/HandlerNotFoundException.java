package org.mediatorj.exception;

import org.mediatorj.IRequest;

/**
 * Exception class for cases where a request was sent but no handler was found.
 *
 * @see org.mediatorj.IRequest
 * @see org.mediatorj.Handler
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
