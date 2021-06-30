package com.example.springbootmongodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeeBadRequestException extends RuntimeException {
    public EmployeeBadRequestException() {
        super();
    }
    public EmployeeBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public EmployeeBadRequestException(String message, String ... params) {
        super(String.format(message, params));
    }
    public EmployeeBadRequestException(String message) {
        super(message);
    }
    public EmployeeBadRequestException(Throwable cause) {
        super(cause);
    }
}
