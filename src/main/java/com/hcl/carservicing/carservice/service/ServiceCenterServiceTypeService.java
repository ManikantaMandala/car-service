package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;

public interface ServiceCenterServiceTypeService {
    ServiceCenterServiceTypeDTO addServiceTypeToCenter(ServiceCenterServiceTypeDTO scstDTO);
    ServiceCenterServiceTypeDTO updateServiceCenterServiceType(Long id, ServiceCenterServiceTypeDTO scstDTO);
    List<ServiceCenterServiceTypeDTO> getByServiceCenter(Long serviceCenterId);
    List<ServiceCenterServiceTypeDTO> getByServiceType(Long serviceTypeId);
}