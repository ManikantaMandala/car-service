package com.hcl.carservicing.carservice.dto;

import com.hcl.carservicing.carservice.enums.Gender;
import com.hcl.carservicing.carservice.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppUserDtoTest {

    private AppUserDTO appUserDTO;

    @BeforeEach
    void setUp() {
        appUserDTO = new AppUserDTO();
    }

    @Test
    void testGetAndSetFirstName() {
        String firstName = "John";
        appUserDTO.setFirstName(firstName);
        assertEquals(firstName, appUserDTO.getFirstName());
    }

    @Test
    void testGetAndSetLastName() {
        String lastName = "Doe";
        appUserDTO.setLastName(lastName);
        assertEquals(lastName, appUserDTO.getLastName());
    }

    @Test
    void testGetAndSetAge() {
        Integer age = 25;
        appUserDTO.setAge(age);
        assertEquals(age, appUserDTO.getAge());
    }

    @Test
    void testGetAndSetGender() {
        Gender gender = Gender.MALE;
        appUserDTO.setGender(gender);
        assertEquals(gender, appUserDTO.getGender());
    }

    @Test
    void testGetAndSetContactNumber() {
        String contactNumber = "1234567890";
        appUserDTO.setContactNumber(contactNumber);
        assertEquals(contactNumber, appUserDTO.getContactNumber());
    }

    @Test
    void testGetAndSetUsername() {
        String username = "johndoe";
        appUserDTO.setUsername(username);
        assertEquals(username, appUserDTO.getUsername());
    }

    @Test
    void testGetAndSetPassword() {
        String password = "Password123!";
        appUserDTO.setPassword(password);
        assertEquals(password, appUserDTO.getPassword());
    }

    @Test
    void testGetAndSetRole() {
        UserRole role = UserRole.USER;
        appUserDTO.setRole(role);
        assertEquals(role, appUserDTO.getRole());
    }

    @Test
    void testGetAndSetAvailable() {
        Boolean available = false;
        appUserDTO.setAvailable(available);
        assertEquals(available, appUserDTO.getAvailable());
    }

    @Test
    void testValidAppUserDTO() {
        appUserDTO.setFirstName("John");
        appUserDTO.setLastName("Doe");
        appUserDTO.setAge(25);
        appUserDTO.setGender(Gender.MALE);
        appUserDTO.setContactNumber("1234567890");
        appUserDTO.setUsername("johndoe");
        appUserDTO.setPassword("Password123!");
        appUserDTO.setRole(UserRole.USER);
        appUserDTO.setAvailable(true);

        assertEquals("John", appUserDTO.getFirstName());
        assertEquals("Doe", appUserDTO.getLastName());
        assertEquals(25, appUserDTO.getAge());
        assertEquals(Gender.MALE, appUserDTO.getGender());
        assertEquals("1234567890", appUserDTO.getContactNumber());
        assertEquals("johndoe", appUserDTO.getUsername());
        assertEquals("Password123!", appUserDTO.getPassword());
        assertEquals(UserRole.USER, appUserDTO.getRole());
        assertTrue(appUserDTO.getAvailable());
    }
}

