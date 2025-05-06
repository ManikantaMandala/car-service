package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.repository.DeliveryBoyRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeliveryBoyDaoServiceImplTest {

    @Mock
    private DeliveryBoyRepository deliveryBoyRepository;

    @InjectMocks
    private DeliveryBoyDaoServiceImpl deliveryBoyDaoService;

    @Test
    void findById_deliveryBoyFound_returnsDeliveryBoy() {
        Long deliveryBoyId = 1L;
        DeliveryBoy expectedDeliveryBoy = new DeliveryBoy();
        expectedDeliveryBoy.setId(deliveryBoyId);
        Optional<DeliveryBoy> deliveryBoyOptional = Optional.of(expectedDeliveryBoy);

        when(deliveryBoyRepository.findById(deliveryBoyId)).thenReturn(deliveryBoyOptional);

        DeliveryBoy actualDeliveryBoy = deliveryBoyDaoService.findById(deliveryBoyId);

        assertEquals(expectedDeliveryBoy, actualDeliveryBoy);
        verify(deliveryBoyRepository).findById(deliveryBoyId);
    }

    @Test
    void findById_deliveryBoyNotFound_throwsElementNotFoundException() {
        Long deliveryBoyId = 2L;
        Optional<DeliveryBoy> deliveryBoyOptional = Optional.empty();

        when(deliveryBoyRepository.findById(deliveryBoyId)).thenReturn(deliveryBoyOptional);

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> {
            deliveryBoyDaoService.findById(deliveryBoyId);
        });
        assertEquals("Delivery boy not found: " + deliveryBoyId, exception.getMessage());
        verify(deliveryBoyRepository).findById(deliveryBoyId);
    }

    @Test
    void findByServiceCenterId_deliveryBoysFound_returnsListOfDeliveryBoys() {
        Long serviceCenterId = 101L;

        ServiceCenter serviceCenter = new ServiceCenter();

        List<DeliveryBoy> expectedDeliveryBoys = new ArrayList<>();
        DeliveryBoy boy1 = new DeliveryBoy();
        boy1.setServiceCenter(serviceCenter);
        DeliveryBoy boy2 = new DeliveryBoy();
        boy2.setServiceCenter(serviceCenter);
        expectedDeliveryBoys.add(boy1);
        expectedDeliveryBoys.add(boy2);

        when(deliveryBoyRepository.findByServiceCenterId(serviceCenterId)).thenReturn(expectedDeliveryBoys);

        List<DeliveryBoy> actualDeliveryBoys = deliveryBoyDaoService.findByServiceCenterId(serviceCenterId);

        assertEquals(expectedDeliveryBoys, actualDeliveryBoys);
        verify(deliveryBoyRepository).findByServiceCenterId(serviceCenterId);
    }

    @Test
    void findByServiceCenterId_noDeliveryBoysFound_returnsEmptyList() {
        Long serviceCenterId = 102L;
        List<DeliveryBoy> expectedDeliveryBoys = new ArrayList<>();

        when(deliveryBoyRepository.findByServiceCenterId(serviceCenterId)).thenReturn(expectedDeliveryBoys);

        List<DeliveryBoy> actualDeliveryBoys = deliveryBoyDaoService.findByServiceCenterId(serviceCenterId);

        assertEquals(expectedDeliveryBoys, actualDeliveryBoys);
        verify(deliveryBoyRepository).findByServiceCenterId(serviceCenterId);
    }

    @Test
    void findAvailableDeliveryBoys_deliveryBoysFound_returnsListOfDeliveryBoys() {
        List<DeliveryBoy> expectedDeliveryBoys = new ArrayList<>();
        DeliveryBoy boy1 = new DeliveryBoy();
        DeliveryBoy boy2 = new DeliveryBoy();

        expectedDeliveryBoys.add(boy1);
        expectedDeliveryBoys.add(boy2);

        when(deliveryBoyRepository.findWithLessThanFourServiceRequests()).thenReturn(expectedDeliveryBoys);

        List<DeliveryBoy> actualDeliveryBoys = deliveryBoyDaoService.findAvailableDeliveryBoys();

        assertEquals(expectedDeliveryBoys, actualDeliveryBoys);
        verify(deliveryBoyRepository).findWithLessThanFourServiceRequests();
    }

    @Test
    void findAvailableDeliveryBoys_noDeliveryBoysFound_returnsEmptyList() {
        List<DeliveryBoy> expectedDeliveryBoys = new ArrayList<>();

        when(deliveryBoyRepository.findWithLessThanFourServiceRequests()).thenReturn(expectedDeliveryBoys);

        List<DeliveryBoy> actualDeliveryBoys = deliveryBoyDaoService.findAvailableDeliveryBoys();

        assertEquals(expectedDeliveryBoys, actualDeliveryBoys);
        verify(deliveryBoyRepository).findWithLessThanFourServiceRequests();
    }

    @Test
    void throwIfContactExists_contactExists_throwsElementAlreadyExistException() {
        String contactNumber = "1122334455";
        DeliveryBoy existingDeliveryBoy = new DeliveryBoy();
        existingDeliveryBoy.setContactNumber(contactNumber);
        Optional<DeliveryBoy> deliveryBoyOptional = Optional.of(existingDeliveryBoy);

        when(deliveryBoyRepository.findByContactNumber(contactNumber)).thenReturn(deliveryBoyOptional);

        ElementAlreadyExistException exception = assertThrows(ElementAlreadyExistException.class, () -> {
            deliveryBoyDaoService.throwIfContactExists(contactNumber);
        });
        assertEquals("Contact Number already exists: " + contactNumber, exception.getMessage());
        verify(deliveryBoyRepository).findByContactNumber(contactNumber);
    }

    @Test
    void throwIfContactExists_contactDoesNotExist_doesNotThrowException() {
        String contactNumber = "9988776655";
        Optional<DeliveryBoy> deliveryBoyOptional = Optional.empty();

        when(deliveryBoyRepository.findByContactNumber(contactNumber)).thenReturn(deliveryBoyOptional);

        deliveryBoyDaoService.throwIfContactExists(contactNumber);

        verify(deliveryBoyRepository).findByContactNumber(contactNumber);
    }

    @Test
    void save_validDeliveryBoy_returnsSavedDeliveryBoy() {
        DeliveryBoy deliveryBoyToSave = new DeliveryBoy();
        deliveryBoyToSave.setName("John Doe");
        deliveryBoyToSave.setContactNumber("1234567890");

        DeliveryBoy savedDeliveryBoy = new DeliveryBoy();
        savedDeliveryBoy.setId(1L);
        savedDeliveryBoy.setName("John Doe");
        savedDeliveryBoy.setContactNumber("1234567890");

        when(deliveryBoyRepository.save(deliveryBoyToSave)).thenReturn(savedDeliveryBoy);

        DeliveryBoy result = deliveryBoyDaoService.save(deliveryBoyToSave);

        assertEquals(savedDeliveryBoy, result);
        verify(deliveryBoyRepository).save(deliveryBoyToSave);
    }
}
