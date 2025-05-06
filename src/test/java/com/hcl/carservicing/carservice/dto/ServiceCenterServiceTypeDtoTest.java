package com.hcl.carservicing.carservice.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ServiceCenterServiceTypeDtoTest {

    private ServiceCenterServiceTypeDTO serviceCenterServiceTypeDTO;

    @BeforeEach
    void setUp() {
        serviceCenterServiceTypeDTO = new ServiceCenterServiceTypeDTO();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        serviceCenterServiceTypeDTO.setId(id);
        assertEquals(id, serviceCenterServiceTypeDTO.getId());
    }

    @Test
    void testGetAndSetServiceCenterId() {
        Long serviceCenterId = 1L;
        serviceCenterServiceTypeDTO.setServiceCenterId(serviceCenterId);
        assertEquals(serviceCenterId, serviceCenterServiceTypeDTO.getServiceCenterId());
    }

    @Test
    void testGetAndSetServiceTypeId() {
        Long serviceTypeId = 1L;
        serviceCenterServiceTypeDTO.setServiceTypeId(serviceTypeId);
        assertEquals(serviceTypeId, serviceCenterServiceTypeDTO.getServiceTypeId());
    }

    @Test
    void testGetAndSetCost() {
        Double cost = 100.0;
        serviceCenterServiceTypeDTO.setCost(cost);
        assertEquals(cost, serviceCenterServiceTypeDTO.getCost());
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        Long serviceCenterId = 1L;
        Long serviceTypeId = 1L;
        Double cost = 100.0;

        ServiceCenterServiceTypeDTO serviceCenterServiceTypeDTO = new ServiceCenterServiceTypeDTO();
        serviceCenterServiceTypeDTO.setId(id);
        serviceCenterServiceTypeDTO.setServiceCenterId(serviceCenterId);
        serviceCenterServiceTypeDTO.setServiceTypeId(serviceTypeId);
        serviceCenterServiceTypeDTO.setCost(cost);

        assertEquals(id, serviceCenterServiceTypeDTO.getId());
        assertEquals(serviceCenterId, serviceCenterServiceTypeDTO.getServiceCenterId());
        assertEquals(serviceTypeId, serviceCenterServiceTypeDTO.getServiceTypeId());
        assertEquals(cost, serviceCenterServiceTypeDTO.getCost());
    }

    @Test
    void testEmptyConstructor() {
        ServiceCenterServiceTypeDTO serviceCenterServiceTypeDTO = new ServiceCenterServiceTypeDTO();
        assertNull(serviceCenterServiceTypeDTO.getId());
        assertNull(serviceCenterServiceTypeDTO.getServiceCenterId());
        assertNull(serviceCenterServiceTypeDTO.getServiceTypeId());
        assertNull(serviceCenterServiceTypeDTO.getCost());
    }

    @Test
    void testSetId() {
        serviceCenterServiceTypeDTO.setId(2L);
        assertEquals(2L, serviceCenterServiceTypeDTO.getId());
    }

    @Test
    void testSetServiceCenterId() {
        serviceCenterServiceTypeDTO.setServiceCenterId(2L);
        assertEquals(2L, serviceCenterServiceTypeDTO.getServiceCenterId());
    }

    @Test
    void testSetServiceTypeId() {
        serviceCenterServiceTypeDTO.setServiceTypeId(2L);
        assertEquals(2L, serviceCenterServiceTypeDTO.getServiceTypeId());
    }

    @Test
    void testSetCost() {
        serviceCenterServiceTypeDTO.setCost(200.0);
        assertEquals(200.0, serviceCenterServiceTypeDTO.getCost());
    }
}
