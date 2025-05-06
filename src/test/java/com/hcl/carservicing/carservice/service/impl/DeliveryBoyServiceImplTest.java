package com.hcl.carservicing.carservice.service.impl;

import com.hcl.carservicing.carservice.dao.service.DeliveryBoyDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceCenterDaoService;
import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
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
class DeliveryBoyServiceImplTest {

    @Mock
    private DeliveryBoyDaoService deliveryBoyDaoService;

    @Mock
    private ServiceCenterDaoService serviceCenterDaoService;

    @InjectMocks
    private DeliveryBoyServiceImpl deliveryBoyService;

    private DeliveryBoyDTO deliveryBoyDTO;
    private DeliveryBoy deliveryBoy;
    private ServiceCenter serviceCenter;

    @BeforeEach
    void setUp() {

        serviceCenter = new ServiceCenter();
        serviceCenter.setId(1L);
        serviceCenter.setName("Test Service Center");

        deliveryBoyDTO = new DeliveryBoyDTO();
        deliveryBoyDTO.setName("Test Delivery Boy");
        deliveryBoyDTO.setContactNumber("1234567890");
        deliveryBoyDTO.setServiceCenterId(1L);

        deliveryBoy = new DeliveryBoy();
        deliveryBoy.setId(1L);
        deliveryBoy.setName("Test Delivery Boy");
        deliveryBoy.setContactNumber("1234567890");
        deliveryBoy.setServiceCenter(serviceCenter);
    }

    @Test
    void testCreateDeliveryBoy() {
        when(serviceCenterDaoService.findById(1L)).thenReturn(serviceCenter);
        when(deliveryBoyDaoService.save(any(DeliveryBoy.class))).thenReturn(deliveryBoy);

        deliveryBoyService.createDeliveryBoy(deliveryBoyDTO);

        verify(deliveryBoyDaoService, times(1)).save(any(DeliveryBoy.class));
    }

    @Test
    void testCreateDeliveryBoy_ContactNumberExists() {
        doThrow(
                new ElementAlreadyExistException(
                        "Contact Number already exists: " + deliveryBoyDTO.getContactNumber()))
                .when(deliveryBoyDaoService).throwIfContactExists(deliveryBoyDTO.getContactNumber());

        assertThrows(ElementAlreadyExistException.class, () -> {
            deliveryBoyService.createDeliveryBoy(deliveryBoyDTO);
        });
    }

    @Test
    void testCreateDeliveryBoy_InvalidServiceCenterId() {
        long id = 1L;
        doThrow(new ElementNotFoundException("Service center not found with ID: " + id))
                .when(serviceCenterDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            deliveryBoyService.createDeliveryBoy(deliveryBoyDTO);
        });
    }

    @Test
    void testUpdateDeliveryBoy() {
        when(deliveryBoyDaoService.findById(1L)).thenReturn(deliveryBoy);
        when(serviceCenterDaoService.findById(1L)).thenReturn(serviceCenter);
        when(deliveryBoyDaoService.save(any(DeliveryBoy.class))).thenReturn(deliveryBoy);

        DeliveryBoyDTO updatedDTO = deliveryBoyService.updateDeliveryBoy(1L, deliveryBoyDTO);

        assertEquals("Test Delivery Boy", updatedDTO.getName());
        verify(deliveryBoyDaoService, times(1)).save(any(DeliveryBoy.class));
    }

    @Test
    void testUpdateDeliveryBoy_NotFound() {
        long id = 1L;
        doThrow(new ElementNotFoundException("")).when(deliveryBoyDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            deliveryBoyService.updateDeliveryBoy(id, deliveryBoyDTO);
        });
    }

    @Test
    void testUpdateDeliveryBoy_InvalidServiceCenterId() {
        long id = 1L;
        when(deliveryBoyDaoService.findById(id)).thenReturn(deliveryBoy);
        doThrow(new ElementNotFoundException("")).when(serviceCenterDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            deliveryBoyService.updateDeliveryBoy(id, deliveryBoyDTO);
        });
    }

    @Test
    void testGetDeliveryBoysByCenter() {
        when(deliveryBoyDaoService.findByServiceCenterId(1L)).thenReturn(List.of(deliveryBoy));

        List<DeliveryBoyDTO> deliveryBoys = deliveryBoyService.getDeliveryBoysByCenter(1L);

        assertEquals(1, deliveryBoys.size());
        assertEquals("Test Delivery Boy", deliveryBoys.get(0).getName());
    }

    @Test
    void testGetAvailableDeliveryBoys() {
        when(deliveryBoyDaoService.findAvailableDeliveryBoys()).thenReturn(List.of(deliveryBoy));

        List<DeliveryBoyDTO> availableDeliveryBoys = deliveryBoyService.getAvailableDeliveryBoys();

        assertEquals(1, availableDeliveryBoys.size());
        assertEquals("Test Delivery Boy", availableDeliveryBoys.get(0).getName());
    }

    @Test
    void testGetDeliveryBoysByCenter_InvalidServiceCenterId() {
        assertThrows(IllegalArgumentException.class, () -> {
            deliveryBoyService.getDeliveryBoysByCenter(-1L);
        });
    }

}