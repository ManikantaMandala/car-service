package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.dao.service.ServiceRequestDaoService;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.ServiceRequest;
import com.hcl.carservicing.carservice.repository.ServiceRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestDaoServiceImpl implements ServiceRequestDaoService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestDaoServiceImpl.class);

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestDaoServiceImpl(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @Override
    public ServiceRequest save(ServiceRequest serviceRequest) {
        return serviceRequestRepository.save(serviceRequest);
    }

    @Override
    public List<ServiceRequest> findByUserUsername(String username) {
        return serviceRequestRepository.findByUserUsername(username);
    }

    @Override
    public ServiceRequest findById(Long requestId) {
        Optional<ServiceRequest> serviceRequestOptional = serviceRequestRepository.findById(requestId);
        if (serviceRequestOptional.isEmpty()) {
            logger.error("Request not found: {}", requestId);
            throw new ElementNotFoundException("Request not found: " + requestId);
        }

        return serviceRequestOptional.get();
    }

    @Override
    public List<ServiceRequest> findAll() {
        return serviceRequestRepository.findAll();
    }
}
