package com.hcl.carservicing.carservice.controller.controlleradvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hcl.carservicing.carservice.exception.DeliveryBoyNotAvailable;
import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import org.junit.jupiter.api.Test;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidationExceptions_shouldReturnBadRequestWithFieldErrors() {
        FieldError fieldError = new FieldError("object", "field", "must not be null");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("must not be null", response.getBody().get("field"));
    }

    @Test
    void handleValidationException_shouldReturnConflict() {
        ValidationException ex = new ValidationException("validation failed");

        ResponseEntity<String> response = handler.handleValidationException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("validation failed", response.getBody());
    }

    @Test
    void handleIllegalArgument_shouldReturnConflict() {
        IllegalArgumentException ex = new IllegalArgumentException("illegal argument");

        ResponseEntity<String> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("illegal argument", response.getBody());
    }

    enum TestEnum { A, B }

    @Test
    void handleInvalidEnumValue_shouldReturnBadRequestWithEnumValues() {
        JsonMappingException.Reference ref = new JsonMappingException.Reference("path", "status");
        InvalidFormatException ex = new InvalidFormatException(null, (String)null, "C", TestEnum.class);
        ex.prependPath(ref);

        ResponseEntity<String> response = handler.handleInvalidEnumValue(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Invalid value 'C' for field 'status'"));
        assertTrue(response.getBody().contains("A"));
        assertTrue(response.getBody().contains("B"));
    }

    @Test
    void handleElementNotFoundException_shouldReturnNotFound() {
        ElementNotFoundException ex = new ElementNotFoundException("not found");

        ResponseEntity<String> response = handler.handleElementNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("not found", response.getBody());
    }

    @Test
    void handleElementAlreadyException_shouldReturnConflict() {
        ElementAlreadyExistException ex = new ElementAlreadyExistException("already exists");

        ResponseEntity<String> response = handler.handleElementAlreadyException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("already exists", response.getBody());
    }

    @Test
    void handleUsernameNotFoundException_shouldReturnNotFound() {
        UsernameNotFoundException ex = new UsernameNotFoundException("user not found");

        ResponseEntity<String> response = handler.handleUsernameNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("user not found", response.getBody());
    }

    @Test
    void handleDeliveryBoyNotAvailableException_shouldReturnConflict() {
        DeliveryBoyNotAvailable ex = new DeliveryBoyNotAvailable("no delivery boy");

        ResponseEntity<String> response = handler.handleDeliveryBoyNotAvailableException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("no delivery boy", response.getBody());
    }
}