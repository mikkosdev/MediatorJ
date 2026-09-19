# MediatorJ

This is work-in-progress. Do not use yet for anything.

## How sending requests works

- Create a request class that contains the fields that are needed for the handling of that request
  - Request class must extend `IRequest` interface
- Create a handler class that is called whenever a Request of fitting type is dispatched
  - Handler class must extend `Handler` abstract class
- Dispatch (send) the request to the Mediator component:
  - `mediator.send(request);`
- Mediator finds a handler for the request and calls its `handle()`method, passing the request object to it
- Handler executes it's `handle()` code using the request object
- Handler may return a value (primitive value or an object) back to the code that called the `send()` method

## How aspects work

MediatorJ has also "aspects" which represent cross-cutting concerns that can be run with every request.
This enables security checks, logging, validation, etc to be centrally.

Extend your cross-cutting concern from the abstract class `Aspect` and implement the `èxecute()` method which takes an `IRequest` object as a parameter.

Every single request will run through all the aspects.

Aspects can be ordered with the `AspectClass` annotations `index` parameter.
There's `MustRunFirst` annotation that is just there to communicate to developers the intent that this aspect should always be first, and it throws an error at compile time.
There's a matchin `MustRunLast` annotation that communicates and enforces the processing to happen last in the aspect chain.

*Pending*

## To Do

- Create unit tests for everything
- Add ordering to aspects with annotation
- Register aspects with annotations
- Register handlers with annotations
- Test performance and optimize if needed
- Create deployment package and make it available in Java package repositories
- Ensure that an aspect can only be registered once
- Check thread safety / multithreading
- Add UML model under doc/
- Clean up standard out debug logs

## Dependencies

### Run-time dependencies

- SLF4J API

### Development-time dependencies for this project (not for users) 

- JUnit 6 for unit tests
- Mockito for mock objects
- Logback Classic 1.5

## Code Examples

0. Get MediatorJ instance

You have two ways of getting a MediatorJ object:

a) using `new MediatorJ()` to create a new instance. This is a good approach for unit tests and in cases where you want to control the instantiation manually.
b) using the `MediatorJ.getDefault()` static factory method that always returns the same object. Use carefully!
 
*** It is recommended to use a dependency injection framework to get the instance(s). ***

1. Create a request for your use case:

```Java
import org.mediatorj.IRequest;

class GetUserRequest implements IRequest {
  public final String username;

  public GetUserRequest(String username) {
    this.username = username;
  }
}
```

There are other ways of writing the requests, for example by using *private* instance variables, or using a *record* instead of a class.

The important thing is that the request should be immutable.


2. Create a handler for the above use case:

With return value:

```Java
import org.mediatorj.Handler;
import org.mediatorj.IRequest;

class GetUserHandler extends Handler<GetUserRequest, User> {
  @Override
  public User handle(IRequest request) {
    var user = userRepository.getUser(request.username);
    return user;
  }
}
```

If you have a handler without return value, use `Void` type, like here:

```Java
import org.mediatorj.Handler;
import org.mediatorj.IRequest;

class SomeHandler extends Handler<SomeRequest, Void> {
  @Override
  public Void handle(IRequest request) {
    // <code omitted here>
  }
}
```

It's a good practice to add the `@Override` annotation.

3. Send the request where you need the action to happen:

```Java
// This code can be anywhere in your application
var req = new GetUserRequest("myTestUser");
var user = (User) mediator.send(req);
```

Notice that you have to cast the return value type for `send()` method.

Or if you have no return value, just do this:

```Java
// This code can be anywhere in your application
@Inject MediatorJ mediator;

var req = new SomeRequest();
var x = mediator.send(req);
```

Above example uses popular `@Inject` field annotation to show how to get the instance using a dependency injection framework.