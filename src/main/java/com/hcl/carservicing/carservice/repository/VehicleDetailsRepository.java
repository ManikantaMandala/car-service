package com.hcl.carservicing.carservice.repository;

import com.hcl.carservicing.carservice.model.VehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDetailsRepository extends JpaRepository<VehicleDetails, Long> {
}
