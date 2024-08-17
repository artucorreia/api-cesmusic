package com.blog.cesmusic.services.util;

import java.util.UUID;

public class LoginCodeGenerator {
    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
