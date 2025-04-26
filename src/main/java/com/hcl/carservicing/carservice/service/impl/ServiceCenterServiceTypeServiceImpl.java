package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import com.hcl.carservicing.carservice.repository.ServiceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceCenterServiceTypeRepository;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
import com.hcl.carservicing.carservice.service.ServiceCenterServiceTypeService;
import com.hcl.carservicing.carservice.service.ServiceTypeService;

@Service
public class ServiceCenterServiceTypeServiceImpl implements ServiceCenterServiceTypeService {

	private static final Logger logger = LoggerFactory.getLogger(ServiceCenterServiceTypeServiceImpl.class);

    private final ServiceCenterServiceTypeRepository repository;
    private final ServiceCenterRepository serviceCenterRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceCenterServiceTypeServiceImpl(ServiceCenterServiceTypeRepository repository,
                                               ServiceCenterRepository serviceCenterRepository,
                                               ServiceTypeRepository serviceTypeRepository
    ) {
        this.repository = repository;
        this.serviceCenterRepository = serviceCenterRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    @Transactional
    public void addServiceTypeToCenter(ServiceCenterServiceTypeDTO scstDTO) {
    	logger.info("Adding service type to service center with ID: {}", scstDTO.getServiceCenterId());

        ServiceCenterServiceType scst = convertToEntity(scstDTO);
        ServiceCenterServiceType savedScst = repository.save(scst);

        logger.info("Service type added successfully with ID: {}", savedScst.getId());
    }

    @Override
    @Transactional
    public void updateServiceCenterServiceType(Long id, ServiceCenterServiceTypeDTO scstDTO) {
    	logger.info("Updating service center service type with ID: {}", id);

    	ServiceCenterServiceType existing = repository.findById(id)
    			.orElseThrow(() -> {
    				logger.error("ServiceCenterServiceType not found with ID: {}", id);
    				return new ElementNotFoundException("ServiceCenterServiceType not found: " + id);
                });

        Optional<ServiceCenter> serviceCenterOptional = serviceCenterRepository.findById(scstDTO.getServiceCenterId());
        if (serviceCenterOptional.isEmpty()) {
            logger.error("Service center not found with ID: {}", scstDTO.getServiceCenterId());
            throw new IllegalArgumentException("ServiceCenter not found: " + id);
        }
        existing.setServiceCenter(serviceCenterOptional.get());

        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(scstDTO.getServiceTypeId());
        if (serviceTypeOptional.isEmpty()) {
            logger.error("Service type not found with ID: {}", id);
            throw new ElementNotFoundException("ServiceType not found: " + id);
        }
        existing.setServiceType(serviceTypeOptional.get());

        existing.setCost(scstDTO.getCost());

        ServiceCenterServiceType savedScst = repository.save(existing);
        logger.info("Service center service type updated successfully with ID: {}", savedScst.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCenterServiceTypeDTO> getByServiceCenter(Long serviceCenterId) {
    	logger.info("Fetching service types for service center ID: {}", serviceCenterId);
        List<ServiceCenterServiceType> scstList = repository.findByServiceCenterId(serviceCenterId);

        logger.info("Fetched {} service types for service center ID: {}", scstList.size(), serviceCenterId);
        return scstList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCenterServiceTypeDTO> getByServiceType(Long serviceTypeId) {
    	logger.info("Fetching service centers for service type ID: {}", serviceTypeId);
        List<ServiceCenterServiceType> scstList = repository.findByServiceTypeId(serviceTypeId);

        logger.info("Fetched {} service centers for service type ID: {}", scstList.size(), serviceTypeId);
        return scstList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    ServiceCenterServiceType convertToEntity(ServiceCenterServiceTypeDTO scstDTO) {
        ServiceCenterServiceType scst = new ServiceCenterServiceType();

        scst.setId(scstDTO.getId());

        Optional<ServiceCenter> serviceCenterOptional = serviceCenterRepository.findById(scstDTO.getServiceCenterId());
        if (serviceCenterOptional.isEmpty()) {
            logger.error("Service center not found with ID: {}", scstDTO.getServiceCenterId());
            throw new IllegalArgumentException("ServiceCenter not found: " + scstDTO.getServiceCenterId());
        }
        scst.setServiceCenter(serviceCenterOptional.get());

        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(scstDTO.getServiceTypeId());
        if (serviceTypeOptional.isEmpty()) {
            logger.error("Service type not found with ID: {}", scstDTO.getServiceTypeId());
            throw new ElementNotFoundException("ServiceType not found: " + scstDTO.getServiceTypeId());
        }
        scst.setServiceType(serviceTypeOptional.get());

        scst.setCost(scstDTO.getCost());

        return scst;
    }

    // TODO: use loggers
     ServiceCenterServiceTypeDTO convertToDTO(ServiceCenterServiceType scst) {
        ServiceCenterServiceTypeDTO scstDTO = new ServiceCenterServiceTypeDTO();

        scstDTO.setId(scst.getId());
        scstDTO.setServiceCenterId(scst.getServiceCenter().getId());
        scstDTO.setServiceTypeId(scst.getServiceType().getId());
        scstDTO.setCost(scst.getCost());

        return scstDTO;
    }

    // TODO: use loggers
    ServiceCenter convertToEntity(ServiceCenterDTO serviceCenterDTO) {
        ServiceCenter serviceCenter = new ServiceCenter();

        serviceCenter.setId(serviceCenterDTO.getId());
        serviceCenter.setName(serviceCenterDTO.getName());
        serviceCenter.setAddress(serviceCenterDTO.getAddress());
        serviceCenter.setRating(serviceCenterDTO.getRating());
        serviceCenter.setAvailable(serviceCenterDTO.getAvailable());

        return serviceCenter;
    }

    // TODO: use loggers
    ServiceType convertToEntity(ServiceTypeDTO serviceTypeDTO) {
        ServiceType serviceType = new ServiceType();

        serviceType.setId(serviceTypeDTO.getId());
        serviceType.setServiceName(serviceTypeDTO.getServiceName());

        return serviceType;
    }
}