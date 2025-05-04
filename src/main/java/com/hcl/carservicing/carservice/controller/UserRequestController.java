package com.hcl.carservicing.carservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.service.ServiceRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicing-request")
public class UserRequestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRequestController.class);

    private final ServiceRequestService service;

    public UserRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRequest(@Valid @RequestBody ServiceRequestDTO requestDTO) {
        logger.info("Creating new servicing request with details: {}", requestDTO);
        service.createRequest(requestDTO);

        logger.info("Service request created successfully with details: {}", requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Service request created successfully");
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ServiceRequestDTO>> getRequestsByUser(@PathVariable String username) {
        logger.info("Fetching servicing requests for user: {}", username);
        List<ServiceRequestDTO> list = service.getRequestsByUser(username);

        logger.info("Fetched {} servicing requests for user: {}", list.size(), username);
        return ResponseEntity.ok(list);
    }
}
