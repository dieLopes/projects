package com.diego.desafiojavaspringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ProductNotFoundException e) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getStatusCode());
    }

    @ExceptionHandler(ProductBadRequestException.class)
    public ResponseEntity<ApiError> handleException(ProductBadRequestException e) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<>(error, error.getStatusCode());
    }
}
