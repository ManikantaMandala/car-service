package com.hcl.carservicing.carservice.repository;

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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ServicingRequestRepositoryTest {

    private AppUser appUser;
    private ServiceCenterServiceType serviceCenterServiceType;

    @Autowired
    ServicingRequestRepository servicingRequestRepository;

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
        appUser.setGender("Male");
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
        ServicingRequest servicingRequest = new ServicingRequest();

        servicingRequest.setUser(appUser);
        servicingRequest.setStartDate(LocalDate.now().plusDays(1));
        servicingRequest.setEndDate(LocalDate.now().plusDays(2));
        servicingRequest.setStatus(RequestStatus.PENDING);
        servicingRequest.setService(serviceCenterServiceType);
        servicingRequest.setServiceCenter(serviceCenter);

        servicingRequest = servicingRequestRepository.save(servicingRequest);
    }

    @Test
    void findByUserUsernameReturnsServicingRequests() {
        List<ServicingRequest> servicingRequests = servicingRequestRepository.findByUserUsername(appUser.getUsername());

        assertThat(servicingRequests).isNotEmpty();
        assertThat(servicingRequests.get(0).getUser().getUsername()).isEqualTo(appUser.getUsername());
    }

    @Test
    void findByUserUsernameReturnsEmpty() {
        List<ServicingRequest> servicingRequests = servicingRequestRepository.findByUserUsername("nonexistentuser");

        assertThat(servicingRequests).isEmpty();
    }

    @Test
    void findByUserUsernameReturnsCorrectServicingRequest() {
        List<ServicingRequest> servicingRequests = servicingRequestRepository.findByUserUsername(appUser.getUsername());

        assertThat(servicingRequests).hasSize(1);
        ServicingRequest request = servicingRequests.get(0);
        assertThat(request.getUser().getUsername()).isEqualTo(appUser.getUsername());
        assertThat(request.getService()).isEqualTo(serviceCenterServiceType);
        assertThat(request.getStatus()).isEqualTo(RequestStatus.PENDING);
    }
}
