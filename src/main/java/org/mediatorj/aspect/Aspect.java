package org.mediatorj.aspect;

import org.mediatorj.IRequest;

/**
 * Aspect represent cross-cutting concerns in an application that are run every time a request is handled.
 * This allows for security checks, logging, and other concerns to be implemented once, and run for all requests.
 *
 * Aspects will be ordered in priority so that for mediatorj if security checks fail, the rest of the processing won't be done.
 */
public abstract class Aspect {
    public abstract void execute(IRequest request);
}
