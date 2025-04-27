package com.hcl.carservicing.carservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;

public interface ServiceCenterService {
    void createServiceCenter(ServiceCenterDTO serviceCenterDTO);
    void updateServiceCenter(Long id, ServiceCenterDTO serviceCenterDTO);
    List<ServiceCenterDTO> getAllServiceCenters();
    List<ServiceCenterDTO> getAvailableServiceCenters(Boolean available);
    ServiceCenterDTO getServiceCenterById(Long id);
    void updateStatusOfServiceCenter(Long id, Boolean status);
    ServiceCenterDTO findById(Long serviceCenterId);
}