package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.dao.service.ServiceCenterDaoService;
import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.mapper.ServiceCenterMapper;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import com.hcl.carservicing.carservice.service.impl.ServiceCenterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCenterDaoServiceImpl implements ServiceCenterDaoService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceCenterServiceImpl.class);
    private ServiceCenterRepository serviceCenterRepository;

    @Override
    public ServiceCenter findById(Long id) {
        logger.info("Fetching service center with ID: {}", id);
        Optional<ServiceCenter> serviceCenter = serviceCenterRepository.findById(id);
        if (serviceCenter.isEmpty()) {
            logger.error("Service center not found with ID: {}", id);
            throw new ElementNotFoundException("Service center not found with ID: " + id);
        }

        logger.info("Returning service center with ID: {}", id);
        return serviceCenter.get();
    }

    @Override
    public List<ServiceCenter> findAll() {
        return serviceCenterRepository.findAll();
    }

    @Override
    public List<ServiceCenter> findByAvailable(Boolean available) {
        return serviceCenterRepository.findByAvailable(available);
    }

    @Override
    public ServiceCenter save(ServiceCenter serviceCenter) {
        return serviceCenterRepository.save(serviceCenter);
    }
}
