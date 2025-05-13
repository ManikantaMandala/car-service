package com.hcl.carservicing.carservice.controller;

import java.util.List;

import com.hcl.carservicing.carservice.enums.RequestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
import com.hcl.carservicing.carservice.service.ServiceRequestService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRequestController {

    private static final Logger logger = LoggerFactory.getLogger(AdminRequestController.class);

    private final ServiceRequestService serviceRequestService;
    private final ServiceCenterService serviceCenterService;

    public AdminRequestController(ServiceRequestService serviceRequestService,
                                  ServiceCenterService serviceCenterService) {
        this.serviceRequestService = serviceRequestService;
        this.serviceCenterService = serviceCenterService;
    }

    // TODO: every route should have different Dto
    @PutMapping("/service-request-status/{id}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status,
            @RequestParam(required = false) Long deliveryBoyId,
            @RequestParam(required = false) String message) {
        logger.info("Updating status of servicing request with ID: {}, Status: {}, DeliveryBoyId: {}", id, status, deliveryBoyId);
        serviceRequestService.updateRequestStatus(id, status, deliveryBoyId, message);

        logger.info("Servicing request status updated successfully for ID: {}, Status: {}, DeliveryBoyId: {}", id, status, deliveryBoyId);
        return ResponseEntity.ok("Servicing request status updated successfully");
    }

    @GetMapping("/service-requests")
    public ResponseEntity<List<ServiceRequestDTO>> getAll() {
        logger.info("Fetching all servicing requests");
        List<ServiceRequestDTO> list = serviceRequestService.getAllRequests();

        logger.info("Fetched {} servicing requests", list.size());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/service-center-status")
    public ResponseEntity<String> updateStatusOfServiceCenter(
            @RequestParam Long id,
            @RequestParam Boolean status) {
        logger.info("Updating status of service center with ID: {}, Status: {}", id, status);
        serviceCenterService.updateStatusOfServiceCenter(id, status);

        logger.info("Service center status updated successfully for ID: {}, Status: {}", id, status);
        return ResponseEntity.ok("Service center status updated successfully");
    }
}