package com.hcl.carservicing.carservice.config;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Value("${jwt.secret}") //  It's crucial to have this value in your application.properties or application.yml
    private String secretKey;

    @BeforeEach
    public void setUp() {
        //  Manually set the secretKey, since @Value injection might not work as expected in a unit test.
        ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKey);
    }

    @Test
    public void generateToken_validUsername_returnsNonEmptyToken() {
        // Arrange
        String username = "testuser";

        // Act
        String token = jwtUtil.generateToken(username);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void extractUsername_validToken_returnsCorrectUsername() {
        // Arrange
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Act
        String extractedUsername = jwtUtil.extractUsername(token);

        // Assert
        assertEquals(username, extractedUsername);
    }

    @Test
    public void validateToken_validTokenAndUsername_returnsTrue() {
        // Arrange
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Act
        boolean isValid = jwtUtil.validateToken(token, username);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void validateToken_invalidUsername_returnsFalse() {
        // Arrange
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        String differentUsername = "differentuser";

        // Act
        boolean isValid = jwtUtil.validateToken(token, differentUsername);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void validateToken_expiredToken_returnsFalse() throws InterruptedException {
        // Arrange
        String username = "testuser";
        long expirationTime = 1000;
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);
        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(jwtUtil.generateKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

        Thread.sleep(expirationTime + 1000);

        // Act
        boolean isValid = jwtUtil.validateToken(expiredToken, username);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isTokenExpired_expiredToken_returnsTrue() throws InterruptedException {
        // Arrange
        String username = "testuser";
        long expirationTime = 1000;
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(jwtUtil.generateKey(secretKey), SignatureAlgorithm.HS256)
                .compact();

        Thread.sleep(expirationTime+1000);

        // Act
        boolean isExpired = jwtUtil.isTokenExpired(expiredToken);

        // Assert
        assertTrue(isExpired);
    }

    @Test
    public void isTokenExpired_validToken_returnsFalse() {
        // Arrange
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Act
        boolean isExpired = jwtUtil.isTokenExpired(token);

        // Assert
        assertFalse(isExpired);
    }

    @Test
    public void extractExpiration_validToken_returnsCorrectExpirationDate() {
        // Arrange
        String username = "testuser";
        long expirationTime = 3600000;
        Date expectedExpiration = new Date(System.currentTimeMillis() + expirationTime);
        String token = jwtUtil.generateToken(username);

        // Act
        Date actualExpiration = jwtUtil.extractExpiration(token);

        // Assert
        assertEquals(expectedExpiration.getTime(), actualExpiration.getTime());
    }
}
