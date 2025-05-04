package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.dao.service.ServiceCenterServiceTypeDaoService;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.repository.ServiceCenterServiceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCenterServiceTypeDaoServiceImpl implements ServiceCenterServiceTypeDaoService {

    private final Logger logger = LoggerFactory.getLogger(ServiceCenterServiceTypeDaoServiceImpl.class);

    private final ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository;

    public ServiceCenterServiceTypeDaoServiceImpl(ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository) {
        this.serviceCenterServiceTypeRepository = serviceCenterServiceTypeRepository;
    }

    @Override
    public ServiceCenterServiceType findById(Long id) {
        Optional<ServiceCenterServiceType> serviceCenterServiceTypeOptional = serviceCenterServiceTypeRepository.findById(id);
        if (serviceCenterServiceTypeOptional.isEmpty()) {
            logger.error("ServiceCenterServiceType not found with ID: {}", id);
            throw new ElementNotFoundException("ServiceCenterServiceType not found: " + id);
        }

        return serviceCenterServiceTypeOptional.get();
    }

    @Override
    public ServiceCenterServiceType save(ServiceCenterServiceType serviceCenterServiceType) {
        return serviceCenterServiceTypeRepository.save(serviceCenterServiceType);
    }

    @Override
    public List<ServiceCenterServiceType> findByServiceCenterId(Long serviceCenterId) {
        return serviceCenterServiceTypeRepository.findByServiceCenterId(serviceCenterId);
    }

    @Override
    public List<ServiceCenterServiceType> findByServiceTypeId(Long serviceTypeId) {
        return serviceCenterServiceTypeRepository.findByServiceTypeId(serviceTypeId);
    }
}
