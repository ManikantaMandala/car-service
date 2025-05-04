package com.hcl.carservicing.carservice.repository;

import com.hcl.carservicing.carservice.enums.Gender;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.enums.UserRole;
import com.hcl.carservicing.carservice.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ServiceRequestRepositoryTest {

    private AppUser appUser;
    private ServiceCenterServiceType serviceCenterServiceType;

    @Autowired
    ServiceRequestRepository serviceRequestRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository;

    @Autowired
    ServiceCenterRepository serviceCenterRepository;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @BeforeEach
    void setUp() {
        // Setup AppUser
        appUser = new AppUser();

        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setAge(28);
        appUser.setGender(Gender.MALE);
        appUser.setContactNumber("1234567890");
        appUser.setUsername("john_doe_123");
        appUser.setPassword("Password@123");
        appUser.setRole(UserRole.USER);
        appUser.setCreatedAt(LocalDateTime.now());

        appUser = appUserRepository.save(appUser);

        // Setup ServiceCenter
        ServiceCenter serviceCenter = new ServiceCenter();

        serviceCenter.setName("Test Service Center");
        serviceCenter.setAddress("123 Test St, Test City, 12345");
        serviceCenter.setRating(4.5);
        serviceCenter.setAvailable(true);

        serviceCenter = serviceCenterRepository.save(serviceCenter);

        // Setup ServiceType
        String serviceTypeName = "Test Service Type";
        String serviceTypeDescription = "test service type description";

        ServiceType serviceType = new ServiceType();

        serviceType.setServiceName(serviceTypeName);
        serviceType.setDescription(serviceTypeDescription);

        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);

        // Setup ServiceCenterServiceType
        serviceCenterServiceType = new ServiceCenterServiceType();

        serviceCenterServiceType.setServiceCenter(serviceCenter);
        serviceCenterServiceType.setServiceType(savedServiceType);
        serviceCenterServiceType.setCost(100.00);

        serviceCenterServiceType = serviceCenterServiceTypeRepository.save(serviceCenterServiceType);

        // Setup ServicingRequest
        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setUser(appUser);
        serviceRequest.setStartDate(LocalDate.now().plusDays(1));
        serviceRequest.setEndDate(LocalDate.now().plusDays(2));
        serviceRequest.setStatus(RequestStatus.PENDING);
        serviceRequest.setService(serviceCenterServiceType);
        serviceRequest.setServiceCenter(serviceCenter);

        serviceRequest = serviceRequestRepository.save(serviceRequest);
    }

    @Test
    void findByUserUsernameReturnsServicingRequests() {
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findByUserUsername(appUser.getUsername());

        assertThat(serviceRequests).isNotEmpty();
        assertThat(serviceRequests.get(0).getUser().getUsername()).isEqualTo(appUser.getUsername());
    }

    @Test
    void findByUserUsernameReturnsEmpty() {
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findByUserUsername("nonexistentuser");

        assertThat(serviceRequests).isEmpty();
    }

    @Test
    void findByUserUsernameReturnsCorrectServicingRequest() {
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findByUserUsername(appUser.getUsername());

        assertThat(serviceRequests).hasSize(1);
        ServiceRequest request = serviceRequests.get(0);
        assertThat(request.getUser().getUsername()).isEqualTo(appUser.getUsername());
        assertThat(request.getService()).isEqualTo(serviceCenterServiceType);
        assertThat(request.getStatus()).isEqualTo(RequestStatus.PENDING);
    }
}
