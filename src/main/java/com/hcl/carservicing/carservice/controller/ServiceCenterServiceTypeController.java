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

import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterServiceTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-center-services")
public class ServiceCenterServiceTypeController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCenterServiceTypeController.class);

    private final ServiceCenterServiceTypeService service;

    public ServiceCenterServiceTypeController(ServiceCenterServiceTypeService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@Valid @RequestBody ServiceCenterServiceTypeDTO scstDTO) {
        logger.info("Adding service type to service center with details: {}", scstDTO);
        service.addServiceTypeToCenter(scstDTO);

        logger.info("Service type assigned to service center successfully with details: {}", scstDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Service type assigned to service center successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @Valid @RequestBody ServiceCenterServiceTypeDTO scstDTO) {
        logger.info("Updating service-center service relation with ID: {}, New details: {}", id, scstDTO);
        service.updateServiceCenterServiceType(id, scstDTO);

        logger.info("Service center service mapping updated successfully for ID: {}, New details: {}", id, scstDTO);
        return ResponseEntity.ok("Service center service mapping updated successfully");
    }

    @GetMapping("/byCenter/{centerId}")
    public ResponseEntity<List<ServiceCenterServiceTypeDTO>> byCenter(@PathVariable Long centerId) {
        logger.info("Fetching all services offered by service center with ID: {}", centerId);
        List<ServiceCenterServiceTypeDTO> services = service.getByServiceCenter(centerId);

        logger.info("Fetched {} services for service center ID: {}", services.size(), centerId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/byServiceType/{serviceTypeId}")
    public ResponseEntity<List<ServiceCenterServiceTypeDTO>> byService(@PathVariable Long serviceTypeId) {
        logger.info("Fetching all centers offering service type with ID: {}", serviceTypeId);
        List<ServiceCenterServiceTypeDTO> services = service.getByServiceType(serviceTypeId);

        logger.info("Fetched {} centers offering service type ID: {}", services.size(), serviceTypeId);
        return ResponseEntity.ok(services);
    }
}
