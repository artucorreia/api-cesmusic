package com.blog.cesmusic.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PendingUserException extends RuntimeException{
    public PendingUserException(String message) {
        super(message);
    }
}
