package com.mikkosdev.mediatorj;

import org.example.Handler;
import org.example.IRequest;
import org.example.MediatorJ;
import org.example.annotations.AspectClass;
import org.example.aspects.Aspect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

public class AspectTest {

    private MediatorJ mediator;
    private Handler myHandler;

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

    @AspectClass(index = 0)
    private class MyAspect extends Aspect {
        public void execute(IRequest req) {
            System.out.println("This is run every time a request is being handled.");
        }
    }

    @BeforeEach
    void setUp() {
        myHandler = new MyHandler();
        mediator = MediatorJ.create();
    }

    @AfterEach
    void tearDown() {
        // Pending
    }

    @Test
    public void testAspectWorks() {
        // Create an aspect
        var myAspect = new MyAspect();

        // Create a spy object for an aspect
        Aspect myAspectSpy = Mockito.spy(myAspect);

        // Register an aspect
        mediator.register(myAspectSpy);

        // Register a handler
        mediator.register(myHandler);

        // Create a request
        var req = new MyRequest();
        req.x = 100;
        req.y = 150;

        // Send the request to the mediator
        mediator.send(req);

        // Check that handler was called
        Mockito.verify(myAspectSpy).execute(any(IRequest.class));
    }

    @Disabled
    @Test
    public void testAspectClassWorks() {
        // Test that a class can be annotated with the @AspectClass annotation
        fail("Not implemented yet.");
    }

    @Disabled
    @Test
    public void testThatAspectClassIndexingWorks() {
        fail("Not implemented yet.");
    }

    @Disabled
    @Test
    public void testMustRunFirstWorks() {
        fail("Not implemented yet.");
    }

    @Disabled
    @Test void testMustRunLastWorks() {
        fail("Not implemented yet.");
    }
}
