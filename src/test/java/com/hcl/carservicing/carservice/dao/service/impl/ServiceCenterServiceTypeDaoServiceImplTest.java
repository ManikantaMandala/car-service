package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceCenterServiceTypeRepository;
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
class ServiceCenterServiceTypeDaoServiceImplTest {

    @Mock
    private ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository;

    @InjectMocks
    private ServiceCenterServiceTypeDaoServiceImpl serviceCenterServiceTypeDaoService;

    @Test
    void findById_serviceCenterServiceTypeFound_returnsServiceCenterServiceType() {
        Long id = 1L;
        ServiceCenterServiceType expectedServiceCenterServiceType = new ServiceCenterServiceType();
        expectedServiceCenterServiceType.setId(id);
        Optional<ServiceCenterServiceType> optional = Optional.of(expectedServiceCenterServiceType);

        when(serviceCenterServiceTypeRepository.findById(id)).thenReturn(optional);

        ServiceCenterServiceType actual = serviceCenterServiceTypeDaoService.findById(id);

        assertEquals(expectedServiceCenterServiceType, actual);
        verify(serviceCenterServiceTypeRepository).findById(id);
    }

    @Test
    void findById_serviceCenterServiceTypeNotFound_throwsElementNotFoundException() {
        Long id = 2L;
        Optional<ServiceCenterServiceType> optional = Optional.empty();

        when(serviceCenterServiceTypeRepository.findById(id)).thenReturn(optional);

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            serviceCenterServiceTypeDaoService.findById(id);
        });
        assertEquals("ServiceCenterServiceType not found: " + id, exception.getMessage());
        verify(serviceCenterServiceTypeRepository).findById(id);
    }

    @Test
    void save_validServiceCenterServiceType_returnsSavedServiceCenterServiceType() {
        ServiceCenter serviceCenter = new ServiceCenter();
        ServiceType serviceType = new ServiceType();

        ServiceCenterServiceType toSave = new ServiceCenterServiceType();
        toSave.setServiceCenter(serviceCenter);
        toSave.setServiceType(serviceType);

        ServiceCenterServiceType saved = new ServiceCenterServiceType();
        saved.setId(1L);
        saved.setServiceCenter(serviceCenter);
        saved.setServiceType(serviceType);

        when(serviceCenterServiceTypeRepository.save(toSave)).thenReturn(saved);

        ServiceCenterServiceType result = serviceCenterServiceTypeDaoService.save(toSave);

        assertEquals(saved, result);
        verify(serviceCenterServiceTypeRepository).save(toSave);
    }

    @Test
    void findByServiceCenterId_serviceCenterIdFound_returnsList() {
        ServiceCenter serviceCenter = new ServiceCenter();

        Long serviceCenterId = 1L;
        List<ServiceCenterServiceType> expectedList = new ArrayList<>();

        ServiceCenterServiceType type1 = new ServiceCenterServiceType();
        type1.setServiceCenter(serviceCenter);

        ServiceCenterServiceType type2 = new ServiceCenterServiceType();
        type2.setServiceCenter(serviceCenter);

        expectedList.add(type1);
        expectedList.add(type2);

        when(serviceCenterServiceTypeRepository.findByServiceCenterId(serviceCenterId)).thenReturn(expectedList);

        List<ServiceCenterServiceType> actualList = serviceCenterServiceTypeDaoService.findByServiceCenterId(serviceCenterId);

        assertEquals(expectedList, actualList);
        verify(serviceCenterServiceTypeRepository).findByServiceCenterId(serviceCenterId);
    }

    @Test
    void findByServiceCenterId_serviceCenterIdNotFound_returnsEmptyList() {
        Long serviceCenterId = 1L;
        List<ServiceCenterServiceType> expectedList = new ArrayList<>();

        when(serviceCenterServiceTypeRepository.findByServiceCenterId(serviceCenterId)).thenReturn(expectedList);

        List<ServiceCenterServiceType> actualList = serviceCenterServiceTypeDaoService.findByServiceCenterId(serviceCenterId);

        assertEquals(expectedList, actualList);
        verify(serviceCenterServiceTypeRepository).findByServiceCenterId(serviceCenterId);
    }

    @Test
    void findByServiceTypeId_serviceTypeIdFound_returnsList() {
        ServiceType serviceType = new ServiceType();

        Long serviceTypeId = 2L;
        List<ServiceCenterServiceType> expectedList = new ArrayList<>();
        ServiceCenterServiceType type1 = new ServiceCenterServiceType();
        type1.setServiceType(serviceType);

        ServiceCenterServiceType type2 = new ServiceCenterServiceType();
        type2.setServiceType(serviceType);
        expectedList.add(type1);
        expectedList.add(type2);

        when(serviceCenterServiceTypeRepository.findByServiceTypeId(serviceTypeId)).thenReturn(expectedList);

        List<ServiceCenterServiceType> actualList = serviceCenterServiceTypeDaoService.findByServiceTypeId(serviceTypeId);

        assertEquals(expectedList, actualList);
        verify(serviceCenterServiceTypeRepository).findByServiceTypeId(serviceTypeId);
    }

    @Test
    void findByServiceTypeId_serviceTypeIdNotFound_returnsEmptyList() {
        Long serviceTypeId = 2L;
        List<ServiceCenterServiceType> expectedList = new ArrayList<>();

        when(serviceCenterServiceTypeRepository.findByServiceTypeId(serviceTypeId)).thenReturn(expectedList);

        List<ServiceCenterServiceType> actualList = serviceCenterServiceTypeDaoService.findByServiceTypeId(serviceTypeId);

        assertEquals(expectedList, actualList);
        verify(serviceCenterServiceTypeRepository).findByServiceTypeId(serviceTypeId);
    }
}
