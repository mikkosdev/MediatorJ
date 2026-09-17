package org.example;

import org.example.aspects.Aspect;

import java.util.*;

public class MediatorJ<T extends Handler> {

    private List<Handler> handlers = new ArrayList<>();
    private List<Aspect> aspects = new ArrayList<>();

    public static MediatorJ create() {
        return new MediatorJ();
    }

    /**
     * Register an aspect
     *
     * @param aspect   Aspect must extend Aspect abstract class
     */
    public void register(Aspect aspect) {
        aspects.add(aspect);

        System.out.println("Registering: " + aspect);
        System.out.println("aspect.getClass(): " + aspect.getClass());
    }

    /**
     * Unregister an aspect
     *
     * @param aspect   Aspect must extend Aspect abstract class
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
     * @param handler   Handler must implement IHandler interface
     */
    public void register(T handler) {
        handlers.add(handler);

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

        // Find handler that handles the request type
        for(Handler h : handlers) {
            System.out.println("Iterating: " + h.getClazz() + " vs " + req.getClass());
            if (h.getClazz() == req.getClass()) {
                System.out.println("Found:" + req.getClass());
                return h.handle(req);
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
