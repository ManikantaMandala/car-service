package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.dao.service.ServiceTypeDaoService;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeDaoServiceImpl implements ServiceTypeDaoService {
    private final static Logger logger = LoggerFactory.getLogger(ServiceTypeDaoServiceImpl.class);

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeDaoServiceImpl(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceType findById(Long id){
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(id);

        if (serviceTypeOptional.isEmpty()) {
            logger.error("Service type not found with ID: {}", id);
            throw new ElementNotFoundException("ServiceType not found: " + id);
        }

        return serviceTypeOptional.get();
    }

    @Override
    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public ServiceType save(ServiceType existing) {
        return serviceTypeRepository.save(existing);
    }
}
