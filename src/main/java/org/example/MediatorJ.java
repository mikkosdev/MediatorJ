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
     * Register handler
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
    public void unregister(IHandler handler) {
//        _handlers.removeIf((h) -> handler.getClass() == handler.getClass());
    }

    public void send(IRequest req) {
        System.out.println("Sending");

        // Find handler that handles the request type
        for(Handler h : handlers) {
            System.out.println("Iterating: " + h.getClazz() + " vs " + req.getClass());
            if (h.getClazz() == req.getClass()) {
                System.out.println("Found:" + req.getClass());
                h.handle(req);
            }
        }
    }

    private void runAspects() {
        for (Aspect a : aspects) {
            // Pending
        }
    }
}
