package com.hcl.carservicing.carservice.entity;

import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRequestTest {

    private Validator validator;
    private ServiceRequest serviceRequest;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        serviceRequest = new ServiceRequest();
    }

    @Test
    void testStartDateNotNull() {
        serviceRequest.setStartDate(null);
        serviceRequest.setEndDate(LocalDate.now().plusDays(1));
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testStartDateFutureOrPresent() {
        serviceRequest.setStartDate(LocalDate.now().minusDays(1));
        serviceRequest.setEndDate(LocalDate.now().plusDays(1));
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testEndDateNotNull() {
        serviceRequest.setStartDate(LocalDate.now());
        serviceRequest.setEndDate(null);
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testEndDateFutureOrPresent() {
        serviceRequest.setStartDate(LocalDate.now());
        serviceRequest.setEndDate(LocalDate.now().minusDays(1));
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testUserNotNull() {
        serviceRequest.setStartDate(LocalDate.now());
        serviceRequest.setEndDate(LocalDate.now().plusDays(1));
        serviceRequest.setUser(null);
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testServiceNotNull() {
        serviceRequest.setStartDate(LocalDate.now());
        serviceRequest.setEndDate(LocalDate.now().plusDays(1));
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(null);
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testValidServiceRequest() {
        serviceRequest.setStartDate(LocalDate.now());
        serviceRequest.setEndDate(LocalDate.now().plusDays(1));
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidServiceRequest() {
        serviceRequest.setStartDate(LocalDate.now().minusDays(1));
        serviceRequest.setEndDate(LocalDate.now().minusDays(2));
        serviceRequest.setUser(null);
        serviceRequest.setService(null);
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }

    @Test
    void testStartDateAndEndDateValid() {
        serviceRequest.setStartDate(LocalDate.now());
        serviceRequest.setEndDate(LocalDate.now().plusDays(1));
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testStartDateAndEndDateInvalid() {
        serviceRequest.setStartDate(LocalDate.now().minusDays(1));
        serviceRequest.setEndDate(LocalDate.now().minusDays(2));
        serviceRequest.setUser(new AppUser());
        serviceRequest.setService(new ServiceCenterServiceType());
        Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(serviceRequest);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }
}
