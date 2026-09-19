package org.mediatorj.exception;

import org.mediatorj.IRequest;

/**
 * Unchecked exception for cases where a request was sent but no handler was found.
 *
 * @see org.mediatorj.IRequest
 * @see org.mediatorj.Handler
 */
public class MissingHandlerException extends RuntimeException {
    /**
     * The request object for which no handler was found.
     */
    public IRequest request;

    public MissingHandlerException(IRequest request) {
        super("No handler found for request type: " + request.getClass());
        this.request = request;
    }
}
