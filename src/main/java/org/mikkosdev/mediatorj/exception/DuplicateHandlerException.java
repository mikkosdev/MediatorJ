package org.mikkosdev.mediatorj.exception;

import org.mikkosdev.mediatorj.IRequest;
import org.mikkosdev.mediatorj.Handler;

/**
 * Unchecked exception for cases where a handler for a request type was already registered.
 *
 * @see IRequest
 * @see Handler
 */
public class DuplicateHandlerException extends RuntimeException {

    public DuplicateHandlerException(Class clazz) {
        super("Handler already registered for type: " + clazz);
    }
}
