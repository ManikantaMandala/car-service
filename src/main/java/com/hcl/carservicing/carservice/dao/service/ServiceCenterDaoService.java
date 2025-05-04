package com.hcl.carservicing.carservice.dao.service;

import com.hcl.carservicing.carservice.model.ServiceCenter;

import java.util.List;

public interface ServiceCenterDaoService {
    ServiceCenter findById(Long id);
    List<ServiceCenter> findAll();
    List<ServiceCenter> findByAvailable(Boolean available);
}
