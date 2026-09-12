import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.Handler;
import org.example.IHandler;
import org.example.IRequest;
import org.junit.jupiter.api.Test;
import org.example.MediatorJ;

public class AppTest {

    @Test
    public void TestSomething() {

        // Create a mediator
        MediatorJ mediator = MediatorJ.create();

        // Create a handler

        // Register a handler
        var h = new MyHandler();
        mediator.register(h);

        // Create a request
        var req = new MyRequest();
        req.x = 100;
        req.y = 150;

        // Send the request to the mediator
        mediator.send(req);

        // Check that handler was called

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

        public void handle(MyRequest request) {
            System.out.println("MyHandler here");
        }
    }
}
