package com.hcl.carservicing.carservice.service.impl;

import java.util.Date;

import com.hcl.carservicing.carservice.config.CustomUserDetailsService;
import com.hcl.carservicing.carservice.config.JwtUtil;
import com.hcl.carservicing.carservice.dao.service.AppUserDaoService;
import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import com.hcl.carservicing.carservice.mapper.AppUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final AppUserDaoService appUserDaoService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public UserServiceImpl(AppUserDaoService appUserDaoService,
                           JwtUtil jwtUtil,
                           CustomUserDetailsService customUserDetailsService,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.appUserDaoService = appUserDaoService;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public void register(AppUserDTO userDTO) {
        appUserDaoService.throwIfUsernameExists(userDTO.getUsername());
        appUserDaoService.throwIfContactExists(userDTO.getContactNumber());

        AppUser user = AppUserMapper.toEntity(userDTO);

        logger.info("user dto converted to AppUser: {}", user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        appUserDaoService.save(user);
        logger.info("user save with username: {}", user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public UserLoginDTO login(String username, String password) {
        logger.info("user details received");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if(!authentication.isAuthenticated()) {
            logger.warn("Invalid credentials");
            throw new IllegalArgumentException("Invalid credentials");
        }
        logger.info("User authenticated successfully");

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        logger.info("User details: {}", userDetails);

        String token = jwtUtil.generateToken(userDetails.getUsername());
        Date expirationTime = jwtUtil.extractExpiration(token);
        logger.info("User jwt token: {}, with expiration time: {}", token, expirationTime);

        return new UserLoginDTO(token, expirationTime);
    }

}
