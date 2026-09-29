package org.mikkosdev.mediatorj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mikkosdev.mediatorj.aspect.Aspect;
import org.mikkosdev.mediatorj.container.AddressableObject;
import org.mikkosdev.mediatorj.container.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class AddressableObjectTest {

    final Logger logger = LoggerFactory.getLogger(ContainerTest.class);

    private Container myContainer;
    private AddressableObject myAddressableObject;
    private MyRequest myRequest;

    // Test request
    private class MyRequest implements IRequest {
        private int x;
        private int y;
    }

    // Test addressable object
    private class MyAddressableObject extends AddressableObject {
    }

    @BeforeEach
    void setUp() {
        myContainer = new Container();
        myAddressableObject = new MyAddressableObject();
        myRequest = new MyRequest();
    }

    @AfterEach
    void tearDown() {
        myContainer = null;
    }

    @Test
    public void testSendMessageWorks() {
        // Check default size
        assertEquals(myAddressableObject.getInboxSize(), 0);

        // Send one message
        myAddressableObject.sendMessage(myRequest);

        // Check size has grown by one
        assertEquals(myAddressableObject.getInboxSize(), 1);
    }
}
