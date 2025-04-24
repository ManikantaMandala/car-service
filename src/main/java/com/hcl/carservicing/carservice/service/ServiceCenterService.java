package com.hcl.carservicing.carservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;

public interface ServiceCenterService {
    ServiceCenterDTO createServiceCenter(ServiceCenterDTO serviceCenterDTO);
    ServiceCenterDTO updateServiceCenter(Long id, ServiceCenterDTO serviceCenterDTO);
    List<ServiceCenterDTO> getAllServiceCenters();
    List<ServiceCenterDTO> getAvailableServiceCenters(Boolean available);
    ServiceCenterDTO getServiceCenterById(Long id);
    ResponseEntity<ServiceCenterDTO> updateStatusOfServiceCenter(Long id, Boolean status);
    ServiceCenterDTO findById(Long serviceCenterId);
}