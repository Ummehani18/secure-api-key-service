package com.hdev.apikeymanager.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component

public class ApiKeyGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public String generateKey() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
