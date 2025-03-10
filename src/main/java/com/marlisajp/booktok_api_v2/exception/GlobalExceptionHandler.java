package com.marlisajp.booktok_api_v2.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class GlobalExceptionHandler {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(GenericException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatusCode(),
                ex.getErrorMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }
}
