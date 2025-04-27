package com.hcl.carservicing.carservice.repository;

import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeliveryBoyRepositoryTest {

    String name;
    String contactNumber;
    Long serviceCenterId;
    Boolean available;
    DeliveryBoy deliveryBoy;
    ServiceCenter serviceCenter;

    @Autowired
    DeliveryBoyRepository deliveryBoyRepository;

    @Autowired
    ServiceCenterRepository serviceCenterRepository;

    @BeforeEach
    void setUp() {
        // Set up initial data
        name = "Test Delivery Boy";
        contactNumber = "9876543210";
        serviceCenterId = 1L;  // Assuming this Service Center exists
        available = true;

        // Create and save ServiceCenter
        serviceCenter = new ServiceCenter();
        serviceCenter.setName("Test Service Center");
        serviceCenter.setAddress("123 Test St.");
        serviceCenter.setRating(4.5);
        serviceCenter.setAvailable(true);
        serviceCenterRepository.save(serviceCenter);

        // Create and save DeliveryBoy
        deliveryBoy = new DeliveryBoy();
        deliveryBoy.setName(name);
        deliveryBoy.setContactNumber(contactNumber);
        deliveryBoy.setServiceCenter(serviceCenter);
        deliveryBoy.setAvailable(available);

        deliveryBoyRepository.save(deliveryBoy);
    }

    @Test
    void findByServiceCenterIdReturnsDeliveryBoys() {
        List<DeliveryBoy> deliveryBoys = deliveryBoyRepository.findByServiceCenterId(serviceCenterId);

        assertThat(deliveryBoys).isNotEmpty();
        assertThat(deliveryBoys.get(0).getServiceCenter().getId()).isEqualTo(serviceCenterId);
    }

    @Test
    void findByServiceCenterIdReturnsEmptyWhenNoMatchingServiceCenter() {
        List<DeliveryBoy> deliveryBoys = deliveryBoyRepository.findByServiceCenterId(999L);

        assertThat(deliveryBoys).isEmpty();
    }

    @Test
    void findByContactNumberReturnsDeliveryBoy() {
        Optional<DeliveryBoy> deliveryBoyOptional = deliveryBoyRepository.findByContactNumber(contactNumber);

        assertThat(deliveryBoyOptional).isPresent();
        assertThat(deliveryBoyOptional.get().getContactNumber()).isEqualTo(contactNumber);
    }

    @Test
    void findByContactNumberReturnsEmptyWhenInvalidContactNumber() {
        Optional<DeliveryBoy> deliveryBoyOptional = deliveryBoyRepository.findByContactNumber("0000000000");

        assertThat(deliveryBoyOptional).isEmpty();
    }
}
