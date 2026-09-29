package org.mikkosdev.mediatorj.container;

import org.mikkosdev.mediatorj.IRequest;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AddressableObject {

    private UUID id;
    protected ConcurrentLinkedQueue<IRequest> inbox = new ConcurrentLinkedQueue<>();

    // ****************************** Public Methods ******************************

    public void sendMessage(IRequest request) {
        inbox.offer(request);
    }

    public int getInboxSize() {
        return inbox.size();
    }

    // ****************************** Protected Methods ******************************

    protected IRequest getNextMessage() {
        return inbox.poll();
    }
}
