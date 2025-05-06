package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppUserDaoServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserDaoServiceImpl appUserDaoService;

    @Test
    void findByUsername_userFound_returnsAppUser() {
        String username = "testuser";
        AppUser expectedUser = new AppUser();
        expectedUser.setUsername(username);
        Optional<AppUser> userOptional = Optional.of(expectedUser);

        when(appUserRepository.findByUsername(username)).thenReturn(userOptional);

        AppUser actualUser = appUserDaoService.findByUsername(username);

        assertEquals(expectedUser, actualUser);
        verify(appUserRepository).findByUsername(username);
    }

    @Test
    void findByUsername_userNotFound_throwsElementNotFoundException() {
        String username = "nonexistentuser";
        Optional<AppUser> userOptional = Optional.empty();

        when(appUserRepository.findByUsername(username)).thenReturn(userOptional);

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            appUserDaoService.findByUsername(username);
        });
        assertEquals("User not found with username: " + username, exception.getMessage());
        verify(appUserRepository).findByUsername(username);
    }

    @Test
    void throwIfUsernameExists_usernameExists_throwsElementAlreadyExistException() {
        String username = "existinguser";
        AppUser existingUser = new AppUser();
        existingUser.setUsername(username);
        Optional<AppUser> userOptional = Optional.of(existingUser);

        when(appUserRepository.findByUsername(username)).thenReturn(userOptional);

        ElementAlreadyExistException exception = assertThrows(ElementAlreadyExistException.class, () -> {
            appUserDaoService.throwIfUsernameExists(username);
        });
        assertEquals("User found with username: " + username, exception.getMessage());
        verify(appUserRepository).findByUsername(username);
    }

    @Test
    void throwIfUsernameExists_usernameDoesNotExist_doesNotThrowException() {
        String username = "newuser";
        Optional<AppUser> userOptional = Optional.empty();

        when(appUserRepository.findByUsername(username)).thenReturn(userOptional);

        appUserDaoService.throwIfUsernameExists(username);

        verify(appUserRepository).findByUsername(username);
    }

    @Test
    void throwIfContactExists_contactExists_throwsElementAlreadyExistException() {
        String contactNumber = "1234567890";
        AppUser existingUser = new AppUser();
        existingUser.setContactNumber(contactNumber);
        Optional<AppUser> userOptional = Optional.of(existingUser);

        when(appUserRepository.findByContactNumber(contactNumber)).thenReturn(userOptional);

        ElementAlreadyExistException exception = assertThrows(ElementAlreadyExistException.class, () -> {
            appUserDaoService.throwIfContactExists(contactNumber);
        });
        assertEquals("User found with contact number: " + contactNumber, exception.getMessage());
        verify(appUserRepository).findByContactNumber(contactNumber);
    }

    @Test
    void throwIfContactExists_contactDoesNotExist_doesNotThrowException() {
        String contactNumber = "9876543210";
        Optional<AppUser> userOptional = Optional.empty();

        when(appUserRepository.findByContactNumber(contactNumber)).thenReturn(userOptional);

        appUserDaoService.throwIfContactExists(contactNumber);

        verify(appUserRepository).findByContactNumber(contactNumber);
    }

    @Test
    void save_validUser_returnsSavedUser() {
        AppUser userToSave = new AppUser();
        userToSave.setUsername("testuser");
        userToSave.setContactNumber("1234567890");

        AppUser savedUser = new AppUser();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setContactNumber("1234567890");

        when(appUserRepository.save(userToSave)).thenReturn(savedUser);

        AppUser result = appUserDaoService.save(userToSave);

        assertEquals(savedUser, result);
        verify(appUserRepository).save(userToSave);
    }
}
