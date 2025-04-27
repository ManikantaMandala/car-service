package com.hcl.carservicing.carservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.dto.ServicingRequestDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
import com.hcl.carservicing.carservice.service.ServicingRequestService;

@RestController
@RequestMapping("/api/admin")
public class AdminRequestController {

    private static final Logger logger = LoggerFactory.getLogger(AdminRequestController.class);

    private final ServicingRequestService servicingRequestService;
    private final ServiceCenterService serviceCenterService;

    public AdminRequestController(ServicingRequestService servicingRequestService,
                                  ServiceCenterService serviceCenterService) {
        this.servicingRequestService = servicingRequestService;
        this.serviceCenterService = serviceCenterService;
    }

    // Update status of a servicing request
    @PutMapping("/updateServiceRequestStatus/{id}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) Long deliveryBoyId) {

        logger.info("Updating status of servicing request with ID: {}, Status: {}, DeliveryBoyId: {}", id, status, deliveryBoyId);
        servicingRequestService.updateRequestStatus(id, status, deliveryBoyId);
        logger.info("Servicing request status updated successfully for ID: {}, Status: {}, DeliveryBoyId: {}", id, status, deliveryBoyId);
        return ResponseEntity.ok("Servicing request status updated successfully");
    }

    // Get all servicing requests (admin view)
    @GetMapping("/getAllServiceRequests")
    public ResponseEntity<List<ServicingRequestDTO>> getAll() {
        logger.info("Fetching all servicing requests");
        List<ServicingRequestDTO> list = servicingRequestService.getAllRequests();
        logger.info("Fetched {} servicing requests", list.size());
        return ResponseEntity.ok(list);

    }

    // Update the availability status of a service center
    @PutMapping("/updateServiceCenterStatus")
    public ResponseEntity<String> updateStatusOfServiceCenter(
            @RequestParam Long id,
            @RequestParam Boolean status) {

        logger.info("Updating status of service center with ID: {}, Status: {}", id, status);
        serviceCenterService.updateStatusOfServiceCenter(id, status);
        logger.info("Service center status updated successfully for ID: {}, Status: {}", id, status);
        return ResponseEntity.ok("Service center status updated successfully");

    }
}