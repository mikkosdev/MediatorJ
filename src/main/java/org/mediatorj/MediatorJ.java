package org.mediatorj;

import org.mediatorj.aspect.Aspect;
import org.mediatorj.exception.DuplicateHandlerException;
import org.mediatorj.exception.MissingHandlerException;

import java.util.*;

public class MediatorJ<T extends Handler> {

    private List<Handler> handlers = new ArrayList<>();
    private List<Aspect> aspects = new ArrayList<>();

    /**
     * Register an aspect
     *
     * @param aspect Aspect must extend Aspect abstract class
     */
    public void register(Aspect aspect) {
        aspects.add(aspect);

        System.out.println("Registering: " + aspect);
        System.out.println("aspect.getClass(): " + aspect.getClass());
    }

    /**
     * Unregister an aspect
     *
     * @param aspect Aspect must extend Aspect abstract class
     */
    public void unregister(Aspect aspect) {
        throw new UnsupportedOperationException();
//        aspects.add(aspect);
//
//        System.out.println("Unregistering: " + aspect);
//        System.out.println("aspect.getClass(): " + aspect.getClass());
    }

    /**
     * Register a handler
     *
     * @param handler Handler must implement IHandler interface
     */
    public void register(T handler) {
        if (getHandler(handler.getClazz()) != null) {
            throw new DuplicateHandlerException(handler.getClazz());
        } else {
            handlers.add(handler);
        }

        System.out.println("Registering: " + handler);
        System.out.println("handler.getClass(): " + handler.getClazz());
    }

    /**
     * Unregister handler
     */
    public void unregister(Handler handler) {
        throw new UnsupportedOperationException();
//        _handlers.removeIf((h) -> handler.getClass() == handler.getClass());
    }

    public Object send(IRequest req) {
        System.out.println("Sending");

        // Run request for all aspects
        for (Aspect a : aspects) {
            System.out.println("Executing aspect: " + a.getClass());
            a.execute(req);
        }

        Handler h = getHandler(req.getClass());
        // Handle request if handler found
        if (h != null) {
            return h.handle(req);
        }

        // If no handler was found, throw exception
        throw new MissingHandlerException(req);
    }

    private Handler getHandler(Class clazz) {
        for (Handler h : handlers) {
            if (h.getClazz() == clazz) {
                return h;
            }
        }
        return null;
    }

    private void runAspects() {
        for (Aspect a : aspects) {
            // Pending
        }
    }
}
