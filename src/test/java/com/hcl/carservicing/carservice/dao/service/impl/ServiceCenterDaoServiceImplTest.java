package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServiceCenterDaoServiceImplTest {

    @Mock
    private ServiceCenterRepository serviceCenterRepository;

    @InjectMocks
    private ServiceCenterDaoServiceImpl serviceCenterDaoService;

    @Test
    void findById_serviceCenterFound_returnsServiceCenter() {
        Long id = 1L;
        ServiceCenter expectedServiceCenter = new ServiceCenter();
        expectedServiceCenter.setId(id);
        Optional<ServiceCenter> serviceCenterOptional = Optional.of(expectedServiceCenter);

        when(serviceCenterRepository.findById(id)).thenReturn(serviceCenterOptional);

        ServiceCenter actualServiceCenter = serviceCenterDaoService.findById(id);

        assertEquals(expectedServiceCenter, actualServiceCenter);
        verify(serviceCenterRepository).findById(id);
    }

    @Test
    void findById_serviceCenterNotFound_throwsElementNotFoundException() {
        Long id = 2L;
        Optional<ServiceCenter> serviceCenterOptional = Optional.empty();

        when(serviceCenterRepository.findById(id)).thenReturn(serviceCenterOptional);

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterDaoService.findById(id);
        });
        assertEquals("Service center not found with ID: " + id, exception.getMessage());
        verify(serviceCenterRepository).findById(id);
    }

    @Test
    void findAll_serviceCentersFound_returnsListOfServiceCenters() {
        List<ServiceCenter> expectedServiceCenters = new ArrayList<>();
        ServiceCenter center1 = new ServiceCenter();
        center1.setId(1L);
        ServiceCenter center2 = new ServiceCenter();

        center2.setId(2L);
        expectedServiceCenters.add(center1);
        expectedServiceCenters.add(center2);

        when(serviceCenterRepository.findAll()).thenReturn(expectedServiceCenters);

        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findAll();

        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findAll();
    }

    @Test
    void findAll_noServiceCentersFound_returnsEmptyList() {
        List<ServiceCenter> expectedServiceCenters = new ArrayList<>();

        when(serviceCenterRepository.findAll()).thenReturn(expectedServiceCenters);

        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findAll();

        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findAll();
    }

    @Test
    void findByAvailable_serviceCentersFound_returnsListOfAvailableServiceCenters() {
        boolean available = true;
        List<ServiceCenter> expectedServiceCenters = new ArrayList<>();

        ServiceCenter center1 = new ServiceCenter();
        center1.setId(1L);
        center1.setAvailable(true);

        ServiceCenter center2 = new ServiceCenter();
        center2.setId(2L);
        center2.setAvailable(true);

        expectedServiceCenters.add(center1);
        expectedServiceCenters.add(center2);

        when(serviceCenterRepository.findByAvailable(available)).thenReturn(expectedServiceCenters);

        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findByAvailable(available);

        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findByAvailable(available);
    }

    @Test
    void findByAvailable_noServiceCentersFound_returnsEmptyList() {
        boolean available = false;
        List<ServiceCenter> expectedServiceCenters = new ArrayList<>();

        when(serviceCenterRepository.findByAvailable(available)).thenReturn(expectedServiceCenters);

        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findByAvailable(available);

        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findByAvailable(available);
    }

    @Test
    void save_validServiceCenter_returnsSavedServiceCenter() {
        ServiceCenter serviceCenterToSave = new ServiceCenter();
        serviceCenterToSave.setName("Test Service Center");
        serviceCenterToSave.setAvailable(true);

        ServiceCenter savedServiceCenter = new ServiceCenter();
        savedServiceCenter.setId(1L);
        savedServiceCenter.setName("Test Service Center");
        savedServiceCenter.setAvailable(true);

        when(serviceCenterRepository.save(serviceCenterToSave)).thenReturn(savedServiceCenter);

        ServiceCenter result = serviceCenterDaoService.save(serviceCenterToSave);

        assertEquals(savedServiceCenter, result);
        verify(serviceCenterRepository).save(serviceCenterToSave);
    }
}
