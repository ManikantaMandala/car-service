package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceTypeRepository;
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
class ServiceTypeDaoServiceImplTest {

    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @InjectMocks
    private ServiceTypeDaoServiceImpl serviceTypeDaoService;

    @Test
    void findById_serviceTypeFound_returnsServiceType() {
        Long id = 1L;
        ServiceType expectedServiceType = new ServiceType();
        expectedServiceType.setId(id);
        Optional<ServiceType> serviceTypeOptional = Optional.of(expectedServiceType);

        when(serviceTypeRepository.findById(id)).thenReturn(serviceTypeOptional);

        ServiceType actualServiceType = serviceTypeDaoService.findById(id);

        assertEquals(expectedServiceType, actualServiceType);
        verify(serviceTypeRepository).findById(id);
    }

    @Test
    void findById_serviceTypeNotFound_throwsElementNotFoundException() {
        Long id = 2L;
        Optional<ServiceType> serviceTypeOptional = Optional.empty();

        when(serviceTypeRepository.findById(id)).thenReturn(serviceTypeOptional);

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {  // Changed exception type.
            serviceTypeDaoService.findById(id);
        });
        assertEquals("ServiceType not found: " + id, exception.getMessage());
        verify(serviceTypeRepository).findById(id);
    }

    @Test
    void findAll_serviceTypesFound_returnsListOfServiceTypes() {
        List<ServiceType> expectedServiceTypes = new ArrayList<>();
        ServiceType type1 = new ServiceType();
        type1.setId(1L);
        ServiceType type2 = new ServiceType();
        type2.setId(2L);
        expectedServiceTypes.add(type1);
        expectedServiceTypes.add(type2);

        when(serviceTypeRepository.findAll()).thenReturn(expectedServiceTypes);

        List<ServiceType> actualServiceTypes = serviceTypeDaoService.findAll();

        assertEquals(expectedServiceTypes, actualServiceTypes);
        verify(serviceTypeRepository).findAll();
    }

    @Test
    void findAll_noServiceTypesFound_returnsEmptyList() {
        List<ServiceType> expectedServiceTypes = new ArrayList<>();

        when(serviceTypeRepository.findAll()).thenReturn(expectedServiceTypes);

        List<ServiceType> actualServiceTypes = serviceTypeDaoService.findAll();

        assertEquals(expectedServiceTypes, actualServiceTypes);
        verify(serviceTypeRepository).findAll();
    }

    @Test
    void save_validServiceType_returnsSavedServiceType() {
        ServiceType typeToSave = new ServiceType();
        typeToSave.setServiceName("Test Service Type");

        ServiceType savedType = new ServiceType();
        savedType.setId(1L);
        savedType.setServiceName("Test Service Type");

        when(serviceTypeRepository.save(typeToSave)).thenReturn(savedType);

        ServiceType result = serviceTypeDaoService.save(typeToSave);

        assertEquals(savedType, result);
        verify(serviceTypeRepository).save(typeToSave);
    }
}
