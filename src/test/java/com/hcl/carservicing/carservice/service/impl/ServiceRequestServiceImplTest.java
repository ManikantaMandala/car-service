package com.hcl.carservicing.carservice.service.impl;

import com.hcl.carservicing.carservice.dao.service.AppUserDaoService;
import com.hcl.carservicing.carservice.dao.service.DeliveryBoyDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceCenterServiceTypeDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceRequestDaoService;
import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.*;
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
class ServiceRequestServiceImplTest {

    @Mock
    private ServiceRequestDaoService serviceRequestDaoService;

    @Mock
    private AppUserDaoService appUserDaoService;

    @Mock
    private DeliveryBoyDaoService deliveryBoyDaoService;

    @Mock
    private ServiceCenterServiceTypeDaoService serviceCenterServiceTypeDaoService;

    @InjectMocks
    private ServiceRequestServiceImpl serviceRequestService;

    private long id;
    private ServiceRequestDTO serviceRequestDTO;
    private ServiceRequest serviceRequest;
    private AppUser appUser;
    private DeliveryBoy deliveryBoy;
    private ServiceCenterServiceType serviceCenterServiceType;
    private ServiceCenter serviceCenter;

    @BeforeEach
    void setUp() {

        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setUsername("testuser");

        deliveryBoy = new DeliveryBoy();
        deliveryBoy.setId(1L);
        deliveryBoy.setName("Test Delivery Boy");

        serviceCenter = new ServiceCenter();
        serviceCenter.setId(1L);
        serviceCenter.setName("Test Service Center");

        serviceCenterServiceType = new ServiceCenterServiceType();
        serviceCenterServiceType.setId(1L);
        serviceCenterServiceType.setServiceCenter(serviceCenter);

        serviceRequestDTO = new ServiceRequestDTO();
        serviceRequestDTO.setUsername("testuser");
        serviceRequestDTO.setServiceId(1L);
        serviceRequestDTO.setDeliveryBoyId(1L);

        serviceRequest = new ServiceRequest();
        serviceRequest.setId(1L);
        serviceRequest.setUser(appUser);
        serviceRequest.setService(serviceCenterServiceType);
        serviceRequest.setServiceCenter(serviceCenter);
        serviceRequest.setDeliveryBoy(deliveryBoy);
        serviceRequest.setStatus(RequestStatus.PENDING);
    }

    @Test
    void testCreateRequest() {
        id = 1L;
        when(appUserDaoService.findByUsername("testuser")).thenReturn(appUser);
        when(serviceCenterServiceTypeDaoService.findById(1L)).thenReturn(serviceCenterServiceType);
        when(deliveryBoyDaoService.findById(1L)).thenReturn(deliveryBoy);
        when(serviceRequestDaoService.save(any(ServiceRequest.class))).thenReturn(serviceRequest);

        serviceRequestService.createRequest(serviceRequestDTO);

        verify(serviceRequestDaoService, times(1)).save(any(ServiceRequest.class));
    }

    @Test
    void testCreateRequest_UserNotFound() {
        doThrow(ElementNotFoundException.class).when(appUserDaoService).findByUsername("testuser");

        assertThrows(ElementNotFoundException.class, () -> {
            serviceRequestService.createRequest(serviceRequestDTO);
        });
    }

    @Test
    void testCreateRequest_ServiceNotFound() {
        id = 1L;
        when(appUserDaoService.findByUsername("testuser")).thenReturn(appUser);
        doThrow(ElementNotFoundException.class).when(serviceCenterServiceTypeDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceRequestService.createRequest(serviceRequestDTO);
        });
    }

    @Test
    void testCreateRequest_DeliveryBoyNotFound() {
        when(appUserDaoService.findByUsername("testuser")).thenReturn(appUser);
        when(serviceCenterServiceTypeDaoService.findById(1L)).thenReturn(serviceCenterServiceType);
        doThrow(ElementNotFoundException.class)
                .when(deliveryBoyDaoService)
                .findById(serviceRequestDTO.getDeliveryBoyId());

        assertThrows(ElementNotFoundException.class, () -> {
            serviceRequestService.createRequest(serviceRequestDTO);
        });
    }

    @Test
    void testGetRequestsByUser() {
        when(serviceRequestDaoService.findByUserUsername("testuser")).thenReturn(List.of(serviceRequest));

        List<ServiceRequestDTO> result = serviceRequestService.getRequestsByUser("testuser");

        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void testUpdateRequestStatus() {
        when(serviceRequestDaoService.findById(1L)).thenReturn(serviceRequest);
        when(deliveryBoyDaoService.findById(1L)).thenReturn(deliveryBoy);
        when(serviceRequestDaoService.save(any(ServiceRequest.class))).thenReturn(serviceRequest);

        ServiceRequestDTO result = serviceRequestService.updateRequestStatus(1L, RequestStatus.ACCEPTED, 1L);

        assertEquals(RequestStatus.ACCEPTED, result.getStatus());
        verify(serviceRequestDaoService, times(1)).save(any(ServiceRequest.class));
    }

    @Test
    void testUpdateRequestStatus_RequestNotFound() {
        id = 1L;
        doThrow(ElementNotFoundException.class).when(serviceRequestDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceRequestService.updateRequestStatus(id, RequestStatus.ACCEPTED, id);
        });
    }

    @Test
    void testUpdateRequestStatus_DeliveryBoyNotFound() {
        id = 1L;
        when(serviceRequestDaoService.findById(1L)).thenReturn(serviceRequest);
        doThrow(ElementNotFoundException.class).when(deliveryBoyDaoService).findById(id);

        assertThrows(ElementNotFoundException.class, () -> {
            serviceRequestService.updateRequestStatus(1L, RequestStatus.ACCEPTED, 1L);
        });
    }

    @Test
    void testGetAllRequests() {
        when(serviceRequestDaoService.findAll()).thenReturn(List.of(serviceRequest));

        List<ServiceRequestDTO> result = serviceRequestService.getAllRequests();

        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

}