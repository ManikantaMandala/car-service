package com.hcl.carservicing.carservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

class DeliveryBoyMapperTest {

    @Test
    void toEntity_shouldMapDTOToEntityCorrectly() {
        DeliveryBoyDTO dto = new DeliveryBoyDTO();
        dto.setName("John Doe");
        dto.setContactNumber("9876543210");

        DeliveryBoy entity = DeliveryBoyMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("John Doe", entity.getName());
        assertEquals("9876543210", entity.getContactNumber());
    }

    @Test
    void toDto_shouldMapEntityToDtoCorrectly_withServiceRequests() {
        ServiceCenter center = new ServiceCenter();
        center.setId(101L);

        ServiceRequest req1 = new ServiceRequest();
        req1.setId(1L);
        ServiceRequest req2 = new ServiceRequest();
        req2.setId(2L);

        DeliveryBoy deliveryBoy = new DeliveryBoy();
        deliveryBoy.setName("Jane Doe");
        deliveryBoy.setContactNumber("1234567890");
        deliveryBoy.setServiceCenter(center);
        deliveryBoy.setServicingRequest(List.of(req1, req2));

        DeliveryBoyDTO dto = DeliveryBoyMapper.toDto(deliveryBoy);

        assertNotNull(dto);
        assertEquals("Jane Doe", dto.getName());
        assertEquals("1234567890", dto.getContactNumber());
        assertEquals(101L, dto.getServiceCenterId());
        assertEquals(List.of(1L, 2L), dto.getServiceRequestsId());
    }

    @Test
    void toDto_shouldHandleNullServiceRequestList() {
        ServiceCenter center = new ServiceCenter();
        center.setId(102L);

        DeliveryBoy deliveryBoy = new DeliveryBoy();
        deliveryBoy.setName("No Requests");
        deliveryBoy.setContactNumber("1112223334");
        deliveryBoy.setServiceCenter(center);
        deliveryBoy.setServicingRequest(null); // null list

        DeliveryBoyDTO dto = DeliveryBoyMapper.toDto(deliveryBoy);

        assertNotNull(dto);
        assertEquals("No Requests", dto.getName());
        assertEquals("1112223334", dto.getContactNumber());
        assertEquals(102L, dto.getServiceCenterId());
        assertNull(dto.getServiceRequestsId());
    }
}
