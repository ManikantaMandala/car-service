package com.hcl.carservicing.carservice.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.repository.AppUserRepository;
import com.hcl.carservicing.carservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final AppUserRepository userRepository;

    public UserServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(AppUserDTO userDTO) {
        // Check for existing username
        Optional<AppUser> existing = userRepository.findByUsername(userDTO.getUsername());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + userDTO.getUsername());
        }
        // Check for existing contact number
        Optional<AppUser> existingContactNumber = userRepository.findByContactNumber(userDTO.getContactNumber());
        if (existingContactNumber.isPresent()) {
            throw new IllegalArgumentException("Contact Number already exists: " + userDTO.getContactNumber());
        }

        AppUser user = new AppUser();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        user.setContactNumber(userDTO.getContactNumber());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // TODO: Encode password before saving
        user.setRole(userDTO.getRole());

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void login(String userId, String password) {
        AppUser user = userRepository.findByUsername(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        // TODO: Compare encoded passwords
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}
