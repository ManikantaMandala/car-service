package com.hcl.carservicing.carservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carservicing.carservice.model.ServicingRequest;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
import com.hcl.carservicing.carservice.service.ServicingRequestService;

@RestController
@RequestMapping("/api/admin")
public class AdminRequestController {
	
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

        servicingRequestService.updateRequestStatus(id, status, deliveryBoyId);
        return ResponseEntity.ok("Servicing request status updated successfully");
    }

    // Get all servicing requests (admin view)
    @GetMapping("/getAllServiceRequests")
    public ResponseEntity<List<ServicingRequest>> getAll() {
        List<ServicingRequest> list = servicingRequestService.getAllRequests();
        return ResponseEntity.ok(list);
    }

    // Update the availability status of a service center
    @PutMapping("/updateServiceCenterStatus")
    public ResponseEntity<String> updateStatusOfServiceCenter(
            @RequestParam Long id,
            @RequestParam Boolean status) {

        serviceCenterService.updateStatusOfServiceCenter(id, status); // Now using the ServiceCenterService
        return ResponseEntity.ok("Service center status updated successfully");
    }
}

