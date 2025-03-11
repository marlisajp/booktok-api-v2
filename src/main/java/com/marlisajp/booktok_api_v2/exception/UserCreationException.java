package com.marlisajp.booktok_api_v2.exception;

import org.springframework.http.HttpStatus;

public class UserCreationException extends GenericException{
    public UserCreationException(HttpStatus status, int statusCode, String message) {
        super(status, statusCode,message);
    }

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}