package com.hcl.carservicing.carservice.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginDtoTest {

    private UserLoginDTO userLoginDTO;

    @BeforeEach
    void setUp() {
        userLoginDTO = new UserLoginDTO();
    }

    @Test
    void testGetAndSetJwt() {
        String jwt = "sampleJwtToken";
        userLoginDTO.setJwt(jwt);
        assertEquals(jwt, userLoginDTO.getJwt());
    }

    @Test
    void testGetAndSetExpirationTime() {
        Date expirationTime = new Date();
        userLoginDTO.setExpirationTime(expirationTime);
        assertEquals(expirationTime, userLoginDTO.getExpirationTime());
    }

    @Test
    void testConstructor() {
        String jwt = "sampleJwtToken";
        Date expirationTime = new Date();

        userLoginDTO = new UserLoginDTO(jwt, expirationTime);

        assertEquals(jwt, userLoginDTO.getJwt());
        assertEquals(expirationTime, userLoginDTO.getExpirationTime());
    }

    @Test
    void testEmptyConstructor() {
        userLoginDTO = new UserLoginDTO();
        assertNull(userLoginDTO.getJwt());
        assertNull(userLoginDTO.getExpirationTime());
    }

    @Test
    void testSetJwt() {
        userLoginDTO.setJwt("newJwtToken");
        assertEquals("newJwtToken", userLoginDTO.getJwt());
    }

    @Test
    void testSetExpirationTime() {
        Date newExpirationTime = new Date();
        userLoginDTO.setExpirationTime(newExpirationTime);
        assertEquals(newExpirationTime, userLoginDTO.getExpirationTime());
    }

    @Test
    void testValidUserLoginDTO() {
        userLoginDTO.setJwt("validJwtToken");
        userLoginDTO.setExpirationTime(new Date());

        assertEquals("validJwtToken", userLoginDTO.getJwt());
        assertNotNull(userLoginDTO.getExpirationTime());
    }

    @Test
    void testInvalidUserLoginDTO() {
        userLoginDTO.setJwt(null);
        userLoginDTO.setExpirationTime(null);

        assertNull(userLoginDTO.getJwt());
        assertNull(userLoginDTO.getExpirationTime());
    }

    @Test
    void testJwtNotNull() {
        userLoginDTO.setJwt("jwtToken");
        assertNotNull(userLoginDTO.getJwt());
    }

    @Test
    void testExpirationTimeNotNull() {
        userLoginDTO.setExpirationTime(new Date());
        assertNotNull(userLoginDTO.getExpirationTime());
    }
}

