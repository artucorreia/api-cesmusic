package com.blog.cesmusic.services;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MailValidatorService {
    private static final String MAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern MAIL_PATTERN = Pattern.compile(MAIL_REGEX);

    public static Boolean isValid(String mail) {
        if (mail == null) return false;

        return MAIL_PATTERN.matcher(mail).matches();
    }
}
