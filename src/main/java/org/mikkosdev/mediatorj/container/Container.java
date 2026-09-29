package org.mikkosdev.mediatorj.container;

import org.mikkosdev.mediatorj.IRequest;
import org.mikkosdev.mediatorj.MediatorJ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Container {

    final Logger logger = LoggerFactory.getLogger(Container.class);
    private Map<UUID, AddressableObject> objects = new HashMap<>();

    public synchronized void add(UUID id, AddressableObject o) {
        objects.put(id, o);
    }

    public synchronized Object remove(UUID id) {
        return objects.remove(id);
    }

    public synchronized void remove(UUID id, AddressableObject o) {
        objects.remove(id, o);
    }

    public synchronized void sendMessage(UUID id, IRequest request) {
        var o = objects.get(id);
        if (o != null) {
            logger.debug("Sending message to object <{}>", o);
            o.sendMessage(request);
        } else {
            logger.warn("Object not found!");
            throw new IllegalArgumentException("Object with id <" + id + "> not found");
        }
    }
}
