package com.hcl.carservicing.carservice.dto;

import com.hcl.carservicing.carservice.enums.RequestStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ServicingRequestDtoTest {

    private ServiceRequestDTO servicingRequestDTO;

    @BeforeEach
    void setUp() {
        servicingRequestDTO = new ServiceRequestDTO();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        servicingRequestDTO.setId(id);
        assertEquals(id, servicingRequestDTO.getId());
    }

    @Test
    void testGetAndSetStartDate() {
        LocalDate startDate = LocalDate.now();
        servicingRequestDTO.setStartDate(startDate);
        assertEquals(startDate, servicingRequestDTO.getStartDate());
    }

    @Test
    void testGetAndSetEndDate() {
        LocalDate endDate = LocalDate.now().plusDays(1);
        servicingRequestDTO.setEndDate(endDate);
        assertEquals(endDate, servicingRequestDTO.getEndDate());
    }

    @Test
    void testGetAndSetStatus() {
        RequestStatus status = RequestStatus.PENDING;
        servicingRequestDTO.setStatus(status);
        assertEquals(status, servicingRequestDTO.getStatus());
    }

    @Test
    void testGetAndSetUsername() {
        String username = "johndoe";
        servicingRequestDTO.setUsername(username);
        assertEquals(username, servicingRequestDTO.getUsername());
    }

    @Test
    void testGetAndSetDeliveryBoyId() {
        Long deliveryBoyId = 1L;
        servicingRequestDTO.setDeliveryBoyId(deliveryBoyId);
        assertEquals(deliveryBoyId, servicingRequestDTO.getDeliveryBoyId());
    }

    @Test
    void testGetAndSetServiceId() {
        Long serviceId = 1L;
        servicingRequestDTO.setServiceId(serviceId);
        assertEquals(serviceId, servicingRequestDTO.getServiceId());
    }

    @Test
    void testGetAndSetServiceCenterId() {
        Long serviceCenterId = 1L;
        servicingRequestDTO.setServiceCenterId(serviceCenterId);
        assertEquals(serviceCenterId, servicingRequestDTO.getServiceCenterId());
    }

    @Test
    void testValidServicingRequestDTO() {
        servicingRequestDTO.setId(1L);
        servicingRequestDTO.setStartDate(LocalDate.now());
        servicingRequestDTO.setEndDate(LocalDate.now().plusDays(1));
        servicingRequestDTO.setStatus(RequestStatus.PENDING);
        servicingRequestDTO.setUsername("johndoe");
        servicingRequestDTO.setDeliveryBoyId(1L);
        servicingRequestDTO.setServiceId(1L);
        servicingRequestDTO.setServiceCenterId(1L);

        assertEquals(1L, servicingRequestDTO.getId());
        assertEquals(LocalDate.now(), servicingRequestDTO.getStartDate());
        assertEquals(LocalDate.now().plusDays(1), servicingRequestDTO.getEndDate());
        assertEquals(RequestStatus.PENDING, servicingRequestDTO.getStatus());
        assertEquals("johndoe", servicingRequestDTO.getUsername());
        assertEquals(1L, servicingRequestDTO.getDeliveryBoyId());
        assertEquals(1L, servicingRequestDTO.getServiceId());
        assertEquals(1L, servicingRequestDTO.getServiceCenterId());
    }

    @Test
    void testEmptyConstructor() {
        ServiceRequestDTO servicingRequestDTO = new ServiceRequestDTO();
        assertNull(servicingRequestDTO.getId());
        assertNull(servicingRequestDTO.getStartDate());
        assertNull(servicingRequestDTO.getEndDate());
        assertNull(servicingRequestDTO.getStatus());
        assertNull(servicingRequestDTO.getUsername());
        assertNull(servicingRequestDTO.getDeliveryBoyId());
        assertNull(servicingRequestDTO.getServiceId());
        assertNull(servicingRequestDTO.getServiceCenterId());
    }
}

