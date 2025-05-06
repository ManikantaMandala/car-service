package com.hcl.carservicing.carservice.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeliveryBoyDtoTest {

    private DeliveryBoyDTO deliveryBoyDTO;

    @BeforeEach
    void setUp() {
        deliveryBoyDTO = new DeliveryBoyDTO();
    }

    @Test
    void testGetAndSetName() {
        String name = "John Doe";
        deliveryBoyDTO.setName(name);
        assertEquals(name, deliveryBoyDTO.getName());
    }

    @Test
    void testGetAndSetContactNumber() {
        String contactNumber = "1234567890";
        deliveryBoyDTO.setContactNumber(contactNumber);
        assertEquals(contactNumber, deliveryBoyDTO.getContactNumber());
    }

    @Test
    void testGetAndSetServiceCenterId() {
        Long serviceCenterId = 1L;
        deliveryBoyDTO.setServiceCenterId(serviceCenterId);
        assertEquals(serviceCenterId, deliveryBoyDTO.getServiceCenterId());
    }

    @Test
    void testGetAndSetServiceRequestsId() {
        List<Long> serviceRequestsId = Arrays.asList(1L, 2L, 3L);
        deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);
        assertEquals(serviceRequestsId, deliveryBoyDTO.getServiceRequestsId());
    }

    @Test
    void testConstructor() {
        String name = "John Doe";
        String contactNumber = "1234567890";
        Long serviceCenterId = 1L;
        List<Long> serviceRequestsId = Arrays.asList(1L, 2L, 3L);

        DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO(name, contactNumber, serviceCenterId, serviceRequestsId);

        assertEquals(name, deliveryBoyDTO.getName());
        assertEquals(contactNumber, deliveryBoyDTO.getContactNumber());
        assertEquals(serviceCenterId, deliveryBoyDTO.getServiceCenterId());
        assertEquals(serviceRequestsId, deliveryBoyDTO.getServiceRequestsId());
    }

    @Test
    void testEmptyConstructor() {
        DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();
        assertNull(deliveryBoyDTO.getName());
        assertNull(deliveryBoyDTO.getContactNumber());
        assertNull(deliveryBoyDTO.getServiceCenterId());
        assertNull(deliveryBoyDTO.getServiceRequestsId());
    }

    @Test
    void testSetName() {
        deliveryBoyDTO.setName("Jane Doe");
        assertEquals("Jane Doe", deliveryBoyDTO.getName());
    }

    @Test
    void testSetContactNumber() {
        deliveryBoyDTO.setContactNumber("0987654321");
        assertEquals("0987654321", deliveryBoyDTO.getContactNumber());
    }

    @Test
    void testSetServiceCenterId() {
        deliveryBoyDTO.setServiceCenterId(2L);
        assertEquals(2L, deliveryBoyDTO.getServiceCenterId());
    }

    @Test
    void testSetServiceRequestsId() {
        List<Long> serviceRequestsId = Arrays.asList(4L, 5L, 6L);
        deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);
        assertEquals(serviceRequestsId, deliveryBoyDTO.getServiceRequestsId());
    }
}

