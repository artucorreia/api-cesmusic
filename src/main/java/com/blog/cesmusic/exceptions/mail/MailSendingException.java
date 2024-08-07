package com.blog.cesmusic.exceptions.mail;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MailSendingException extends RuntimeException {
    public MailSendingException(String message) {
        super(message);
    }
}
