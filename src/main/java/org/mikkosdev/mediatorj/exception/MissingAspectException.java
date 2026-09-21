package org.mikkosdev.mediatorj.exception;

public class MissingAspectException extends RuntimeException {

    public MissingAspectException(Class clazz) {
        super("No aspect" + clazz + " found");
    }
}
