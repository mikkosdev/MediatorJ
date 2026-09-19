package org.mediatorj.exception;

import org.mediatorj.IRequest;

/**
 * Unchecked exception for cases where a handler for a request type was already registered.
 *
 * @see org.mediatorj.IRequest
 * @see org.mediatorj.Handler
 */
public class DuplicateHandlerException extends RuntimeException {
    public DuplicateHandlerException(Class clazz) {
        super("Handler already registered for type: " + clazz);
    }
}
