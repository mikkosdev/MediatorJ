# MediatorJ

This is work-in-progress. Do not use yet for anything.

# How sending requests works

- Create a request class that contains the fields that are needed for the handling of that request
  - Request class must extend `IRequest` interface
- Create a handler class that is called whenever a Request of fitting type is dispatched
  - Handler class must extend `Handler` abstract class
- Dispatch (send) the request to the Mediator component:
  - `mediator.send(request);`
- Mediator finds a handler for the request and calls its `handle()`method, passing the request object to it
- Handler executes it's `handle()` code using the request object
- Handler may return a value (primitive value or an object) back to the code that called the `send()` method

# How aspects work

MediatorJ has also "aspects" which represent cross-cutting concerns that can be run with every request.
This enables security checks, logging, validation, etc to be centrally.

Extend your cross-cutting concern from the abstract class `Aspect` and implement the `èxecute()` method which takes an `IRequest` object as a parameter.

Every single request will run through all the aspects.

Aspects can be ordered with the `AspectClass` annotations `index` parameter.
There's `MustRunFirst` annotation that is just there to communicate to developers the intent that this aspect should always be first, and it throws an error at compile time.
There's a matchin `MustRunLast` annotation that communicates and enforces the processing to happen last in the aspect chain.

*Pending*

# To Do

- Ensure the MediatorJ object is singleton and injectable
- Create unit tests for everything
- Add ordering to aspects
- Check that handlers can return values to the place where the request was sent
- Check performance
- Create package and make it available in Java package repositories