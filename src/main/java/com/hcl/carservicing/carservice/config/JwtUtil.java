package com.hcl.carservicing.carservice.config;

import com.hcl.carservicing.carservice.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

   @Value("${jwt.secret}")
    private String secretKey; // At least 256-bit (32 chars for HS256)
   @Value("${jwt.expiration}")
    private long expirationTime= 3600000; // 1 hour in milliseconds
//    private Key key = generateKey(secretKey);

//    private final String secretKey = "mySuperSecretKey12345mySuperSecretKey12345"; // At least 256-bit (32 chars for HS256)
//    private final long expirationTime = 3600000; // 1 hour in milliseconds
//    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // Generate JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(generateKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from the JWT token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate JWT token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Check if token is expired
    boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    Key generateKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
