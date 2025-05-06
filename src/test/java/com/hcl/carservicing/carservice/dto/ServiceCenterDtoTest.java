package com.hcl.carservicing.carservice.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceCenterDtoTest {

    private ServiceCenterDTO serviceCenterDTO;

    @BeforeEach
    void setUp() {
        serviceCenterDTO = new ServiceCenterDTO();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        serviceCenterDTO.setId(id);
        assertEquals(id, serviceCenterDTO.getId());
    }

    @Test
    void testGetAndSetName() {
        String name = "AutoFix";
        serviceCenterDTO.setName(name);
        assertEquals(name, serviceCenterDTO.getName());
    }

    @Test
    void testGetAndSetAddress() {
        String address = "123 Main Street, City, Country";
        serviceCenterDTO.setAddress(address);
        assertEquals(address, serviceCenterDTO.getAddress());
    }

    @Test
    void testGetAndSetRating() {
        Double rating = 4.5;
        serviceCenterDTO.setRating(rating);
        assertEquals(rating, serviceCenterDTO.getRating());
    }

    @Test
    void testGetAndSetAvailable() {
        Boolean available = false;
        serviceCenterDTO.setAvailable(available);
        assertEquals(available, serviceCenterDTO.getAvailable());
    }

    @Test
    void testGetAndSetServicingRequests() {
        List<ServiceRequestDTO> requests = Arrays.asList(new ServiceRequestDTO(), new ServiceRequestDTO());
        serviceCenterDTO.setServicingRequests(requests);
        assertEquals(requests, serviceCenterDTO.getServicingRequests());
    }

    @Test
    void testGetAndSetServiceCenterServiceTypes() {
        List<ServiceCenterServiceTypeDTO> types = Arrays.asList(new ServiceCenterServiceTypeDTO(), new ServiceCenterServiceTypeDTO());
        serviceCenterDTO.setServiceCenterServiceTypes(types);
        assertEquals(types, serviceCenterDTO.getServiceCenterServiceTypes());
    }

    @Test
    void testGetAndSetDeliveryBoys() {
        List<DeliveryBoyDTO> boys = Arrays.asList(new DeliveryBoyDTO(), new DeliveryBoyDTO());
        serviceCenterDTO.setDeliveryBoys(boys);
        assertEquals(boys, serviceCenterDTO.getDeliveryBoys());
    }

    @Test
    void testValidServiceCenterDTO() {
        serviceCenterDTO.setId(1L);
        serviceCenterDTO.setName("AutoFix");
        serviceCenterDTO.setAddress("123 Main Street, City, Country");
        serviceCenterDTO.setRating(4.5);
        serviceCenterDTO.setAvailable(true);

        assertEquals(1L, serviceCenterDTO.getId());
        assertEquals("AutoFix", serviceCenterDTO.getName());
        assertEquals("123 Main Street, City, Country", serviceCenterDTO.getAddress());
        assertEquals(4.5, serviceCenterDTO.getRating());
        assertTrue(serviceCenterDTO.getAvailable());
    }

    @Test
    void testEmptyConstructor() {
        serviceCenterDTO = new ServiceCenterDTO();

        assertNull(serviceCenterDTO.getId());
        assertNull(serviceCenterDTO.getName());
        assertNull(serviceCenterDTO.getAddress());
        assertNull(serviceCenterDTO.getRating());
        assertNull(serviceCenterDTO.getServicingRequests());
        assertNull(serviceCenterDTO.getServiceCenterServiceTypes());
        assertNull(serviceCenterDTO.getDeliveryBoys());
        assertTrue(serviceCenterDTO.getAvailable());
    }
}

