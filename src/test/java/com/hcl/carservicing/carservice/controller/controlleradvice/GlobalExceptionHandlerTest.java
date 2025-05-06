package com.hcl.carservicing.carservice.controller.controlleradvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hcl.carservicing.carservice.exception.DeliveryBoyNotAvailable;
import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import org.junit.jupiter.api.Test;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleValidationExceptions_shouldReturnBadRequest() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "errorMessage");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<Map<String, String>> responseEntity = globalExceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("errorMessage", responseEntity.getBody().get("fieldName"));
    }

    @Test
    void handleValidationException_shouldReturnConflict() {
        // Arrange
        ValidationException ex = new ValidationException("Validation failed");

        // Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleValidationException(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Validation failed", responseEntity.getBody());
    }

    @Test
    void handleIllegalArgument_shouldReturnConflict() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");

        // Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleIllegalArgument(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Illegal argument", responseEntity.getBody());
    }

    // TODO: now how to test this code
//    @Test
//    void handleInvalidEnumValue_withEnum_shouldReturnBadRequest() {
//        // Arrange
//        InvalidFormatException ex = mock(InvalidFormatException.class);
//        JsonMappingException.Reference reference = mock(JsonMappingException.Reference.class);
//
//        when(ex.getTargetType()).thenReturn(eq(any())); // Use eq() here
//        when(ex.getPath()).thenReturn(List.of(reference));
//        when(reference.getFieldName()).thenReturn("testEnumField");
//        when(ex.getValue()).thenReturn("INVALID_VALUE");
//
//        // Act
//        ResponseEntity<String> responseEntity = globalExceptionHandler.handleInvalidEnumValue(ex);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        String expectedMessage = "Invalid value 'INVALID_VALUE' for field 'testEnumField'. Allowed values are: VALUE1, VALUE2";
//        assertEquals(expectedMessage, responseEntity.getBody());
//    }
//
//    @Test
//    void handleInvalidEnumValue_withoutEnum_shouldReturnBadRequest() {
//        // Arrange
//        InvalidFormatException ex = mock(InvalidFormatException.class);
//        JsonMappingException.Reference reference = mock(JsonMappingException.Reference.class);
//        when(ex.getTargetType()).thenReturn(any()); // Simulate not being an enum
//        when(ex.getPath()).thenReturn(List.of(reference));
//
//        // Act
//        ResponseEntity<String> responseEntity = globalExceptionHandler.handleInvalidEnumValue(ex);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertEquals("Invalid request format", responseEntity.getBody());
//    }
//
//    // Mock enum for testing handleInvalidEnumValue
//    private enum TestEnum {
//        VALUE1, VALUE2
//    }

    @Test
    void handleElementNotFoundException_shouldReturnNotFound() {
        // Arrange
        ElementNotFoundException ex = new ElementNotFoundException("Element not found");

        // Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleElementNotFoundException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Element not found", responseEntity.getBody());
    }

    @Test
    void handleElementAlreadyException_shouldReturnConflict() {
        // Arrange
        ElementAlreadyExistException ex = new ElementAlreadyExistException("Element already exists");

        // Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleElementAlreadyException(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Element already exists", responseEntity.getBody());
    }

    @Test
    void handleUsernameNotFoundException_shouldReturnNotFound() {
        // Arrange
        UsernameNotFoundException ex = new UsernameNotFoundException("Username not found");

        // Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleUsernameNotFoundException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Username not found", responseEntity.getBody());
    }

    @Test
    void handleDeliveryBoyNotAvailableException_shouldReturnConflict(){
        //Arrange
        DeliveryBoyNotAvailable ex = new DeliveryBoyNotAvailable("Delivery boy not available");

        //Act
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleDeliveryBoyNotAvailableException(ex);

        //Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Delivery boy not available", responseEntity.getBody());
    }


}

