package com.hcl.carservicing.carservice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import com.hcl.carservicing.carservice.config.CustomUserDetailsService;
import com.hcl.carservicing.carservice.config.JwtUtil;
import com.hcl.carservicing.carservice.config.SecurityConfig;
import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import com.hcl.carservicing.carservice.enums.UserRole;
import com.hcl.carservicing.carservice.exceptionhandler.ElementAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.repository.AppUserRepository;
import com.hcl.carservicing.carservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final AppUserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public UserServiceImpl(AppUserRepository userRepository,
                           JwtUtil jwtUtil,
                           CustomUserDetailsService customUserDetailsService,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public void register(AppUserDTO userDTO) {
        // Check for existing username
        Optional<AppUser> existing = userRepository.findByUsername(userDTO.getUsername());
        if (existing.isPresent()) {
            throw new ElementAlreadyExistException("Username already exists: " + userDTO.getUsername());
        }
        // Check for existing contact number
        Optional<AppUser> existingContactNumber = userRepository.findByContactNumber(userDTO.getContactNumber());
        if (existingContactNumber.isPresent()) {
            throw new ElementAlreadyExistException("Contact Number already exists: " + userDTO.getContactNumber());
        }

        AppUser user = new AppUser();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        user.setContactNumber(userDTO.getContactNumber());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserLoginDTO login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if(!authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        String token = jwtUtil.generateToken(userDetails.getUsername());
        Date expirationTime = jwtUtil.extractExpiration(token);

        return new UserLoginDTO(token, expirationTime);
    }

}
