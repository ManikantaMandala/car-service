package com.hcl.carservicing.carservice.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ServiceTypeDtoTest {

    private ServiceTypeDTO serviceTypeDTO;

    @BeforeEach
    void setUp() {
        serviceTypeDTO = new ServiceTypeDTO();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        serviceTypeDTO.setId(id);
        assertEquals(id, serviceTypeDTO.getId());
    }

    @Test
    void testGetAndSetServiceName() {
        String serviceName = "Oil Change";
        serviceTypeDTO.setServiceName(serviceName);
        assertEquals(serviceName, serviceTypeDTO.getServiceName());
    }

    @Test
    void testGetAndSetDescription() {
        String description = "Complete oil change service including filter replacement.";
        serviceTypeDTO.setDescription(description);
        assertEquals(description, serviceTypeDTO.getDescription());
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        String serviceName = "Oil Change";
        String description = "Complete oil change service including filter replacement.";

        serviceTypeDTO = new ServiceTypeDTO();
        serviceTypeDTO.setId(id);
        serviceTypeDTO.setServiceName(serviceName);
        serviceTypeDTO.setDescription(description);

        assertEquals(id, serviceTypeDTO.getId());
        assertEquals(serviceName, serviceTypeDTO.getServiceName());
        assertEquals(description, serviceTypeDTO.getDescription());
    }

    @Test
    void testEmptyConstructor() {
        serviceTypeDTO = new ServiceTypeDTO();
        assertNull(serviceTypeDTO.getId());
        assertNull(serviceTypeDTO.getServiceName());
        assertNull(serviceTypeDTO.getDescription());
    }

    @Test
    void testSetId() {
        serviceTypeDTO.setId(2L);
        assertEquals(2L, serviceTypeDTO.getId());
    }

    @Test
    void testSetServiceName() {
        serviceTypeDTO.setServiceName("Tire Rotation");
        assertEquals("Tire Rotation", serviceTypeDTO.getServiceName());
    }

    @Test
    void testSetDescription() {
        serviceTypeDTO.setDescription("Rotating tires to ensure even wear.");
        assertEquals("Rotating tires to ensure even wear.", serviceTypeDTO.getDescription());
    }

    @Test
    void testValidServiceTypeDTO() {
        serviceTypeDTO.setId(1L);
        serviceTypeDTO.setServiceName("Brake Inspection");
        serviceTypeDTO.setDescription("Inspection of brake pads, rotors, and fluid levels.");

        assertEquals(1L, serviceTypeDTO.getId());
        assertEquals("Brake Inspection", serviceTypeDTO.getServiceName());
        assertEquals("Inspection of brake pads, rotors, and fluid levels.", serviceTypeDTO.getDescription());
    }

    @Test
    void testInvalidServiceTypeDTO() {
        serviceTypeDTO.setId(null);
        serviceTypeDTO.setServiceName(null);
        serviceTypeDTO.setDescription(null);

        assertNull(serviceTypeDTO.getId());
        assertNull(serviceTypeDTO.getServiceName());
        assertNull(serviceTypeDTO.getDescription());
    }
}
