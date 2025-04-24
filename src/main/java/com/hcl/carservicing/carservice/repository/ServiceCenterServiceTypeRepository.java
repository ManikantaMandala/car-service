package com.hcl.carservicing.carservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;

@Repository
public interface ServiceCenterServiceTypeRepository extends JpaRepository<ServiceCenterServiceType, Long> {
    List<ServiceCenterServiceType> findByServiceCenterId(Long serviceCenterId);
    List<ServiceCenterServiceType> findByServiceTypeId(Long serviceTypeId);
}

