package com.hcl.carservicing.carservice.service.impl;

import com.hcl.carservicing.carservice.dao.service.ServiceCenterDaoService;
import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceCenterServiceImplTest {

    @Mock
    private ServiceCenterDaoService serviceCenterDaoService;

    @InjectMocks
    private ServiceCenterServiceImpl serviceCenterService;

    private ServiceCenterDTO serviceCenterDTO;
    private ServiceCenter serviceCenter;
    private long id;

    @BeforeEach
    void setUp() {

        serviceCenterDTO = new ServiceCenterDTO();
        serviceCenterDTO.setId(1L);
        serviceCenterDTO.setName("Test Service Center");
        serviceCenterDTO.setAddress("123 Test St");
        serviceCenterDTO.setRating(4.5);
        serviceCenterDTO.setAvailable(true);

        serviceCenter = new ServiceCenter();
        serviceCenter.setId(1L);
        serviceCenter.setName("Test Service Center");
        serviceCenter.setAddress("123 Test St");
        serviceCenter.setRating(4.5);
        serviceCenter.setAvailable(true);
    }

    @Test
    void testCreateServiceCenter() {
        when(serviceCenterDaoService.save(any(ServiceCenter.class))).thenReturn(serviceCenter);

        serviceCenterService.createServiceCenter(serviceCenterDTO);

        verify(serviceCenterDaoService, times(1)).save(any(ServiceCenter.class));
    }

    @Test
    void testUpdateServiceCenter() {
        id = 1L;
        when(serviceCenterDaoService.findById(id)).thenReturn(serviceCenter);
        when(serviceCenterDaoService.save(any(ServiceCenter.class))).thenReturn(serviceCenter);

        serviceCenterService.updateServiceCenter(id, serviceCenterDTO);

        verify(serviceCenterDaoService, times(1)).save(any(ServiceCenter.class));
    }

    @Test
    void testUpdateServiceCenter_NotFound() {
        id = 1L;
        doThrow(ElementNotFoundException.class).when(serviceCenterDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterService.updateServiceCenter(id, serviceCenterDTO);
        });
    }

    @Test
    void testGetAllServiceCenters() {
        when(serviceCenterDaoService.findAll()).thenReturn(List.of(serviceCenter));

        List<ServiceCenterDTO> result = serviceCenterService.getAllServiceCenters();

        assertEquals(1, result.size());
        assertEquals("Test Service Center", result.get(0).getName());
    }

    @Test
    void testGetAvailableServiceCenters() {
        when(serviceCenterDaoService.findByAvailable(true)).thenReturn(List.of(serviceCenter));

        List<ServiceCenterDTO> result = serviceCenterService.getAvailableServiceCenters(true);

        assertEquals(1, result.size());
        assertEquals(true, result.get(0).getAvailable());
    }

    @Test
    void testGetServiceCenterById() {
        id = 1L;
        when(serviceCenterDaoService.findById(id)).thenReturn(serviceCenter);

        ServiceCenterDTO result = serviceCenterService.getServiceCenterById(id);

        assertEquals("Test Service Center", result.getName());
    }

    @Test
    void testGetServiceCenterById_NotFound() {
        id = 1L;
        doThrow(ElementNotFoundException.class).when(serviceCenterDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterService.getServiceCenterById(id);
        });
    }

    @Test
    void testUpdateStatusOfServiceCenter() {
        id = 1L;
        when(serviceCenterDaoService.findById(id)).thenReturn(serviceCenter);
        when(serviceCenterDaoService.save(any(ServiceCenter.class))).thenReturn(serviceCenter);

        serviceCenterService.updateStatusOfServiceCenter(id, false);

        verify(serviceCenterDaoService, times(1)).save(any(ServiceCenter.class));
    }

    @Test
    void testUpdateStatusOfServiceCenter_NotFound() {
        id = 1L;
        doThrow(ElementNotFoundException.class).when(serviceCenterDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterService.updateStatusOfServiceCenter(id, false);
        });
    }

}