package com.example.library_shop.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_STRING =
            "THIS_IS_MY_SUPER_SECRET_KEY_FOR_JWT_1234567890"; // >= 32 chars

    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 24; // 24 hours

    private SecretKey secretKey;

    // Tạo key chuẩn từ SECRET_STRING
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
    }

    // ===========================
    // Generate Token
    // ===========================
    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .claim("role", role)
                .signWith(secretKey)      // ✔ API mới
                .compact();
    }

    // ===========================
    // Extract Username
    // ===========================
    public String extractUsername(String token) {
        return Jwts.parser()              // ✔ API mới
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // ===========================
    // Extract Role
    // ===========================
    public String extractRole(String token) {
        Object role = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role");

        return role != null ? role.toString() : null;
    }

    // ===========================
    // Validate Token
    // ===========================
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
