package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // TODO: change the request param of both userId and password
    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestParam String userId, @RequestParam String password) {
        UserLoginDTO result = userService.login(userId, password);

        return ResponseEntity.ok(result);
    }

}
