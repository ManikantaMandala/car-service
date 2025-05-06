package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceRequest;
import com.hcl.carservicing.carservice.repository.ServiceRequestRepository;
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

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class ServiceRequestDaoServiceImplTest {

    @Mock
    private ServiceRequestRepository serviceRequestRepository;

    @InjectMocks
    private ServiceRequestDaoServiceImpl serviceRequestDaoService;

    @Test
    public void save_validServiceRequest_returnsSavedServiceRequest() {
        ServiceRequest requestToSave = new ServiceRequest();
        requestToSave.setStartDate(LocalDate.now());
        requestToSave.setEndDate(LocalDate.now().plusDays(1));
        requestToSave.setStatus(RequestStatus.PENDING);

        AppUser user = new AppUser();
        user.setId(1L);
        requestToSave.setUser(user);

        ServiceCenterServiceType service = new ServiceCenterServiceType();
        service.setId(1L);
        requestToSave.setService(service);

        ServiceRequest savedRequest = new ServiceRequest();
        savedRequest.setId(1L);
        savedRequest.setUser(user);
        savedRequest.setStartDate(LocalDate.now());
        savedRequest.setEndDate(LocalDate.now().plusDays(1));
        savedRequest.setStatus(RequestStatus.PENDING);
        savedRequest.setUser(user);
        savedRequest.setService(service);

        when(serviceRequestRepository.save(requestToSave)).thenReturn(savedRequest);

        ServiceRequest result = serviceRequestDaoService.save(requestToSave);

        assertEquals(savedRequest, result);
        verify(serviceRequestRepository).save(requestToSave);
    }

    @Test
    public void findByUserUsername_userHasRequests_returnsListOfServiceRequests() {
        String username = "testuser";
        List<ServiceRequest> expectedRequests = new ArrayList<>();
        ServiceRequest request1 = new ServiceRequest();
        request1.setStartDate(LocalDate.now());
        request1.setEndDate(LocalDate.now().plusDays(1));
        request1.setStatus(RequestStatus.PENDING);
        AppUser user1 = new AppUser();
        user1.setId(1L);
        request1.setUser(user1);

        ServiceCenterServiceType service1 = new ServiceCenterServiceType();
        service1.setId(1L);
        request1.setService(service1);

        ServiceRequest request2 = new ServiceRequest();
        request2.setStartDate(LocalDate.now());
        request2.setEndDate(LocalDate.now().plusDays(1));
        request2.setStatus(RequestStatus.PENDING);

        AppUser user2 = new AppUser();
        user2.setId(2L);
        request2.setUser(user2);

        ServiceCenterServiceType service2 = new ServiceCenterServiceType();
        service2.setId(2L);
        request2.setService(service2);

        expectedRequests.add(request1);
        expectedRequests.add(request2);

        when(serviceRequestRepository.findByUserUsername(username)).thenReturn(expectedRequests);

        List<ServiceRequest> actualRequests = serviceRequestDaoService.findByUserUsername(username);

        assertEquals(expectedRequests, actualRequests);
        verify(serviceRequestRepository).findByUserUsername(username);
    }

    @Test
    public void findByUserUsername_userHasNoRequests_returnsEmptyList() {
        String username = "anotheruser";
        List<ServiceRequest> expectedRequests = new ArrayList<>();

        when(serviceRequestRepository.findByUserUsername(username)).thenReturn(expectedRequests);

        List<ServiceRequest> actualRequests = serviceRequestDaoService.findByUserUsername(username);

        assertEquals(expectedRequests, actualRequests);
        verify(serviceRequestRepository).findByUserUsername(username);
    }

    @Test
    public void findById_serviceRequestFound_returnsServiceRequest() {
        Long requestId = 1L;
        ServiceRequest expectedRequest = new ServiceRequest();
        expectedRequest.setId(requestId);
        expectedRequest.setStartDate(LocalDate.now());
        expectedRequest.setEndDate(LocalDate.now().plusDays(1));
        expectedRequest.setStatus(RequestStatus.PENDING);
        AppUser user = new AppUser();
        user.setId(1L);
        expectedRequest.setUser(user);

        ServiceCenterServiceType service = new ServiceCenterServiceType();
        service.setId(1L);
        expectedRequest.setService(service);

        Optional<ServiceRequest> optional = Optional.of(expectedRequest);

        when(serviceRequestRepository.findById(requestId)).thenReturn(optional);

        ServiceRequest actualRequest = serviceRequestDaoService.findById(requestId);

        assertEquals(expectedRequest, actualRequest);
        verify(serviceRequestRepository).findById(requestId);
    }

    @Test
    public void findById_serviceRequestNotFound_throwsElementNotFoundException() {
        Long requestId = 2L;
        Optional<ServiceRequest> optional = Optional.empty();

        when(serviceRequestRepository.findById(requestId)).thenReturn(optional);

        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> { // Changed Exception type
            serviceRequestDaoService.findById(requestId);
        });
        assertEquals("Request not found: " + requestId, exception.getMessage());
        verify(serviceRequestRepository).findById(requestId);
    }

    @Test
    public void findAll_serviceRequestsFound_returnsListOfServiceRequests() {
        List<ServiceRequest> expectedRequests = new ArrayList<>();
        ServiceRequest request1 = new ServiceRequest();
        request1.setId(1L);
        request1.setStartDate(LocalDate.now());
        request1.setEndDate(LocalDate.now().plusDays(1));
        request1.setStatus(RequestStatus.PENDING);
        AppUser user1 = new AppUser();
        user1.setId(1L);
        request1.setUser(user1);

        ServiceCenterServiceType service1 = new ServiceCenterServiceType();
        service1.setId(1L);
        request1.setService(service1);

        ServiceRequest request2 = new ServiceRequest();
        request2.setId(2L);
        request2.setStartDate(LocalDate.now());
        request2.setEndDate(LocalDate.now().plusDays(1));
        request2.setStatus(RequestStatus.PENDING);

        AppUser user2 = new AppUser();
        user2.setId(2L);
        request2.setUser(user2);

        ServiceCenterServiceType service2 = new ServiceCenterServiceType();
        service2.setId(2L);
        request2.setService(service2);

        expectedRequests.add(request1);
        expectedRequests.add(request2);

        when(serviceRequestRepository.findAll()).thenReturn(expectedRequests);

        List<ServiceRequest> actualRequests = serviceRequestDaoService.findAll();

        assertEquals(expectedRequests, actualRequests);
        verify(serviceRequestRepository).findAll();
    }

    @Test
    public void findAll_noServiceRequestsFound_returnsEmptyList() {
        List<ServiceRequest> expectedRequests = new ArrayList<>();

        when(serviceRequestRepository.findAll()).thenReturn(expectedRequests);

        List<ServiceRequest> actualRequests = serviceRequestDaoService.findAll();

        assertEquals(expectedRequests, actualRequests);
        verify(serviceRequestRepository).findAll();
    }
}

