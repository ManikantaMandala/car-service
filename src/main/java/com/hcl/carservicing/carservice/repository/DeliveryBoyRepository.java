package com.hcl.carservicing.carservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.carservicing.carservice.model.DeliveryBoy;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Long> {
    List<DeliveryBoy> findByServiceCenterId(Long serviceCenterId);
    List<DeliveryBoy> findByServicingRequestIsNull();
	Optional<DeliveryBoy> findByContactNumber(String contactNumber);
}

