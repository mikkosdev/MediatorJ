package org.mediatorj.exception;

public class DuplicateAspectException extends RuntimeException {
    public DuplicateAspectException(Class clazz) {
        super("Aspect {} already registered" + clazz);
    }
}
