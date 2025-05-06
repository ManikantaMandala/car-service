package com.hcl.carservicing.carservice.repository;

import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ServiceCenterServiceTypeRepositoryTest {

    String serviceTypeName;
    String serviceTypeDescription;
    ServiceCenter serviceCenter;
    ServiceCenterServiceType serviceCenterServiceType;

    @Autowired
    ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository;

    @Autowired
    ServiceCenterRepository serviceCenterRepository;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @BeforeEach
    void setUp() {
        // Setup service center
        serviceCenter = new ServiceCenter();
        serviceCenter.setName("Test Service Center");
        serviceCenter.setAddress("123 Test St, Test City, 12345");
        serviceCenter.setRating(4.5);
        serviceCenter.setAvailable(true);
        serviceCenter = serviceCenterRepository.save(serviceCenter);

        // Setup service type
        serviceTypeName = "Test Service Type";
        serviceTypeDescription = "test service type description";

        ServiceType serviceType = new ServiceType();

        serviceType.setServiceName(serviceTypeName);
        serviceType.setDescription(serviceTypeDescription);

        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);

        // Create service center service type
        serviceCenterServiceType = new ServiceCenterServiceType();
        serviceCenterServiceType.setServiceCenter(serviceCenter);
        serviceCenterServiceType.setServiceType(savedServiceType);
        serviceCenterServiceType.setCost(100.00);

        // Save service center service type
        serviceCenterServiceTypeRepository.save(serviceCenterServiceType);
    }

    @Test
    void findByServiceCenterIdReturnsServiceCenterServiceTypes() {
        List<ServiceCenterServiceType> serviceCenterServiceTypes =
                serviceCenterServiceTypeRepository.findByServiceCenterId(serviceCenter.getId());

        assertThat(serviceCenterServiceTypes).isNotEmpty();
        assertThat(serviceCenterServiceTypes.get(0).getServiceCenter().getId()).isEqualTo(serviceCenter.getId());
    }

    @Test
    void findByServiceCenterIdReturnsEmpty() {
        List<ServiceCenterServiceType> serviceCenterServiceTypes =
                serviceCenterServiceTypeRepository.findByServiceCenterId(999L); // Non-existent ID

        assertThat(serviceCenterServiceTypes).isEmpty();
    }

    @Test
    void findByServiceTypeIdReturnsServiceCenterServiceTypes() {
        List<ServiceCenterServiceType> serviceCenterServiceTypes =
                serviceCenterServiceTypeRepository.findByServiceTypeId(serviceCenterServiceType
                        .getServiceType().getId());

        assertThat(serviceCenterServiceTypes).isNotEmpty();
        assertThat(serviceCenterServiceTypes.get(0).getServiceType().getServiceName()).isEqualTo(serviceTypeName);
    }

    @Test
    void findByServiceTypeIdReturnsEmpty() {
        List<ServiceCenterServiceType> serviceCenterServiceTypes = serviceCenterServiceTypeRepository.findByServiceTypeId(999L); // Non-existent service type ID

        assertThat(serviceCenterServiceTypes).isEmpty();
    }

}
