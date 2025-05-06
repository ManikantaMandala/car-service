package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import com.hcl.carservicing.carservice.dto.UserLoginRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AppUserDTO userDTO) {
        logger.info("Registering new user with details: {}", userDTO);
        userService.register(userDTO);

        logger.info("User registered successfully with details: {}", userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        logger.info("Logging in user with details: {}", userLoginRequestDTO);
        UserLoginDTO result = userService.login(userLoginRequestDTO.username(),
                userLoginRequestDTO.password());

        logger.info("User logged in successfully with username: {}", userLoginRequestDTO.username());
        return ResponseEntity.ok(result);
    }

}
