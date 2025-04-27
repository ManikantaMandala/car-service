package com.hcl.carservicing.carservice.service.impl;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import com.hcl.carservicing.carservice.repository.ServiceCenterServiceTypeRepository;
import com.hcl.carservicing.carservice.repository.ServiceTypeRepository;
import com.hcl.carservicing.carservice.service.ServiceCenterServiceTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCenterServiceTypeServiceImplTest {

    @Mock
    ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository;

    @Mock
    ServiceCenterRepository serviceCenterRepository;

    @Mock
    ServiceTypeRepository serviceTypeRepository;

    @InjectMocks
    ServiceCenterServiceTypeServiceImpl serviceCenterServiceTypeService;


    private ServiceCenterServiceTypeDTO createSampleDTO() {
        ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();
        dto.setId(1L);
        dto.setServiceCenterId(1L);
        dto.setServiceTypeId(1L);
        dto.setCost(100.00);
        return dto;
    }

    private ServiceCenterServiceType createSampleEntity() {
        ServiceCenterServiceType entity = new ServiceCenterServiceType();
        entity.setId(1L);
        entity.setCost(100.00);

        ServiceCenter center = createSampleServiceCenter();
        ServiceType serviceType = createSampleServiceType();

        entity.setServiceCenter(center);
        entity.setServiceType(serviceType);

        return entity;
    }

    private ServiceCenter createSampleServiceCenter() {
        ServiceCenter center = new ServiceCenter();
        center.setId(1L);
        center.setName("Center 1");
        return center;
    }

    private ServiceType createSampleServiceType() {
        ServiceType type = new ServiceType();
        type.setId(1L);
        type.setServiceName("Oil Change");
        return type;
    }

    @Test
    void addServiceTypeToCenterSuccessfully() {
        ServiceCenterServiceTypeDTO dto = createSampleDTO();
        ServiceCenter serviceCenter = createSampleServiceCenter();
        ServiceType serviceType = createSampleServiceType();

        when(serviceCenterRepository.findById(dto.getServiceCenterId())).thenReturn(Optional.of(serviceCenter));
        when(serviceTypeRepository.findById(dto.getServiceTypeId())).thenReturn(Optional.of(serviceType));

        ServiceCenterServiceType savedEntity = new ServiceCenterServiceType();
        savedEntity.setId(1L);

        when(serviceCenterServiceTypeRepository.save(any(ServiceCenterServiceType.class))).thenReturn(savedEntity);

        serviceCenterServiceTypeService.addServiceTypeToCenter(dto);

        verify(serviceCenterServiceTypeRepository).save(any(ServiceCenterServiceType.class));
    }



    @Test
    void updateServiceCenterServiceType() {
        Long id = 1L;
        ServiceCenterServiceTypeDTO dto = createSampleDTO();
        ServiceCenterServiceType existing = new ServiceCenterServiceType();
        existing.setId(id);

        when(serviceCenterServiceTypeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(serviceCenterRepository.findById(dto.getServiceCenterId())).thenReturn(Optional.of(createSampleServiceCenter()));
        when(serviceTypeRepository.findById(dto.getServiceTypeId())).thenReturn(Optional.of(createSampleServiceType()));

        when(serviceCenterServiceTypeRepository.save(any(ServiceCenterServiceType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        serviceCenterServiceTypeService.updateServiceCenterServiceType(id, dto);

        verify(serviceCenterServiceTypeRepository).save(existing);
    }

    @Test
    void updateServiceCenterServiceTypeWhichThrowsElementNotFoundException() {
        Long id = 99L;
        ServiceCenterServiceTypeDTO dto = createSampleDTO();

        when(serviceCenterServiceTypeRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ElementNotFoundException.class, () ->
                serviceCenterServiceTypeService.updateServiceCenterServiceType(id, dto));

        assertTrue(exception.getMessage().contains("ServiceCenterServiceType not found"));
    }

    @Test
    void getByServiceCenter() {

        Long serviceCenterId = 1L;
        ServiceCenterServiceType entity = createSampleEntity();
        when(serviceCenterServiceTypeRepository.findByServiceCenterId(serviceCenterId)).thenReturn(List.of(entity));

        List<ServiceCenterServiceTypeDTO> result = serviceCenterServiceTypeService.getByServiceCenter(serviceCenterId);

        assertEquals(1, result.size());
        assertEquals(serviceCenterId, result.get(0).getServiceCenterId());
    }

    @Test
    void getByServiceType() {
        Long serviceTypeId = 1L;
        ServiceCenterServiceType entity = createSampleEntity();
        when(serviceCenterServiceTypeRepository.findByServiceTypeId(serviceTypeId)).thenReturn(List.of(entity));

        List<ServiceCenterServiceTypeDTO> result = serviceCenterServiceTypeService.getByServiceType(serviceTypeId);

        assertEquals(1, result.size());
        assertEquals(serviceTypeId, result.get(0).getServiceTypeId());
    }

    @Test
    void testConvertToEntity_ServiceCenterServiceTypeDTO_success() {
        ServiceCenterServiceTypeDTO dto = createSampleDTO();
        when(serviceCenterRepository.findById(dto.getServiceCenterId())).thenReturn(Optional.of(createSampleServiceCenter()));
        when(serviceTypeRepository.findById(dto.getServiceTypeId())).thenReturn(Optional.of(createSampleServiceType()));

        ServiceCenterServiceType entity = serviceCenterServiceTypeService.convertToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getCost(), entity.getCost());
        assertEquals(dto.getServiceCenterId(), entity.getServiceCenter().getId());
        assertEquals(dto.getServiceTypeId(), entity.getServiceType().getId());
    }

    @Test
    void testConvertToEntity_ServiceCenterDTO_success() {
        ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
        serviceCenterDTO.setId(1L);
        serviceCenterDTO.setName("Center 1");
        serviceCenterDTO.setAddress("Address 1");
        serviceCenterDTO.setRating(4.5);
        serviceCenterDTO.setAvailable(true);

        ServiceCenter center = serviceCenterServiceTypeService.convertToEntity(serviceCenterDTO);

        assertNotNull(center);
        assertEquals(serviceCenterDTO.getName(), center.getName());
        assertEquals(serviceCenterDTO.getAddress(), center.getAddress());
        assertEquals(serviceCenterDTO.getRating(), center.getRating());
        assertEquals(serviceCenterDTO.getAvailable(), center.getAvailable());
    }

    @Test
    void testConvertToEntity_ServiceTypeDTO_success() {
        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();
        serviceTypeDTO.setId(1L);
        serviceTypeDTO.setServiceName("Oil Change");

        ServiceType serviceType = serviceCenterServiceTypeService.convertToEntity(serviceTypeDTO);

        assertNotNull(serviceType);
        assertEquals(serviceTypeDTO.getServiceName(), serviceType.getServiceName());
    }

    @Test
    void testConvertToDTO_success() {
        ServiceCenterServiceType entity = createSampleEntity();

        ServiceCenterServiceTypeDTO dto = serviceCenterServiceTypeService.convertToDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getServiceCenter().getId(), dto.getServiceCenterId());
        assertEquals(entity.getServiceType().getId(), dto.getServiceTypeId());
        assertEquals(entity.getCost(), dto.getCost());
    }

    @Test
    void testConvertToEntity_Success() {
        ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();
        dto.setId(1L);
        dto.setServiceCenterId(2L);
        dto.setServiceTypeId(3L);
        dto.setCost(100.00);

        ServiceCenter serviceCenter = new ServiceCenter();
        serviceCenter.setId(2L);

        ServiceType serviceType = new ServiceType();
        serviceType.setId(3L);

        when(serviceCenterRepository.findById(2L)).thenReturn(Optional.of(serviceCenter));
        when(serviceTypeRepository.findById(3L)).thenReturn(Optional.of(serviceType));

        ServiceCenterServiceType entity = serviceCenterServiceTypeService.convertToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(serviceCenter, entity.getServiceCenter());
        assertEquals(serviceType, entity.getServiceType());
        assertEquals(dto.getCost(), entity.getCost());
    }

    @Test
    void testConvertToEntity_ServiceCenterNotFound() {
        ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();
        dto.setId(1L);
        dto.setServiceCenterId(2L);
        dto.setServiceTypeId(3L);
        dto.setCost(100.00);

        when(serviceCenterRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            serviceCenterServiceTypeService.convertToEntity(dto);
        });

        assertEquals("ServiceCenter not found: 2", exception.getMessage());
    }

    @Test
    void testConvertToEntity_ServiceTypeNotFound() {
        ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();
        dto.setId(1L);
        dto.setServiceCenterId(2L);
        dto.setServiceTypeId(3L);
        dto.setCost(100.00);

        ServiceCenter serviceCenter = new ServiceCenter();
        serviceCenter.setId(2L);

        when(serviceCenterRepository.findById(2L)).thenReturn(Optional.of(serviceCenter));
        when(serviceTypeRepository.findById(3L)).thenReturn(Optional.empty());

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterServiceTypeService.convertToEntity(dto);
        });

        assertEquals("ServiceType not found: 3", exception.getMessage());
    }

    @Test
    void testConvertToDTO_Success() {
        ServiceCenterServiceType entity = new ServiceCenterServiceType();
        entity.setId(1L);

        ServiceCenter serviceCenter = new ServiceCenter();
        serviceCenter.setId(2L);
        entity.setServiceCenter(serviceCenter);

        ServiceType serviceType = new ServiceType();
        serviceType.setId(3L);
        entity.setServiceType(serviceType);

        entity.setCost(100.00);

        ServiceCenterServiceTypeDTO dto = serviceCenterServiceTypeService.convertToDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(serviceCenter.getId(), dto.getServiceCenterId());
        assertEquals(serviceType.getId(), dto.getServiceTypeId());
        assertEquals(entity.getCost(), dto.getCost());
    }

    @Test
    void testConvertServiceCenterDTOToEntity_Success() {
        ServiceCenterDTO dto = new ServiceCenterDTO();
        dto.setId(1L);
        dto.setName("Center A");
        dto.setAddress("123 Street");
        dto.setRating(4.5);
        dto.setAvailable(true);

        ServiceCenter entity = serviceCenterServiceTypeService.convertToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getAddress(), entity.getAddress());
        assertEquals(dto.getRating(), entity.getRating());
        assertEquals(dto.getAvailable(), entity.getAvailable());
    }

    @Test
    void testConvertServiceTypeDTOToEntity_Success() {
        ServiceTypeDTO dto = new ServiceTypeDTO();
        dto.setId(1L);
        dto.setServiceName("Oil Change");

        ServiceType entity = serviceCenterServiceTypeService.convertToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getServiceName(), entity.getServiceName());
    }
}