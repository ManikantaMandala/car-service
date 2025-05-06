package com.hcl.carservicing.carservice.service.impl;

import com.hcl.carservicing.carservice.dao.service.ServiceTypeDaoService;
import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTypeServiceImplTest {

    @Mock
    private ServiceTypeDaoService serviceTypeDaoService;

    @InjectMocks
    private ServiceTypeServiceImpl serviceTypeService;

    private Long id;
    private ServiceTypeDTO serviceTypeDTO;
    private ServiceType serviceType;

    @BeforeEach
    void setUp() {

        serviceTypeDTO = new ServiceTypeDTO();
        serviceTypeDTO.setId(1L);
        serviceTypeDTO.setServiceName("Test Service Type");
        serviceTypeDTO.setDescription("Test Description");

        serviceType = new ServiceType();
        serviceType.setId(1L);
        serviceType.setServiceName("Test Service Type");
        serviceType.setDescription("Test Description");
    }

    @Test
    void testCreateServiceType() {
        when(serviceTypeDaoService.save(any(ServiceType.class))).thenReturn(serviceType);

        serviceTypeService.createServiceType(serviceTypeDTO);

        verify(serviceTypeDaoService, times(1)).save(any(ServiceType.class));
    }

    @Test
    void testUpdateServiceType() {
        id = 1L;
        when(serviceTypeDaoService.findById(id)).thenReturn(serviceType);
        when(serviceTypeDaoService.save(any(ServiceType.class))).thenReturn(serviceType);

        serviceTypeService.updateServiceType(id, serviceTypeDTO);

        verify(serviceTypeDaoService, times(1)).save(any(ServiceType.class));
    }

    @Test
    void testUpdateServiceType_NotFound() {
        id = 1L;
        doThrow(ElementNotFoundException.class).when(serviceTypeDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceTypeService.updateServiceType(id, serviceTypeDTO);
        });
    }

    @Test
    void testGetServiceTypeById() {
        id = 1L;
        when(serviceTypeDaoService.findById(id)).thenReturn(serviceType);

        ServiceTypeDTO result = serviceTypeService.getServiceTypeById(id);

        assertEquals("Test Service Type", result.getServiceName());
    }

    @Test
    void testGetServiceTypeById_NotFound() {
        id = 1L;
        doThrow(ElementNotFoundException.class).when(serviceTypeDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceTypeService.getServiceTypeById(1L);
        });
    }

    @Test
    void testGetAllServiceTypes() {
        when(serviceTypeDaoService.findAll()).thenReturn(List.of(serviceType));

        List<ServiceTypeDTO> result = serviceTypeService.getAllServiceTypes();

        assertEquals(1, result.size());
        assertEquals("Test Service Type", result.get(0).getServiceName());
    }

    @Test
    void testFindById() {
        id = 1L;
        when(serviceTypeDaoService.findById(id)).thenReturn(serviceType);

        ServiceTypeDTO result = serviceTypeService.findById(id);

        assertEquals("Test Service Type", result.getServiceName());
    }

    @Test
    void testFindById_NotFound() {
        id = 1L;
        doThrow(ElementNotFoundException.class).when(serviceTypeDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceTypeService.findById(id);
        });
    }

}