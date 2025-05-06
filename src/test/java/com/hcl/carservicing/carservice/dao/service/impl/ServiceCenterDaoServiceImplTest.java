package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServiceCenterDaoServiceImplTest {

    @Mock
    private ServiceCenterRepository serviceCenterRepository;

    @InjectMocks
    private ServiceCenterDaoServiceImpl serviceCenterDaoService;

    @Test
    public void findById_serviceCenterFound_returnsServiceCenter() {
        // Arrange
        Long id = 1L;
        ServiceCenter expectedServiceCenter = new ServiceCenter();
        expectedServiceCenter.setId(id);
        Optional<ServiceCenter> serviceCenterOptional = Optional.of(expectedServiceCenter);

        when(serviceCenterRepository.findById(id)).thenReturn(serviceCenterOptional);

        // Act
        ServiceCenter actualServiceCenter = serviceCenterDaoService.findById(id);

        // Assert
        assertEquals(expectedServiceCenter, actualServiceCenter);
        verify(serviceCenterRepository).findById(id);
    }

    @Test
    public void findById_serviceCenterNotFound_throwsElementNotFoundException() {
        // Arrange
        Long id = 2L;
        Optional<ServiceCenter> serviceCenterOptional = Optional.empty();

        when(serviceCenterRepository.findById(id)).thenReturn(serviceCenterOptional);

        // Act & Assert
        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterDaoService.findById(id);
        });
        assertEquals("Service center not found with ID: " + id, exception.getMessage());
        verify(serviceCenterRepository).findById(id);
    }

    @Test
    public void findAll_serviceCentersFound_returnsListOfServiceCenters() {
        // Arrange
        List<ServiceCenter> expectedServiceCenters = new ArrayList<>();
        ServiceCenter center1 = new ServiceCenter();
        center1.setId(1L);
        ServiceCenter center2 = new ServiceCenter();
        center2.setId(2L);
        expectedServiceCenters.add(center1);
        expectedServiceCenters.add(center2);

        when(serviceCenterRepository.findAll()).thenReturn(expectedServiceCenters);

        // Act
        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findAll();

        // Assert
        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findAll();
    }

    @Test
    public void findAll_noServiceCentersFound_returnsEmptyList() {
        // Arrange
        List<ServiceCenter> expectedServiceCenters = new ArrayList<>();

        when(serviceCenterRepository.findAll()).thenReturn(expectedServiceCenters);

        // Act
        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findAll();

        // Assert
        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findAll();
    }

    @Test
    public void findByAvailable_serviceCentersFound_returnsListOfAvailableServiceCenters() {
        // Arrange
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

        // Act
        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findByAvailable(available);

        // Assert
        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findByAvailable(available);
    }

    @Test
    public void findByAvailable_noServiceCentersFound_returnsEmptyList() {
        // Arrange
        boolean available = false;
        List<ServiceCenter> expectedServiceCenters = new ArrayList<>();

        when(serviceCenterRepository.findByAvailable(available)).thenReturn(expectedServiceCenters);

        // Act
        List<ServiceCenter> actualServiceCenters = serviceCenterDaoService.findByAvailable(available);

        // Assert
        assertEquals(expectedServiceCenters, actualServiceCenters);
        verify(serviceCenterRepository).findByAvailable(available);
    }

    @Test
    public void save_validServiceCenter_returnsSavedServiceCenter() {
        // Arrange
        ServiceCenter serviceCenterToSave = new ServiceCenter();
        serviceCenterToSave.setName("Test Service Center");
        serviceCenterToSave.setAvailable(true);
        ServiceCenter savedServiceCenter = new ServiceCenter(); // Mock the saved service center
        savedServiceCenter.setId(1L);
        savedServiceCenter.setName("Test Service Center");
        savedServiceCenter.setAvailable(true);

        when(serviceCenterRepository.save(serviceCenterToSave)).thenReturn(savedServiceCenter);

        // Act
        ServiceCenter result = serviceCenterDaoService.save(serviceCenterToSave);

        // Assert
        assertEquals(savedServiceCenter, result);
        verify(serviceCenterRepository).save(serviceCenterToSave);
    }
}
