package org.mikkosdev.mediatorj.exception;

import org.mikkosdev.mediatorj.IRequest;
import org.mikkosdev.mediatorj.Handler;

/**
 * Unchecked exception for cases where a request was sent but no handler was found.
 *
 * @see IRequest
 * @see Handler
 */
public class MissingHandlerException extends RuntimeException {

    public MissingHandlerException(Class clazz) {
        super("No handler found for request type: " + clazz);
    }
}
