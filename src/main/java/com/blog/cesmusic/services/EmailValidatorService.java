package com.blog.cesmusic.services;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailValidatorService {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static Boolean isValid(String email) {
        if (email == null) return false;

        return EMAIL_PATTERN.matcher(email).matches();
    }
}
