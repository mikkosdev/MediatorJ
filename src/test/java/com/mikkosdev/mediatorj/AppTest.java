package com.mikkosdev.mediatorj;

import org.example.Handler;
import org.example.IRequest;
import org.example.MediatorJ;
import org.example.annotations.AspectClass;
import org.example.aspects.Aspect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class AppTest {

    private MediatorJ mediator;
    private Handler myHandler;

    @BeforeEach
    public void setup() {
        myHandler = new MyHandler();
        mediator = MediatorJ.create();
    }

    @Test
    public void testSendingRequest() {
        Handler myHandlerSpy = Mockito.spy(myHandler);

        // Register a handler
        mediator.register(myHandlerSpy);

        // Create a request
        var req = new MyRequest();
        req.x = 100;
        req.y = 150;

        // Send the request to the mediator
        mediator.send(req);

        // Check that handler was called
        Mockito.verify(myHandlerSpy).handle(any(IRequest.class));
    }

    public void testSendingRequestWithReturnValue() {

    }

    private class MyRequest implements IRequest {
        private int x;
        private int y;
    }

    // Test handler class
    private class MyHandler extends Handler<MyRequest> {
        public MyHandler() {
            super(MyRequest.class);
        }

        @Override
        public void handle(IRequest request) {
            System.out.println("MyHandler here");
        }
    }
}
