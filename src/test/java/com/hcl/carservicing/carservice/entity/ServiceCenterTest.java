package com.hcl.carservicing.carservice.entity;

import com.hcl.carservicing.carservice.model.ServiceCenter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServiceCenterTest {

    private Validator validator;
    private ServiceCenter serviceCenter;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        serviceCenter = new ServiceCenter();
    }

    @Test
    void testNameNotBlank() {
        serviceCenter.setName("");
        serviceCenter.setAddress("123 Main Street, City, Country");
        serviceCenter.setRating(2.6);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    void testNameSizeTooShort() {
        serviceCenter.setName("ABC");
        serviceCenter.setAddress("123 Main Street, City, Country");
        serviceCenter.setRating(4.5);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testNameSizeTooLong() {
        serviceCenter.setName("A".repeat(31));
        serviceCenter.setAddress("123 Main Street, City, Country");
        serviceCenter.setRating(4.5);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testNameValid() {
        serviceCenter.setName("AutoFix");
        serviceCenter.setAddress("123 Main Street, City, Country");
        serviceCenter.setRating(4.5);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testAddressNotBlank() {
        serviceCenter.setName("AutoFix");
        serviceCenter.setAddress("");
        serviceCenter.setRating(4.5);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    void testAddressSizeTooShort() {
        serviceCenter.setName("AutoFix");
        serviceCenter.setAddress("Short");
        serviceCenter.setRating(4.5);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testAddressSizeTooLong() {
        serviceCenter.setName("AutoFix");
        serviceCenter.setAddress("A".repeat(256));
        serviceCenter.setRating(4.5);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testAddressValid() {
        serviceCenter.setName("AutoFix");
        serviceCenter.setAddress("123 Main Street, City, Country");
        serviceCenter.setRating(4.5);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testRatingNotNull() {
        serviceCenter.setName("AutoFix");
        serviceCenter.setAddress("123 Main Street, City, Country");
        serviceCenter.setRating(null);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testRatingMin() {
        serviceCenter.setName("AutoFix");
        serviceCenter.setAddress("123 Main Street, City, Country");
        serviceCenter.setRating(3.2);
        Set<ConstraintViolation<ServiceCenter>> violations = validator.validate(serviceCenter);
        assertTrue(violations.isEmpty());
        assertEquals(0, violations.size());
    }
}
