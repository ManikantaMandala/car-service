package com.hcl.carservicing.carservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.carservicing.carservice.model.ServiceCenter;

@Repository
public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Long> {
    // Custom queries can be added as needed, e.g., find by availability or rating
    List<ServiceCenter> findByAvailable(Boolean available);
}

