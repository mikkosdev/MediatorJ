package org.mediatorj.exception;

import org.mediatorj.IRequest;

/**
 * Unchecked exception for cases where a request was sent but no handler was found.
 *
 * @see org.mediatorj.IRequest
 * @see org.mediatorj.Handler
 */
public class MissingHandlerException extends RuntimeException {

    public MissingHandlerException(Class clazz) {
        super("No handler found for request type: " + clazz);
    }
}
