package org.mediatorj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class AppTest {

    private MediatorJ mediator;
    private Handler myHandler;
    private Handler myHandlerWithReturn;

    // Test request
    private class MyRequest implements IRequest {
        private int x;
        private int y;
    }

    // Test handler (no return value)
    private class MyHandler extends Handler<MyRequest, Void> {
        public MyHandler() {
            super(MyRequest.class);
        }

        @Override
        public Void handle(MyRequest request) {
            return null;
        }
    }

    // Class for return value
    private class ReturnValue {
        public String val = "hello";
    }

    // Test handler (w/ return value)
    private class MyHandlerWithReturn extends Handler<MyRequest, ReturnValue> {
        public MyHandlerWithReturn() {
            super(MyRequest.class);
        }

        @Override
        public ReturnValue handle(MyRequest request) {
            System.out.println("MyHandlerWithReturn here");

            return new ReturnValue();
        }
    }

    @BeforeEach
    public void setup() {
        myHandler = new MyHandler();
        myHandlerWithReturn = new MyHandlerWithReturn();
        mediator = MediatorJ.create();
    }

    @AfterEach
    public void tearDown() {
        // Pending
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

    @Test
    public void testSendingRequestWithReturnValue() {
        // Register a handler
        mediator.register(myHandlerWithReturn);

        // Create a request
        var req = new MyRequest();
        req.x = 100;
        req.y = 150;

        // Send the request to the mediator
        var resp = (ReturnValue) mediator.send(req);
        assertEquals("hello", resp.val);
    }
}
