package com.example.smapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JWTConfig {
    private final PasswordEncoder encoder;
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expiration}")
    private long expirationTime;

    public JWTConfig(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String getSecret() {
        return secret;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
