package com.blog.cesmusic.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LoginAlreadyUsedException extends RuntimeException{
    public LoginAlreadyUsedException(String message) {
        super(message);
    }
}
