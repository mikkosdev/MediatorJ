package org.mikkosdev.mediatorj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mikkosdev.mediatorj.aspect.Aspect;
import org.mikkosdev.mediatorj.container.AddressableObject;
import org.mikkosdev.mediatorj.container.Container;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class ContainerTest {

    final Logger logger = LoggerFactory.getLogger(ContainerTest.class);

    private MediatorJ mediator;
    private Handler myHandler;
    private Aspect myAspect;
    private Container myContainer;

    // Test request
    private class MyRequest implements IRequest {
        private int x;
        private int y;
    }

    // Test handler class
    private class MyHandler extends Handler<MyRequest, Void> {
        public MyHandler() {
            super(MyRequest.class);
        }

        @Override
        public Void handle(MyRequest request) {
            return null;
        }
    }

    private class MyAspect extends Aspect {
        public int index = 0;
        public void execute(IRequest req) {
            logger.debug("MyAspect: This is run every time a request is being handled.");
        }
    }

    private class MyAddressableObject extends AddressableObject {
    }

    @BeforeEach
    void setUp() {
        mediator = new MediatorJ();
        myHandler = new MyHandler();
        myAspect = new MyAspect();
        myContainer = new Container();
    }

    @AfterEach
    void tearDown() {
        mediator = null;
        myContainer = null;
    }

    @Test
    public void testSendingMessageToObject() {
        var uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        var obj = new MyAddressableObject();
        var request = new MyRequest();

        // Create spy object for the AddressableObject
        MyAddressableObject mySpy = Mockito.spy(obj);

        // Add the AddressableObject to the container and send it a message
        myContainer.add(uuid, mySpy);
        myContainer.sendMessage(uuid, request);

        // Check that sendMessage() was called
        Mockito.verify(mySpy).sendMessage(any(IRequest.class));
    }

    @Test
    public void testSendingMessageToNonExistentObjectThrows() {
        var uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        var request = new MyRequest();

        // Send message to a non-existent object
        assertThrows(Exception.class, () -> {
            myContainer.sendMessage(uuid, request);
        });
    }
}
