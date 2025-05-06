package com.hcl.carservicing.carservice.service.impl;

import com.hcl.carservicing.carservice.config.CustomUserDetailsService;
import com.hcl.carservicing.carservice.config.JwtUtil;
import com.hcl.carservicing.carservice.dao.service.AppUserDaoService;
import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import com.hcl.carservicing.carservice.enums.Gender;
import com.hcl.carservicing.carservice.enums.UserRole;
import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.model.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private AppUserDaoService appUserDaoService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private UserServiceImpl userService;

    private AppUserDTO appUserDTO;
    private AppUser appUser;
    private UserLoginDTO userLoginDTO;

    @BeforeEach
    void setUp() {

        appUserDTO = new AppUserDTO();
        appUserDTO.setFirstName("Test");
        appUserDTO.setLastName("User");
        appUserDTO.setAge(25);
        appUserDTO.setGender(Gender.MALE);
        appUserDTO.setContactNumber("1234567890");
        appUserDTO.setUsername("testuser");
        appUserDTO.setPassword("password");
        appUserDTO.setRole(UserRole.USER);

        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setFirstName("Test");
        appUser.setLastName("User");
        appUser.setAge(25);
        appUser.setGender(Gender.MALE);
        appUser.setContactNumber("1234567890");
        appUser.setUsername("testuser");
        appUser.setPassword("password");
        appUser.setRole(UserRole.USER);

        userLoginDTO = new UserLoginDTO("jwtToken", new Date());
    }

    @Test
    void testRegister() {
        when(appUserDaoService.save(any(AppUser.class))).thenReturn(appUser);

        userService.register(appUserDTO);

        verify(appUserDaoService, times(1)).save(any(AppUser.class));
    }

    @Test
    void testRegister_UsernameExists() {
        doThrow(ElementAlreadyExistException.class).when(appUserDaoService).throwIfUsernameExists("testuser");

        assertThrows(ElementAlreadyExistException.class, () -> {
            userService.register(appUserDTO);
        });
    }

    @Test
    void testRegister_ContactNumberExists() {
        doThrow(ElementAlreadyExistException.class).when(appUserDaoService).throwIfContactExists("1234567890");

        assertThrows(ElementAlreadyExistException.class, () -> {
            userService.register(appUserDTO);
        });
    }

    @Test
    void testLogin() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUsername("testuser");
        appUserDTO.setPassword("password");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                appUserDTO.getUsername(), appUserDTO.getPassword(), authorities);

        UserDetails userDetails = new User(appUserDTO.getUsername(), appUserDTO.getPassword(), authorities);
        String token = "valid-token";

        when(authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(appUserDTO.getUsername(), appUserDTO.getPassword())))
                .thenReturn(authentication);

        when(customUserDetailsService.loadUserByUsername(appUserDTO.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails.getUsername())).thenReturn(token);
        when(jwtUtil.extractExpiration(token)).thenReturn(new Date());

        UserLoginDTO login = userService.login(appUserDTO.getUsername(), appUserDTO.getPassword());

        assertNotNull(login);
    }

    @Test
    void testLogin_AuthenticationFailure() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUsername("testuser");
        appUserDTO.setPassword("wrongpassword");

        when(authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(appUserDTO.getUsername(), appUserDTO.getPassword())))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> userService.login(appUserDTO.getUsername(), appUserDTO.getPassword()));
    }

    @Test
    void testLogin_throwIllegalArgumentException() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUsername("testuser");
        appUserDTO.setPassword("wrongpassword");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        assertThrows(IllegalArgumentException.class,
                () -> userService.login(appUserDTO.getUsername(), appUserDTO.getPassword()));
    }

}