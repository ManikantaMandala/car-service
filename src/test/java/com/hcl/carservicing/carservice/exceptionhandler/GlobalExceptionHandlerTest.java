package com.hcl.carservicing.carservice.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.ValidationException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

//    @Test
//    void testHandleMethodArgumentNotValidException() {
//        // Simulate MethodArgumentNotValidException
//        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, null);
//
//        // Call the method
//        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals(0, response.getBody().size()); // No field error, so empty response body
//    }

    @Test
    void testHandleValidationException() {
        // Simulate ValidationException
        ValidationException exception = new ValidationException("Validation failed");

        // Call the method
        ResponseEntity<String> response = globalExceptionHandler.handleValidationException(exception);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Validation failed", response.getBody());
    }

    @Test
    void testHandleIllegalArgumentException() {
        // Simulate IllegalArgumentException
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        // Call the method
        ResponseEntity<String> response = globalExceptionHandler.handleIllegalArgument(exception);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody());
    }

//    @Test
//    void testHandleInvalidFormatException() {
//        // Simulate InvalidFormatException
//        InvalidFormatException exception = new InvalidFormatException("Invalid enum value", null, null);
//
//        // Call the method
//        ResponseEntity<String> response = globalExceptionHandler.handleInvalidEnumValue(exception);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Invalid request format", response.getBody()); // In a real-world scenario, you may enhance this further.
//    }

    @Test
    void testHandleElementNotFoundException() {
        // Simulate ElementNotFoundException
        ElementNotFoundException exception = new ElementNotFoundException("Element not found");

        // Call the method
        ResponseEntity<String> response = globalExceptionHandler.handleElementNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Element not found", response.getBody());
    }

    @Test
    void testHandleUsernameNotFoundException() {
        // Simulate UsernameNotFoundException
        UsernameNotFoundException exception = new UsernameNotFoundException("Username not found");

        // Call the method
        ResponseEntity<String> response = globalExceptionHandler.handleUsernameNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Username not found", response.getBody());
    }
}
