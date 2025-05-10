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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/service-center")
public class ServiceCenterController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCenterController.class);

    private final ServiceCenterService serviceCenterService;

    public ServiceCenterController(ServiceCenterService serviceCenterService) {
        this.serviceCenterService = serviceCenterService;
    }

    @PostMapping
    public ResponseEntity<String> addServiceCenter(@Valid @RequestBody ServiceCenterDTO serviceCenterDTO) {
        logger.info("Adding new service center with details: {}", serviceCenterDTO);
        serviceCenterService.createServiceCenter(serviceCenterDTO);

        logger.info("Service center created successfully with details: {}", serviceCenterDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Service center created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateServiceCenter(@PathVariable Long id,
                                                      @Valid @RequestBody ServiceCenterDTO serviceCenterDTO) {
        logger.info("Updating service center with ID: {}, New details: {}", id, serviceCenterDTO);
        serviceCenterService.updateServiceCenter(id, serviceCenterDTO);

        logger.info("Service center updated successfully with ID: {}, New details: {}", id, serviceCenterDTO);
        return ResponseEntity.ok("Service center updated successfully");
    }

    @GetMapping
    public ResponseEntity<List<ServiceCenterDTO>> getAllServiceCenters() {
        logger.info("Fetching all service centers");
        List<ServiceCenterDTO> centers = serviceCenterService.getAllServiceCenters();

        logger.info("Fetched {} service centers", centers.size());
        return ResponseEntity.ok(centers);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ServiceCenterDTO>> getAvailableServiceCenters(
            @RequestParam(defaultValue = "true") Boolean available) {
        logger.info("Fetching service centers by availability: {}", available);
        List<ServiceCenterDTO> availableCenters = serviceCenterService.getAvailableServiceCenters(available);

        logger.info("Fetched {} available service centers", availableCenters.size());
        return ResponseEntity.ok(availableCenters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCenterDTO> getServiceCenterById(@PathVariable Long id) {
        logger.info("Fetching service center with ID: {}", id);
        ServiceCenterDTO center = serviceCenterService.getServiceCenterById(id);

        logger.info("Fetched service center with ID: {}, Details: {}", id, center);
        return ResponseEntity.ok(center);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateStatusOfServiceCenter(@PathVariable Long id,
                                                              @RequestParam Boolean status) {
        logger.info("Updating status of service center with ID: {}, Status: {}", id, status);
        serviceCenterService.updateStatusOfServiceCenter(id, status);

        logger.info("Service center status updated successfully for ID: {}, Status: {}", id, status);
        return ResponseEntity.ok("Service center status updated successfully");
    }
}