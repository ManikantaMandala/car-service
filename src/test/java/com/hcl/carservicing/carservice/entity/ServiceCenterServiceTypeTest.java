package com.hcl.carservicing.carservice.entity;

import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceCenterServiceTypeTest {

    private Validator validator;
    private ServiceCenterServiceType serviceCenterServiceType;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        serviceCenterServiceType = new ServiceCenterServiceType();
    }

    @Test
    void testCostNotNull() {
        serviceCenterServiceType.setCost(null);
        Set<ConstraintViolation<ServiceCenterServiceType>> violations= validator.validate(serviceCenterServiceType);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testCostMinNegative() {
        serviceCenterServiceType.setCost(-1.0);
        Set<ConstraintViolation<ServiceCenterServiceType>> violations = validator.validate(serviceCenterServiceType);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testCostMinZero() {
        serviceCenterServiceType.setCost(0.0);
        Set<ConstraintViolation<ServiceCenterServiceType>> violations = validator.validate(serviceCenterServiceType);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCostValid() {
        serviceCenterServiceType.setCost(100.0);
        Set<ConstraintViolation<ServiceCenterServiceType>> violations = validator.validate(serviceCenterServiceType);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidServiceCenterServiceType() {
        serviceCenterServiceType.setCost(100.0);
        serviceCenterServiceType.setServiceCenter(new ServiceCenter());
        serviceCenterServiceType.setServiceType(new ServiceType());
        Set<ConstraintViolation<ServiceCenterServiceType>> violations = validator.validate(serviceCenterServiceType);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidServiceCenter() {
        serviceCenterServiceType.setCost(100.0);
        serviceCenterServiceType.setServiceCenter(new ServiceCenter());
        Set<ConstraintViolation<ServiceCenterServiceType>> violations = validator.validate(serviceCenterServiceType);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidServiceType() {
        serviceCenterServiceType.setCost(100.0);
        serviceCenterServiceType.setServiceType(new ServiceType());
        Set<ConstraintViolation<ServiceCenterServiceType>> violations = validator.validate(serviceCenterServiceType);
        assertTrue(violations.isEmpty());
    }
}
