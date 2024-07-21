package com.blog.cesmusic.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyIsAdministratorException extends RuntimeException{
    public UserAlreadyIsAdministratorException(String message) {
        super(message);
    }
}
