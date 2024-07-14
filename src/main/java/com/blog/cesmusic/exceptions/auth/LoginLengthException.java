package com.blog.cesmusic.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginLengthException extends RuntimeException{
    public LoginLengthException(String message) {
        super(message);
    }
}
