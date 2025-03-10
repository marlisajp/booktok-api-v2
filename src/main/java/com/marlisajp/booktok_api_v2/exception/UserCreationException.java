package com.marlisajp.booktok_api_v2.exception;

public class UserCreationException extends RuntimeException{
    public UserCreationException(String message) {
        super(message);
    }

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}