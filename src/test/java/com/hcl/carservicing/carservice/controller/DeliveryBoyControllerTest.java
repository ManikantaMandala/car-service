package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.exceptionhandler.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import com.hcl.carservicing.carservice.service.DeliveryBoyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryBoyControllerTest {

    private String name;
    private String contactNumber;
    private Long serviceCenterId;
    private List<Long> serviceRequestsId;
    private DeliveryBoyDTO deliveryBoyDTO;

    @Mock
    private DeliveryBoyService deliveryBoyService;

    @InjectMocks
    private DeliveryBoyController deliveryBoyController;

    @BeforeEach
    void beforeEach() {
        name = "test_name";
        contactNumber = "1809439134";
        serviceCenterId = 1L;
        serviceRequestsId = new ArrayList<>();

        deliveryBoyDTO = new DeliveryBoyDTO();

        deliveryBoyDTO.setName(name);
        deliveryBoyDTO.setContactNumber(contactNumber);
        deliveryBoyDTO.setServiceCenterId(serviceCenterId);
        deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);
    }

    @Test
    void createWithCreatedStatus() {
        doNothing().when(deliveryBoyService).createDeliveryBoy(deliveryBoyDTO);

        ResponseEntity<String> result = deliveryBoyController.create(deliveryBoyDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Delivery boy created successfully", result.getBody());
        verify(deliveryBoyService, times(1))
                .createDeliveryBoy(deliveryBoyDTO);
    }

    @Test
    void createWhichThrowsElementAlreadyExistExceptionForContactNumber() {
        doThrow(new ElementAlreadyExistException("Contact Number already exists: " + deliveryBoyDTO.getContactNumber()))
                .when(deliveryBoyService).createDeliveryBoy(deliveryBoyDTO);

        ElementAlreadyExistException thrownException = assertThrows(ElementAlreadyExistException.class,
                () -> deliveryBoyController.create(deliveryBoyDTO));

        assertEquals("Contact Number already exists: " + deliveryBoyDTO.getContactNumber(),
                thrownException.getMessage());
        verify(deliveryBoyService, times(1))
                .createDeliveryBoy(deliveryBoyDTO);
    }

    @Test
    void createWhichThrowsIllegalArgumentExceptionForServiceCenterDTOBeingNull() {
        doThrow(
                new IllegalArgumentException("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId()))
                .when(deliveryBoyService).createDeliveryBoy(deliveryBoyDTO);

        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class,
                () -> deliveryBoyController.create(deliveryBoyDTO));

        assertEquals("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId(),
                thrownException.getMessage());

        verify(deliveryBoyService, times(1))
                .createDeliveryBoy(deliveryBoyDTO);
    }

    @Test
    void update() {

    }

    @Test
    void byCenter() {

    }

    @Test
    void available() {

    }
}