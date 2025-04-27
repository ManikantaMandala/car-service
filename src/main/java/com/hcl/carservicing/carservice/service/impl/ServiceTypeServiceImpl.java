package com.hcl.carservicing.carservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceTypeRepository;
import com.hcl.carservicing.carservice.service.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTypeServiceImpl.class);

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    @Transactional
    public void createServiceType(ServiceTypeDTO serviceTypeDTO) {
    	logger.info("Creating service type with name: {}", serviceTypeDTO.getServiceName());
        ServiceType serviceType = new ServiceType();

        serviceType.setServiceName(serviceTypeDTO.getServiceName());
        serviceType.setDescription(serviceTypeDTO.getDescription());
        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);
        logger.info("Service type created successfully with ID: {}", savedServiceType.getId());

    }

    @Override
    @Transactional
    public void updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO) {
    	logger.info("Updating service type with ID: {}", id);
    	ServiceType existing = serviceTypeRepository.findById(id)
    			.orElseThrow(() -> {
    				logger.error("Service type not found with ID: {}", id);
    				return new ElementNotFoundException("ServiceType not found: " + id);
    			});

        existing.setServiceName(serviceTypeDTO.getServiceName());
        existing.setDescription(serviceTypeDTO.getDescription());

        ServiceType savedServiceType = serviceTypeRepository.save(existing);

        logger.info("Service type updated successfully with ID: {}", savedServiceType.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeDTO getServiceTypeById(Long id) {
    	logger.info("Fetching service type with ID: {}", id);
    	return serviceTypeRepository.findById(id)
    			.map(serviceType -> {
    				logger.info("Service type found with ID: {}", id);
    				return convertServiceTypeToDTO(serviceType);
    		}).orElseThrow(() -> {
    			logger.error("Service type not found with ID: {}", id);
    			return new ElementNotFoundException("ServiceType not found: " + id);
    		});
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceTypeDTO> getAllServiceTypes() {
    	logger.info("Fetching all service types");
    	List<ServiceType> serviceTypes = serviceTypeRepository.findAll();

    	logger.info("Fetched {} service types", serviceTypes.size());
    	return serviceTypes.stream().map(this::convertServiceTypeToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeDTO findById(Long id) {
    	logger.info("Finding service type with ID: {}", id);
    	ServiceType serviceType = serviceTypeRepository.findById(id)
    			.orElseThrow(() -> {
    				logger.error("Service type not found with ID: {}", id);
    				return new ElementNotFoundException("ServiceType not found: " + id);
    			});

    	logger.info("Service type found with ID: {}", id);
    	return convertServiceTypeToDTO(serviceType);
    }

    // TODO: use loggers
    private ServiceTypeDTO convertServiceTypeToDTO(ServiceType serviceType) {
        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

        serviceTypeDTO.setId(serviceType.getId());
        serviceTypeDTO.setServiceName(serviceType.getServiceName());

        return serviceTypeDTO;
    }
}
