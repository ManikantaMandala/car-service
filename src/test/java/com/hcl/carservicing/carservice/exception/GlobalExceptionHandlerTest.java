package com.hcl.carservicing.carservice.exception;

import com.hcl.carservicing.carservice.controller.controlleradvice.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleValidationException() {
        ValidationException exception = new ValidationException("Validation failed");

        ResponseEntity<String> response = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Validation failed", response.getBody());
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<String> response = globalExceptionHandler.handleIllegalArgument(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody());
    }


    @Test
    void testHandleElementNotFoundException() {
        ElementNotFoundException exception = new ElementNotFoundException("Element not found");

        ResponseEntity<String> response = globalExceptionHandler.handleElementNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Element not found", response.getBody());
    }

    @Test
    void testHandleUsernameNotFoundException() {
        UsernameNotFoundException exception = new UsernameNotFoundException("Username not found");

        ResponseEntity<String> response = globalExceptionHandler.handleUsernameNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Username not found", response.getBody());
    }
}
