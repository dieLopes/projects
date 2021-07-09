package com.diego.desafiojavaspringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductBadRequestException extends RuntimeException {

    public ProductBadRequestException(String message) {
        super(message);
    }
}
