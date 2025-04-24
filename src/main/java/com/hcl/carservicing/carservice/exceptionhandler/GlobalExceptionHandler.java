package com.hcl.carservicing.carservice.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation exceptions thrown by @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMessages.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    // Handle custom ValidationException (UserId already exists)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidEnumValue(InvalidFormatException ex) {
        if (ex.getTargetType().isEnum()) {
            String fieldName = ex.getPath().get(0).getFieldName();
            String invalidValue = ex.getValue().toString();
            String message = String.format("Invalid value '%s' for field '%s'. Allowed values are: %s",
                    invalidValue,
                    fieldName,
                    String.join(", ", getEnumValues(ex.getTargetType())));
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Invalid request format", HttpStatus.BAD_REQUEST);
    }

    private String[] getEnumValues(Class<?> enumClass) {
        Object[] constants = enumClass.getEnumConstants();
        String[] values = new String[constants.length];
        for (int i = 0; i < constants.length; i++) {
            values[i] = constants[i].toString();
        }
        return values;
    }
}

