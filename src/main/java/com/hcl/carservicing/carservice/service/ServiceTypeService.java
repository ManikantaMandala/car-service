package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceType;

public interface ServiceTypeService {
    ServiceType createServiceType(ServiceTypeDTO serviceTypeDTO);
    ServiceType updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO);
    ServiceType getServiceTypeById(Long id);
    List<ServiceType> getAllServiceTypes();
	ServiceTypeDTO findById(Long serviceTypeId);
}

