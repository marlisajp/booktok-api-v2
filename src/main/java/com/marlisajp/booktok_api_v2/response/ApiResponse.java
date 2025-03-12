package com.marlisajp.booktok_api_v2.response;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private boolean success;
    private T body;

    public ApiResponse() {
    }

    public ApiResponse(HttpStatus status, int statusCode, String message, boolean success, T body) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.body = body;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
