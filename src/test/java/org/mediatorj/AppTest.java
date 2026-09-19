package org.mediatorj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mediatorj.exception.DuplicateHandlerException;
import org.mediatorj.exception.MissingHandlerException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
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
        mediator = new MediatorJ();
    }

    @AfterEach
    public void tearDown() {
        mediator = null;
    }

    @Test
    public void testRegisteringHandlerOnce() {
        assertDoesNotThrow(() -> {
            mediator.register(myHandler);
        });
    }

    @Test
    public void testRegisteringHandlerTwiceFails() {
        mediator.register(myHandler);
        assertThrows(DuplicateHandlerException.class, () -> {
            mediator.register(myHandler);
        });
    }

    @Test
    public void testUnregisteringHandlerThatExists() {
        mediator.register(myHandler);
        assertDoesNotThrow(() -> {
            mediator.unregister(myHandler);
        });
    }

    @Test
    public void testUnregisteringHandlerThatDoesntExist() {
        assertThrows(RuntimeException.class, () -> {
            mediator.unregister(myHandler);
        });
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

    @Test
    public void testMissingHandlerThrowsException() {
        // Create a request
        var req = new MyRequest();
        req.x = 100;
        req.y = 150;

        // Check that exception is thrown
        assertThrows(MissingHandlerException.class, () -> {
            mediator.send(req);
        });
    }
}
