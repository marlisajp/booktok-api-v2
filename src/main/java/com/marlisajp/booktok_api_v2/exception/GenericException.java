package com.marlisajp.booktok_api_v2.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {
    private HttpStatus status;
    private String errorMessage;
    private int statusCode;

    public GenericException(HttpStatus status, int statusCode, String errorMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
