package com.diego.taskboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TenantBadRequestException extends RuntimeException {
    public TenantBadRequestException() {
        super();
    }
    public TenantBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public TenantBadRequestException(String message, String ... params) {
        super(String.format(message, params));
    }
    public TenantBadRequestException(String message) {
        super(message);
    }
    public TenantBadRequestException(Throwable cause) {
        super(cause);
    }
}
