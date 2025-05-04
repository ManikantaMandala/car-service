package com.hcl.carservicing.carservice.dao.service;

import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;

import java.util.List;

public interface ServiceCenterServiceTypeDaoService {
    ServiceCenterServiceType findById(Long id);
    ServiceCenterServiceType save(ServiceCenterServiceType serviceCenterServiceType);
    List<ServiceCenterServiceType> findByServiceCenterId(Long serviceCenterId);
    List<ServiceCenterServiceType> findByServiceTypeId(Long serviceTypeId);
}
