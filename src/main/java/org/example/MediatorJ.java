package org.example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MediatorJ<T extends Handler> {

    private ArrayList<Handler> _handlers = new ArrayList<>();

    public static MediatorJ create() {
        return new MediatorJ();
    }

    /**
     * Register handler
     *
     * @param handler   Handler must implement IHandler interface
     */
    public void register(T handler) {
        _handlers.add(handler);

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
        for(Handler h : _handlers) {
            System.out.println("Iterating: " + h.getClazz() + " vs " + req.getClass());
            if (h.getClazz() == req.getClass()) {
                System.out.println("Found:" + req.getClass());
            }
        }
    }
}
