package com.marlisajp.booktok_api_v2.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {
    private HttpStatus statusCode;
    private String errorMessage;

    public GenericException(HttpStatus statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
