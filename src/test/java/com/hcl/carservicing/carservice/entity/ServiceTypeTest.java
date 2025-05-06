package com.hcl.carservicing.carservice.entity;

import com.hcl.carservicing.carservice.model.ServiceType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTypeTest {

    private Validator validator;
    private ServiceType serviceType;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        serviceType = new ServiceType();
    }

    @Test
    void testServiceNameNotBlank() {
        serviceType.setServiceName("");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(3, violations.size());
    }

    @Test
    void testServiceNameSizeTooShort() {
        serviceType.setServiceName("AB");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(2, violations.size());
    }

    @Test
    void testServiceNameSizeTooLong() {
        serviceType.setServiceName("A".repeat(100));
        serviceType.setDescription("A".repeat(250));
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(0, violations.size());
    }

    @Test
    void testServiceNameValid() {
        serviceType.setServiceName("Oil Change");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(1, violations.size());
    }

    @Test
    void testDescriptionNotBlank() {
        serviceType.setDescription("");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(2, violations.size());
    }

    @Test
    void testDescriptionSizeTooLong() {
        serviceType.setDescription("A".repeat(256));
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(2, violations.size());
    }

    @Test
    void testDescriptionValid() {
        serviceType.setDescription("Complete oil change service including filter replacement.");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(1, violations.size());
    }

    @Test
    void testValidServiceType() {
        serviceType.setServiceName("Tire Rotation");
        serviceType.setDescription("Rotating tires to ensure even wear.");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidServiceType() {
        serviceType.setServiceName("A");
        serviceType.setDescription("");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(2, violations.size());
    }

    @Test
    void testServiceNameAndDescriptionValid() {
        serviceType.setServiceName("Brake Inspection");
        serviceType.setDescription("Inspection of brake pads, rotors, and fluid levels.");
        Set<ConstraintViolation<ServiceType>> violations = validator.validate(serviceType);
        assertEquals(0, violations.size());
    }
}
