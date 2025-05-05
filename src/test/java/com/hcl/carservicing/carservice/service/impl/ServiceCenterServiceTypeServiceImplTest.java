package com.hcl.carservicing.carservice.service.impl;

import com.hcl.carservicing.carservice.dao.service.ServiceCenterDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceCenterServiceTypeDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceTypeDaoService;
import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.mapper.ServiceCenterMapper;
import com.hcl.carservicing.carservice.mapper.ServiceCenterServiceTypeMapper;
import com.hcl.carservicing.carservice.mapper.ServiceTypeMapper;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCenterServiceTypeServiceImplTest {

    @Mock
    ServiceTypeDaoService serviceTypeDaoService;

    @Mock
    ServiceCenterServiceTypeDaoService serviceCenterServiceTypeDaoService;

    @Mock
    ServiceCenterDaoService serviceCenterDaoService;

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

        when(serviceCenterDaoService.findById(dto.getServiceCenterId())).thenReturn(serviceCenter);
        when(serviceTypeDaoService.findById(dto.getServiceTypeId())).thenReturn(serviceType);

        ServiceCenterServiceType savedEntity = new ServiceCenterServiceType();
        savedEntity.setId(1L);

        when(serviceCenterServiceTypeDaoService.save(any(ServiceCenterServiceType.class))).thenReturn(savedEntity);

        serviceCenterServiceTypeService.addServiceTypeToCenter(dto);

        verify(serviceCenterServiceTypeDaoService).save(any(ServiceCenterServiceType.class));
    }



    @Test
    void updateServiceCenterServiceType() {
        Long id = 1L;
        ServiceCenterServiceTypeDTO dto = createSampleDTO();
        ServiceCenterServiceType existing = new ServiceCenterServiceType();
        existing.setId(id);

        when(serviceCenterServiceTypeDaoService.findById(id)).thenReturn(existing);
        when(serviceCenterDaoService.findById(dto.getServiceCenterId())).thenReturn(createSampleServiceCenter());
        when(serviceTypeDaoService.findById(dto.getServiceTypeId())).thenReturn(createSampleServiceType());

        when(serviceCenterServiceTypeDaoService.save(any(ServiceCenterServiceType.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        serviceCenterServiceTypeService.updateServiceCenterServiceType(id, dto);

        verify(serviceCenterServiceTypeDaoService).save(existing);
    }

    @Test
    void updateServiceCenterServiceTypeWhichThrowsElementNotFoundException() {
        Long id = 99L;
        ServiceCenterServiceTypeDTO dto = createSampleDTO();

        when(serviceCenterServiceTypeDaoService.findById(id))
                .thenThrow(new ElementNotFoundException("ServiceCenterServiceType not found"));

        Exception exception = assertThrows(ElementNotFoundException.class, () ->
                serviceCenterServiceTypeService.updateServiceCenterServiceType(id, dto));

        assertTrue(exception.getMessage().contains("ServiceCenterServiceType not found"));
    }

    @Test
    void getByServiceCenter() {

        Long serviceCenterId = 1L;
        ServiceCenterServiceType entity = createSampleEntity();

        when(serviceCenterServiceTypeDaoService.findByServiceCenterId(serviceCenterId)).thenReturn(List.of(entity));

        List<ServiceCenterServiceTypeDTO> result = serviceCenterServiceTypeService.getByServiceCenter(serviceCenterId);

        assertEquals(1, result.size());
        assertEquals(serviceCenterId, result.get(0).getServiceCenterId());
    }

    @Test
    void getByServiceType() {
        Long serviceTypeId = 1L;
        ServiceCenterServiceType entity = createSampleEntity();

        when(serviceCenterServiceTypeDaoService.findByServiceTypeId(serviceTypeId)).thenReturn(List.of(entity));

        List<ServiceCenterServiceTypeDTO> result = serviceCenterServiceTypeService.getByServiceType(serviceTypeId);

        assertEquals(1, result.size());
        assertEquals(serviceTypeId, result.get(0).getServiceTypeId());
    }

    @Test
    void testConvertToEntity_ServiceCenterServiceTypeDTO_success() {
        ServiceCenterServiceTypeDTO dto = createSampleDTO();

        when(serviceCenterDaoService.findById(dto.getServiceCenterId())).thenReturn(createSampleServiceCenter());
        when(serviceTypeDaoService.findById(dto.getServiceTypeId())).thenReturn(createSampleServiceType());

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

        ServiceCenter center = ServiceCenterMapper.convertToEntity(serviceCenterDTO);

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

        ServiceType serviceType = ServiceTypeMapper.convertToEntity(serviceTypeDTO);

        assertNotNull(serviceType);
        assertEquals(serviceTypeDTO.getServiceName(), serviceType.getServiceName());
    }

    @Test
    void testConvertToDTO_success() {
        ServiceCenterServiceType entity = createSampleEntity();

        ServiceCenterServiceTypeDTO dto = ServiceCenterServiceTypeMapper.convertToDTO(entity);

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

        when(serviceCenterDaoService.findById(2L)).thenReturn(serviceCenter);
        when(serviceTypeDaoService.findById(3L)).thenReturn(serviceType);

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

        when(serviceCenterDaoService.findById(2L))
                .thenThrow(
                        new ElementNotFoundException(
                                "Service center not found with ID: " + dto.getServiceCenterId()));

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterServiceTypeService.convertToEntity(dto);
        });

        assertEquals("Service center not found with ID: " + dto.getServiceCenterId(), exception.getMessage());
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

        when(serviceCenterDaoService.findById(2L)).thenReturn(serviceCenter);
        when(serviceTypeDaoService.findById(3L)).thenThrow(new ElementNotFoundException("ServiceType not found: " + dto.getServiceTypeId()));

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

        ServiceCenterServiceTypeDTO dto = ServiceCenterServiceTypeMapper.convertToDTO(entity);

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

        ServiceCenter entity = ServiceCenterMapper.convertToEntity(dto);

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

        ServiceType entity = ServiceTypeMapper.convertToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getServiceName(), entity.getServiceName());
    }
}