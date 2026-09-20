package org.mediatorj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mediatorj.aspect.Aspect;
import org.mediatorj.exception.DuplicateAspectException;
import org.mediatorj.exception.MissingAspectException;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class AspectTest {

    final Logger logger = LoggerFactory.getLogger(AspectTest.class);
    private MediatorJ mediator;
    private Handler myHandler;
    private Aspect myAspect;

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

    private class MyAspect2 extends Aspect {
        public int index = 1;
        public void execute(IRequest req) {
            logger.debug("MyAspect2: This is run every time a request is being handled.");
        }
    }

    @BeforeEach
    void setUp() {
        mediator = new MediatorJ();
        myHandler = new MyHandler();
        myAspect = new MyAspect();
    }

    @AfterEach
    void tearDown() {
        mediator = null;
    }

    @Test
    public void testRegisterAspectWorks() {
        // Register an aspect
        assertDoesNotThrow(() -> {
            mediator.register(myAspect);
        });
    }

    @Test
    public void testRegisteringAspectNTimesThrows() {
        // Do it once
        mediator.register(myAspect);
        assertThrows(DuplicateAspectException.class, () -> {
            // Doing it twice should throw exception
            mediator.register(myAspect);
        });
    }

    @Test
    public void testUnregisteringAspectWorks() {
        mediator.register(myAspect);

        // Unregister an aspect
        assertDoesNotThrow(() -> {
            mediator.unregister(myAspect);
        });
    }

    @Test
    public void testUnregisteringAspectNTimesThrows() {
        mediator.register(myAspect);

        // Do it once
        mediator.unregister(myAspect);
        assertThrows(MissingAspectException.class, () -> {
            // Doing it twice should throw exception
            mediator.unregister(myAspect);
        });
    }

    @Test
    public void testAspectWorks() {
        // Create an aspect
        var myAspect2 = new MyAspect2();

        // Create a spy object for an aspect
        Aspect myAspectSpy = Mockito.spy(myAspect);
        Aspect myAspectSpy2 = Mockito.spy(myAspect2);

        // Register an aspect
        mediator.register(myAspectSpy);
        mediator.register(myAspectSpy2);

        // Register a handler
        mediator.register(myHandler);

        // Create a request
        var req = new MyRequest();
        req.x = 100;
        req.y = 150;

        // Send the request to the mediator
        mediator.send(req);

        // Check that aspects were called
        Mockito.verify(myAspectSpy).execute(any(IRequest.class));
        Mockito.verify(myAspectSpy2).execute(any(IRequest.class));
    }
}
