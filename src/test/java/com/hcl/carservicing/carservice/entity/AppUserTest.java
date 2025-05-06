package com.hcl.carservicing.carservice.entity;

import com.hcl.carservicing.carservice.model.AppUser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class AppUserTest {

    private Validator validator;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        appUser = new AppUser();
    }

    @Test
    void testFirstNameNotBlank() {
        appUser.setFirstName("");
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testFirstNameSize() {
        appUser.setFirstName("A");
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testLastNameNotBlank() {
        appUser.setLastName("");
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testLastNameSize() {
        appUser.setLastName("A");
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAgeNotNull() {
        appUser.setAge(null);
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAgeMin() {
        appUser.setAge(17);
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAgeMax() {
        appUser.setAge(101);
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testContactNumberPattern() {
        appUser.setContactNumber("12345");
        Set<ConstraintViolation<AppUser>> violations = validator.validate(appUser);
        assertFalse(violations.isEmpty());
    }
}