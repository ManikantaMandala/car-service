package com.hcl.carservicing.carservice.repository;

import com.hcl.carservicing.carservice.enums.UserRole;
import com.hcl.carservicing.carservice.model.AppUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserRepositoryTest {

    String firstName;
    String lastName;
    String username;
    int age;
    String gender;
    UserRole role;
    String password;
    String contactNumber;
    LocalDateTime createdAt;

    @Autowired
    AppUserRepository appUserRepository;

    @BeforeEach
    void setUp() {
        firstName = "test_user_first_name";
        lastName = "test_user_last_name";
        username = "test_user_name";
        age = 21;
        gender = "Male";
        role = UserRole.USER;
        password = "TestUser!21";
        contactNumber = "1234123412";
        createdAt = LocalDateTime.now();

        AppUser appUser = new AppUser();

        appUser.setUsername(username);
        appUser.setAge(age);
        appUser.setGender(gender);
        appUser.setRole(role);
        appUser.setPassword(password);
        appUser.setContactNumber(contactNumber);
        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);
        appUser.setCreatedAt(createdAt);

        appUserRepository.save(appUser);
    }

    @Test
    void findByUsernameReturnsAppUser() {
        Optional<AppUser> userOptional = appUserRepository.findByUsername(username);

        assertThat(userOptional).isPresent();
        assertThat(userOptional.get().getUsername()).isEqualTo(username);
    }

    @Test
    void findByUsernameReturnsEmpty() {
        Optional<AppUser> userOptional = appUserRepository.findByUsername("invalid_username");

        assertThat(userOptional).isEmpty();
    }

    @Test
    void findByContactNumberReturnsAppUser() {
        Optional<AppUser> userOptional = appUserRepository.findByContactNumber(contactNumber);

        assertThat(userOptional).isPresent();
        assertThat(userOptional.get().getContactNumber()).isEqualTo(contactNumber);
    }

    @Test
    void findByContactNumberReturnsEmpty() {
        Optional<AppUser> userOptional = appUserRepository.findByContactNumber("invalid_contact_number");

        assertThat(userOptional).isEmpty();
    }
}