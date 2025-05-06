package com.hcl.carservicing.carservice.config;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();

        Field field = JwtUtil.class.getDeclaredField("secretKey");
        field.setAccessible(true);
        field.set(jwtUtil, "my-test-secret-key-1234567890123456");
    }

    @Test
    void generateToken_validUsername_tokenGenerated() {
        String token = jwtUtil.generateToken("test-user");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_validToken_returnsCorrectUsername() {
        String token = jwtUtil.generateToken("test-user");
        String username = jwtUtil.extractUsername(token);
        assertEquals("test-user", username);
    }

    @Test
    void validateToken_validTokenAndUsername_returnsTrue() {
        String token = jwtUtil.generateToken("test-user");
        boolean isValid = jwtUtil.validateToken(token, "test-user");
        assertTrue(isValid);
    }

    @Test
    void validateToken_invalidUsername_returnsFalse() {
        String token = jwtUtil.generateToken("test-user");
        boolean isValid = jwtUtil.validateToken(token, "someoneElse");
        assertFalse(isValid);
    }

    @Test
    void isTokenExpired_expiredToken_returnsTrue() throws Exception {
        String expiredToken = Jwts.builder()
                .setSubject("test-user")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(jwtUtil.generateKey("my-test-secret-key-1234567890123456"))
                .compact();

        assertTrue(jwtUtil.isTokenExpired(expiredToken));
    }

    @Test
    void extractExpiration_validToken_returnsFutureDate() {
        String token = jwtUtil.generateToken("test-user");
        Date expiration = jwtUtil.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }
}
