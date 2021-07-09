package com.diego.desafiojavaspringboot.exception;

import org.springframework.http.HttpStatus;

public class ApiError {

    private final HttpStatus statusCode;
    private final String message;

    public ApiError(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
