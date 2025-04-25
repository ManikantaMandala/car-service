package com.hcl.carservicing.carservice.service.impl;

import java.util.Date;
import java.util.Optional;

import com.hcl.carservicing.carservice.config.JwtUtil;
import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcl.carservicing.carservice.exceptionhandler.ElementAlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.repository.AppUserRepository;
import com.hcl.carservicing.carservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AppUserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(AppUserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public void register(AppUserDTO userDTO) {
    	logger.info("Registering user with username: {}", userDTO.getUsername());
        // Check for existing username
        Optional<AppUser> existing = userRepository.findByUsername(userDTO.getUsername());
        if (existing.isPresent()) {
        	logger.error("Username already exists: {}", userDTO.getUsername());
            throw new ElementAlreadyExistException("Username already exists: " + userDTO.getUsername());
        }
        // Check for existing contact number
        Optional<AppUser> existingContactNumber = userRepository.findByContactNumber(userDTO.getContactNumber());
        if (existingContactNumber.isPresent()) {
        	logger.error("Contact Number already exists: {}", userDTO.getContactNumber());
            throw new ElementAlreadyExistException("Contact Number already exists: " + userDTO.getContactNumber());
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
        logger.info("User registered successfully with username: {}", userDTO.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public UserLoginDTO login(String userId, String password) {
    	logger.info("User login attempt with username: {}", userId);
    	AppUser user = userRepository.findByUsername(userId)
    		.orElseThrow(() -> {
    			logger.error("Invalid credentials for username: {}", userId);
    			return new IllegalArgumentException("Invalid credentials");
    		});

        // TODO: Compare encoded passwords
        // TODO: !Objects.equals(a, b)
    	if (!user.getPassword().equals(password)) {
    		logger.error("Invalid credentials for username: {}", userId);
    		throw new IllegalArgumentException("Invalid credentials");
    	}

    	logger.info("User logged in successfully with username: {}", userId);

        // TODO: why two times checking?
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String jwt = jwtUtil.generateToken(userId);
        Date expirationDate = jwtUtil.extractExpiration(jwt);

        return new UserLoginDTO(jwtUtil.generateToken(userId), expirationDate);
    }
}
