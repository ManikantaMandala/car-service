package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;

public interface ServiceTypeService {
//    ServiceTypeDTO createServiceType(ServiceTypeDTO serviceTypeDTO);
    void createServiceType(ServiceTypeDTO serviceTypeDTO);
//    ServiceTypeDTO updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO);
    void updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO);
    ServiceTypeDTO getServiceTypeById(Long id);
    List<ServiceTypeDTO> getAllServiceTypes();
	ServiceTypeDTO findById(Long serviceTypeId);
}

