package com.example.later_spring.exception;

public class InsufficientPermissionException extends LaterApplicationException {
    public InsufficientPermissionException(String message) {
        super(message);
    }
}
