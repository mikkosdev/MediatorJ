package org.mikkosdev.mediatorj;

import org.mikkosdev.mediatorj.aspect.Aspect;
import org.mikkosdev.mediatorj.exception.DuplicateAspectException;
import org.mikkosdev.mediatorj.exception.DuplicateHandlerException;
import org.mikkosdev.mediatorj.exception.MissingAspectException;
import org.mikkosdev.mediatorj.exception.MissingHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MediatorJ<T extends Handler> {

    final Logger logger = LoggerFactory.getLogger(MediatorJ.class);
    private static final MediatorJ INSTANCE = new MediatorJ();

    private List<Handler> handlers = new ArrayList<>();
    private List<Aspect> aspects = new ArrayList<>();

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

    /**
     * Get the default (singleton) instance.
     * <p>
     * This is a convenience method, but you should generally use dependency injection - not this.
     * If you need multiple instances, just can create them with `new MediatorJ()`.
     *
     * @return MediatorJ object instance
     */
    public static MediatorJ getDefault() {
        return INSTANCE;
    }

    /**
     * Register an aspect
     *
     * @param aspect Aspect must extend Aspect abstract class
     */
    public synchronized void register(Aspect aspect) {
        logger.debug("Registering aspect <{}>", aspect.getClass());

        if (getAspect(aspect.getClass()) != null) {
            throw new DuplicateAspectException(aspect.getClass());
        } else {
            writeLock.lock();
            try {
                aspects.add(aspect);
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * Unregister an aspect
     *
     * @param aspect Aspect must extend Aspect abstract class
     */
    public synchronized void unregister(Aspect aspect) {
        logger.debug("Unregistering aspect <{}>", aspect.getClass());

        var removed = false;

        writeLock.lock();
        try {
            removed = aspects.removeIf((a) -> a.getClass() == aspect.getClass());
        } finally {
            writeLock.unlock();
        }

        if (removed) {
            logger.debug("Unregistered aspect <{}>", aspect.getClass());
        } else {
            // TODO: Should I use generic exception here? MissingHandlerException might be better.
            throw new MissingAspectException(aspect.getClass());
        }
    }

    /**
     * Register a handler
     *
     * @param handler Handler must implement IHandler interface
     */
    public synchronized void register(T handler) {
        logger.debug("Registering handler for type <{}>", handler.getClass());

        if (getHandler(handler.getClazz()) != null) {
            throw new DuplicateHandlerException(handler.getClazz());
        } else {
            writeLock.lock();
            try {
                handlers.add(handler);
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * Unregister handler
     */
    public synchronized void unregister(Handler handler) {
        logger.debug("Unregistering handler for type <{}>", handler.getClass());

        var removed = false;

        writeLock.lock();
        try {
            removed = handlers.removeIf((h) -> h.getClass() == handler.getClass());
        } finally {
            writeLock.unlock();
        }

        if (removed) {
            logger.debug("Unregistered handler for type <{}>", handler.getClass());
        } else {
            // TODO: Should I use generic exception here? MissingHandlerException might be better.
            throw new MissingHandlerException(handler.getClazz());
        }
    }

    /**
     * This method does the dispatching of requests to handlers and aspects.
     *
     * @param req Object that implements `IRequest` interface
     * @return Return value defined by the handler, must be object type
     * @see IRequest
     */
    public Object send(IRequest req) {
        logger.debug("Sending request with type <{}>", req.getClass());

        Handler h = null;

        readLock.lock();
        try {
            // Try to find a handler for the request
            h = getHandler(req.getClass());

            // Run request for all aspects
            runAspects(req);
        } finally {
            readLock.unlock();
        }

        // Handle request if handler found
        if (h != null) {
            return h.handle(req);
        }

        // If no handler was found, throw exception
        throw new MissingHandlerException(req.getClass());
    }

    // Find handler with class
    private Handler getHandler(Class clazz) {
        for (Handler h : handlers) {
            if (h.getClazz() == clazz) {
                return h;
            }
        }
        return null;
    }

    // Find aspect with class
    private Aspect getAspect(Class clazz) {
        for (Aspect a : aspects) {
            if (a.getClass() == clazz) {
                return a;
            }
        }
        return null;
    }

    // Run all aspects
    private void runAspects(IRequest request) {
        logger.debug("Running aspects:");

        // If any aspects have been registered, run the request against them
        if (aspects.size() > 0) {
            for (Aspect a : aspects) {
                logger.debug("Running aspect <{}>", a.getClass());
                a.execute(request);
            }
        } else {
            logger.debug("(No aspects)");
        }
    }
}
