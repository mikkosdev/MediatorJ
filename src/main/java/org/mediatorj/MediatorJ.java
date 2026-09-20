package org.mediatorj;

import org.mediatorj.aspect.Aspect;
import org.mediatorj.exception.DuplicateAspectException;
import org.mediatorj.exception.DuplicateHandlerException;
import org.mediatorj.exception.MissingAspectException;
import org.mediatorj.exception.MissingHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MediatorJ<T extends Handler> {

    final Logger logger = LoggerFactory.getLogger(MediatorJ.class);
    private static final MediatorJ INSTANCE = new MediatorJ();

    private List<Handler> handlers = new ArrayList<>();
    private List<Aspect> aspects = new ArrayList<>();

    /**
     * Get the default (singleton) instance.
     *
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
    public void register(Aspect aspect) {
        logger.debug("Registering aspect <{}>", aspect.getClass());

        if (getAspect(aspect.getClass()) != null) {
            throw new DuplicateAspectException(aspect.getClass());
        } else {
            aspects.add(aspect);
        }
    }

    /**
     * Unregister an aspect
     *
     * @param aspect Aspect must extend Aspect abstract class
     */
    public void unregister(Aspect aspect) {
        logger.debug("Unregistering aspect <{}>", aspect.getClass());

        var removed = aspects.removeIf((a) -> a.getClass() == aspect.getClass());
        if(removed) {
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
    public void register(T handler) {
        logger.debug("Registering handler for type <{}>", handler.getClass());

        if (getHandler(handler.getClazz()) != null) {
            throw new DuplicateHandlerException(handler.getClazz());
        } else {
            handlers.add(handler);
        }
    }

    /**
     * Unregister handler
     */
    public void unregister(Handler handler) {
        logger.debug("Unregistering handler for type <{}>", handler.getClass());

        var removed = handlers.removeIf((h) -> h.getClass() == handler.getClass());
        if(removed) {
            logger.debug("Unregistered handler for type <{}>", handler.getClass());
        } else {
            // TODO: Should I use generic exception here? MissingHandlerException might be better.
            throw new MissingHandlerException(handler.getClazz());
        }
    }

    public Object send(IRequest req) {
        logger.debug("Sending request with type <{}>", req.getClass());

        // Run request for all aspects
//        for (Aspect a : aspects) {
//            logger.debug("Executing aspect <{}>", a.getClass());
//            a.execute(req);
//        }

        runAspects(req);

        Handler h = getHandler(req.getClass());
        // Handle request if handler found
        if (h != null) {
            return h.handle(req);
        }

        // If no handler was found, throw exception
        throw new MissingHandlerException(req.getClass());
    }

    private Handler getHandler(Class clazz) {
        for (Handler h : handlers) {
            if (h.getClazz() == clazz) {
                return h;
            }
        }
        return null;
    }

    private Aspect getAspect(Class clazz) {
        for (Aspect a : aspects) {
            if (a.getClass() == clazz) {
                return a;
            }
        }
        return null;
    }

    private void runAspects(IRequest request) {
        logger.debug("Running aspects:");

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
