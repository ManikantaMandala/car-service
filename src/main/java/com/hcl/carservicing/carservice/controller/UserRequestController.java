package com.hcl.carservicing.carservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.dto.ServicingRequestDTO;
import com.hcl.carservicing.carservice.model.ServicingRequest;
import com.hcl.carservicing.carservice.service.ServicingRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicing-request")
public class UserRequestController {
    private final ServicingRequestService service;

    public UserRequestController(ServicingRequestService service) {
        this.service = service;
    }

    // Create a new servicing request
    @PostMapping("/create")
    public ResponseEntity<String> createRequest(@Valid @RequestBody ServicingRequestDTO requestDTO) {
        service.createRequest(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Service request created successfully");
    }

    // Get all servicing requests for a specific user
    @GetMapping("/user/{username}")
    public ResponseEntity<List<ServicingRequest>> getRequestsByUser(@PathVariable String username) {
        List<ServicingRequest> list = service.getRequestsByUser(username);
        return ResponseEntity.ok(list);
    }
}
