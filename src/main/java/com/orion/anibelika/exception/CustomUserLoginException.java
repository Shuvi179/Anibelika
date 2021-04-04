package com.orion.anibelika.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class CustomUserLoginException extends RuntimeException {
    public CustomUserLoginException(String message) {
        super(message);
    }
}
