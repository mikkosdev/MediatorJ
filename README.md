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

*Pending*

# To Do

- Ensure the MediatorJ object is singleton and injectable
- Create unit tests for everything
- Add ordering to aspects
- Check that handlers can return values to the place where the request was sent

