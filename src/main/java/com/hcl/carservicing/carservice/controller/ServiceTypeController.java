package com.hcl.carservicing.carservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);

    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> create(@Valid @RequestBody ServiceTypeDTO serviceTypeDTO) {
        logger.info("Creating new service type with details: {}", serviceTypeDTO);
        serviceTypeService.createServiceType(serviceTypeDTO);

        logger.info("Service type created successfully with details: {}", serviceTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Service type created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @Valid @RequestBody ServiceTypeDTO serviceTypeDTO) {
        logger.info("Updating service type with ID: {}, New details: {}", id, serviceTypeDTO);
        serviceTypeService.updateServiceType(id, serviceTypeDTO);

        logger.info("Service type updated successfully with ID: {}, New details: {}", id, serviceTypeDTO);
        return ResponseEntity.ok("Service type updated successfully");
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ServiceTypeDTO> getById(@PathVariable Long id) {
        logger.info("Fetching service type with ID: {}", id);
        ServiceTypeDTO result = serviceTypeService.getServiceTypeById(id);

        logger.info("Fetched service type with ID: {}, Details: {}", id, result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceTypeDTO>> getAll() {
        logger.info("Fetching all service types");
        List<ServiceTypeDTO> list = serviceTypeService.getAllServiceTypes();

        logger.info("Fetched {} service types", list.size());
        return ResponseEntity.ok(list);
    }
}
