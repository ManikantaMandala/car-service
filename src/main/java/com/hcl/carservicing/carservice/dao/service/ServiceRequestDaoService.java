package com.hcl.carservicing.carservice.dao.service;

import com.hcl.carservicing.carservice.model.ServiceRequest;

import java.util.List;

public interface ServiceRequestDaoService {
    ServiceRequest save(ServiceRequest request);
    List<ServiceRequest> findByUserUsername(String username);
    ServiceRequest findById(Long requestId);
    List<ServiceRequest> findAll();
}
