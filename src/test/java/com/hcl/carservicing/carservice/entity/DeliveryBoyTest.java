package com.hcl.carservicing.carservice.entity;

import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryBoyTest {

    private Validator validator;
    private DeliveryBoy deliveryBoy;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        deliveryBoy = new DeliveryBoy();
    }

    @Test
    void testNameNotBlank() {
        deliveryBoy.setName("");
        deliveryBoy.setContactNumber("1234567890");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNameSizeTooShort() {
        deliveryBoy.setName("A");
        deliveryBoy.setContactNumber("1234567890");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNameSizeTooLong() {
        deliveryBoy.setName("A".repeat(101));
        deliveryBoy.setContactNumber("1234567890");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNameValid() {
        deliveryBoy.setName("John Doe");
        deliveryBoy.setContactNumber("1234567890");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testContactNumberNotBlank() {
        deliveryBoy.setName("John Doe");
        deliveryBoy.setContactNumber("");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testContactNumberPatternInvalid() {
        deliveryBoy.setName("John Doe");
        deliveryBoy.setContactNumber("12345");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testContactNumberPatternValid() {
        deliveryBoy.setName("John Doe");
        deliveryBoy.setContactNumber("1234567890");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidDeliveryBoy() {
        deliveryBoy.setName("John Doe");
        deliveryBoy.setContactNumber("1234567890");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidDeliveryBoy() {
        deliveryBoy.setName("A");
        deliveryBoy.setContactNumber("12345");
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testValidDeliveryBoyWithServiceCenter() {
        deliveryBoy.setName("John Doe");
        deliveryBoy.setContactNumber("1234567890");
        deliveryBoy.setServiceCenter(new ServiceCenter());
        Set<ConstraintViolation<DeliveryBoy>> violations = validator.validate(deliveryBoy);
        assertTrue(violations.isEmpty());
    }
}
