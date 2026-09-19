package org.mediatorj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mediatorj.aspect.Aspect;
import org.mockito.Mockito;

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

    private class MyAspect extends Aspect {
        public int index = 0;
        public void execute(IRequest req) {
            System.out.println("MyAspect: This is run every time a request is being handled.");
        }
    }

    @BeforeEach
    void setUp() {
        myHandler = new MyHandler();
        mediator = MediatorJ.getDefault();
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
}
