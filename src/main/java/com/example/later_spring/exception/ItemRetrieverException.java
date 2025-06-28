package com.example.later_spring.exception;

public class ItemRetrieverException extends LaterApplicationException {
    public ItemRetrieverException(String message) {
        super(message);
    }

    public ItemRetrieverException(String message, Throwable cause) {
        super(message, cause);
    }
}