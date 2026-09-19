package org.mediatorj;

/**
 * This abstract class is used as superclass for all those classes that handle requests.
 *
 * @param <T>
 * @see org.mediatorj.IRequest
 */
public abstract class Handler<T, U> {

    private final Class<T> clazz;

    public Handler(Class<T> request) {
        this.clazz = request;
    }

    /**
     * This method returns the class of the request type it matches.
     *
     * @return the request class this handler matches
     */
    public final Class<T> getClazz() {
        return this.clazz;
    }

    /**
     * Abstract method that has to be implemented by every handler
     *
     * @param request The request that needs to be handler
     * @return
     * @see IRequest
     */
    public abstract U handle(T request);
}
