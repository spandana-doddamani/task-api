
package com.ikaansh.task_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // Development secret: replace with an environment variable
    // before deploying the application.
    private static final String SECRET =
            "TaskApiDevelopmentSecretKey2026-AtLeast32Bytes!";

    private final SecretKey key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000L * 60 * 60))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            return username.equals(extractUsername(token));
        } catch (RuntimeException ex) {
            return false;
        }
    }
}
