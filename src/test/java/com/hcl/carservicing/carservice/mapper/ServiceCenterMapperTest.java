package com.hcl.carservicing.carservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import org.junit.jupiter.api.Test;

import java.util.List;

class ServiceCenterMapperTest {

    @Test
    void convertToEntity_shouldMapDTOtoEntityCorrectly() {
        ServiceCenterDTO dto = new ServiceCenterDTO();
        dto.setId(1L);
        dto.setName("ABC Motors");
        dto.setAddress("123 Main Street");
        dto.setRating(4.5);
        dto.setAvailable(true);

        ServiceCenter entity = ServiceCenterMapper.convertToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getAddress(), entity.getAddress());
        assertEquals(dto.getRating(), entity.getRating());
        assertEquals(dto.getAvailable(), entity.getAvailable());
    }

    @Test
    void convertToDTO_shouldMapEntityToDTOCorrectly_withoutServiceTypes() {
        ServiceCenter entity = new ServiceCenter();
        entity.setId(1L);
        entity.setName("XYZ Garage");
        entity.setAddress("456 Second Ave");
        entity.setRating(3.9);
        entity.setAvailable(false);
        entity.setServiceCenterServiceTypes(null);  // no service types

        ServiceCenterDTO dto = ServiceCenterMapper.convertToDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getAddress(), dto.getAddress());
        assertEquals(entity.getRating(), dto.getRating());
        assertEquals(entity.getAvailable(), dto.getAvailable());
        assertNull(dto.getServiceCenterServiceTypes());
    }

    @Test
    void convertToDTO_shouldMapEntityToDTOCorrectly_withServiceTypes() {
        ServiceCenter entity = new ServiceCenter();
        ServiceCenterServiceType serviceCenterServiceType = new ServiceCenterServiceType();
        ServiceType serviceType = new ServiceType();

        serviceType.setId(1L);

        serviceCenterServiceType.setId(100L);
        serviceCenterServiceType.setServiceCenter(entity);
        serviceCenterServiceType.setServiceType(serviceType);

        entity.setId(2L);
        entity.setName("RapidFix");
        entity.setAddress("789 Repair Lane");
        entity.setRating(4.8);
        entity.setAvailable(true);
        entity.setServicingRequests(List.of());
        entity.setDeliveryBoys(List.of());
        entity.setServiceCenterServiceTypes(List.of(serviceCenterServiceType));

        ServiceCenterDTO dto = ServiceCenterMapper.convertToDTO(entity);

        assertNotNull(dto);
        assertEquals(1, dto.getServiceCenterServiceTypes().size());
    }
}
