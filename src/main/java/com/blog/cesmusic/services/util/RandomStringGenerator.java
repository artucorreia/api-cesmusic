package com.blog.cesmusic.services.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class RandomStringGenerator {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String UPPER_DIGITS = UPPER + DIGITS;
    private static final Random RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException("Length must be at least 1");

        char[] buffer = new char[length];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = UPPER_DIGITS.charAt(RANDOM.nextInt(UPPER_DIGITS.length()));
        }
        return new String(buffer);
    }
}
