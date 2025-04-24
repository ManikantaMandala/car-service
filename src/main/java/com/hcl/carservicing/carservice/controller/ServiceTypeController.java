package com.hcl.carservicing.carservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.service.ServiceTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-types")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> create(@Valid @RequestBody ServiceTypeDTO serviceTypeDTO) {
        serviceTypeService.createServiceType(serviceTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Service type created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @Valid @RequestBody ServiceTypeDTO serviceTypeDTO) {
        serviceTypeService.updateServiceType(id, serviceTypeDTO);
        return ResponseEntity.ok("Service type updated successfully");
    }

    // Get service type by ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<ServiceTypeDTO> getById(@PathVariable Long id) {
        ServiceTypeDTO result = serviceTypeService.getServiceTypeById(id);
        return ResponseEntity.ok(result);
    }

    // Get all service types
    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceTypeDTO>> getAll() {
        List<ServiceTypeDTO> list = serviceTypeService.getAllServiceTypes();
        return ResponseEntity.ok(list);
    }
}
