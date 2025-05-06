package com.hcl.carservicing.carservice.config;

import com.hcl.carservicing.carservice.dao.service.AppUserDaoService;
import com.hcl.carservicing.carservice.enums.Gender;
import com.hcl.carservicing.carservice.enums.UserRole;
import com.hcl.carservicing.carservice.model.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private AppUserDaoService appUserDaoService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private String username;
    private String password;

    private User user;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        username = "test-user";
        password = "test-user-password";

        appUser = new AppUser();
        appUser.setFirstName("test-user-first-name");
        appUser.setLastName("test-user-last-name");
        appUser.setAge(21);
        appUser.setGender(Gender.MALE);
        appUser.setContactNumber("1234567890");
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setRole(UserRole.USER);
        appUser.setCreatedAt(LocalDateTime.now());
        appUser.setServiceRequests(new ArrayList<>());

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(appUser.getRole().toString());

        user = new User(username, password, authorities);
    }

    @Test
    void loadUserByUsername() {
        when(appUserDaoService.findByUsername(username)).thenReturn(appUser);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(user, userDetails);
    }

    @Test
    void loadUserByUsername_userNotFound_shouldThrowException() {
        when(appUserDaoService.findByUsername(username)).thenThrow(new UsernameNotFoundException("User not found"));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername(username));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void loadUserByUsername_userIsNull_shouldThrowException() {
        when(appUserDaoService.findByUsername(username)).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                customUserDetailsService.loadUserByUsername(username));
    }

}