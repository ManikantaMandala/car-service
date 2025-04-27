package com.hcl.carservicing.carservice.service.impl;

import java.util.Optional;

import com.hcl.carservicing.carservice.config.SecurityConfig;
import com.hcl.carservicing.carservice.exceptionhandler.ElementAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(AppUserRepository userRepository) {

        this.userRepository = userRepository;
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
    public void login(String userId, String password) {
        AppUser user = userRepository.findByUsername(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        // Corrected: raw password first, encoded password second
        boolean isValidPassword = passwordEncoder.matches(password, user.getPassword());

        if (!isValidPassword) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

}
