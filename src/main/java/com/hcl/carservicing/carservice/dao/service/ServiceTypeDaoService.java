package com.hcl.carservicing.carservice.dao.service;

import com.hcl.carservicing.carservice.model.ServiceType;

import java.util.List;

public interface ServiceTypeDaoService {
    ServiceType findById(Long id);
    List<ServiceType> findAll();
    ServiceType save(ServiceType existing);
}
