package com.example.springbootmongodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException() {
        super();
    }
    public TenantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public TenantNotFoundException(String message, String ... params) {
        super(String.format(message, params));
    }
    public TenantNotFoundException(String message) {
        super(message);
    }
    public TenantNotFoundException(Throwable cause) {
        super(cause);
    }
}
