package com.hcl.carservicing.carservice.repository;

import com.hcl.carservicing.carservice.model.ServiceCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ServiceCenterRepositoryTest {

    String name;
    String address;
    Double rating;
    Boolean available;
    ServiceCenter serviceCenter;

    @Autowired
    ServiceCenterRepository serviceCenterRepository;

    @BeforeEach
    void setUp() {
        name = "Test Service Center";
        address = "123 Test St, Test City, 12345";
        rating = 4.5;
        available = true;

        serviceCenter = new ServiceCenter();
        serviceCenter.setName(name);
        serviceCenter.setAddress(address);
        serviceCenter.setRating(rating);
        serviceCenter.setAvailable(available);

        serviceCenterRepository.save(serviceCenter);
    }

    @Test
    void findByAvailableReturnsServiceCenters() {
        List<ServiceCenter> serviceCenters = serviceCenterRepository.findByAvailable(available);

        assertThat(serviceCenters).isNotEmpty();
        assertThat(serviceCenters.get(0).getAvailable()).isEqualTo(available);
    }

    @Test
    void findByAvailableReturnsEmpty() {
        List<ServiceCenter> serviceCenters = serviceCenterRepository.findByAvailable(false);

        assertThat(serviceCenters).isEmpty();
    }

    @Test
    void findByAvailableReturnsMultipleServiceCenters() {
        // Save additional service centers with the same availability status
        ServiceCenter serviceCenter2 = new ServiceCenter();
        serviceCenter2.setName("Another Service Center");
        serviceCenter2.setAddress("456 Another St, Test City, 12345");
        serviceCenter2.setRating(3.8);
        serviceCenter2.setAvailable(available);

        serviceCenterRepository.save(serviceCenter2);

        List<ServiceCenter> serviceCenters = serviceCenterRepository.findByAvailable(available);

        assertThat(serviceCenters).hasSize(2);
    }
}
