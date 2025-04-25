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

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.service.DeliveryBoyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/delivery-boys")
public class DeliveryBoyController {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryBoyController.class);
    private final DeliveryBoyService deliveryBoyService;

    public DeliveryBoyController(DeliveryBoyService deliveryBoyService) {
        this.deliveryBoyService = deliveryBoyService;
    }

    // Add a new delivery boy
    @PostMapping("/add")
    public ResponseEntity<String> create(@Valid @RequestBody DeliveryBoyDTO deliveryBoyDTO) {
        logger.info("Creating new delivery boy with details: {}", deliveryBoyDTO);
        deliveryBoyService.createDeliveryBoy(deliveryBoyDTO);
        logger.info("Delivery boy created successfully with details: {}", deliveryBoyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Delivery boy created successfully");

    }

    // Update an existing delivery boy by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @Valid @RequestBody DeliveryBoyDTO deliveryBoyDTO) {
        logger.info("Updating delivery boy with ID: {}, New details: {}", id, deliveryBoyDTO);
        deliveryBoyService.updateDeliveryBoy(id, deliveryBoyDTO);
        logger.info("Delivery boy updated successfully with ID: {}, New details: {}", id, deliveryBoyDTO);
        return ResponseEntity.ok("Delivery boy updated successfully");

    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<List<DeliveryBoyDTO>> byCenter(@PathVariable Long centerId) {
        logger.info("Fetching delivery boys for service center ID: {}", centerId);
        List<DeliveryBoyDTO> list = deliveryBoyService.getDeliveryBoysByCenter(centerId);
        logger.info("Fetched {} delivery boys for service center ID: {}", list.size(), centerId);
        return ResponseEntity.ok(list);

    }

    @GetMapping("/available")
    public ResponseEntity<List<DeliveryBoyDTO>> available() {
        logger.info("Fetching all available delivery boys");
        List<DeliveryBoyDTO> list = deliveryBoyService.getAvailableDeliveryBoys();
        logger.info("Fetched {} available delivery boys", list.size());
        return ResponseEntity.ok(list);

    }
}
