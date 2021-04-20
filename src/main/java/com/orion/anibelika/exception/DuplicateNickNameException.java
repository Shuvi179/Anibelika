package com.orion.anibelika.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "NickName is already in use")
public class DuplicateNickNameException extends RuntimeException {
    public DuplicateNickNameException(String message) {
        super(message);
    }
}
