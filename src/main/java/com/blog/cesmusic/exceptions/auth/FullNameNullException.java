package com.blog.cesmusic.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FullNameNullException extends RuntimeException{
    public FullNameNullException(String message) {
        super(message);
    }
}
