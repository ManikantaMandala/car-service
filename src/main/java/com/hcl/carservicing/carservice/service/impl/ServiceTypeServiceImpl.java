package com.hcl.carservicing.carservice.service.impl;

import java.util.List;

import com.hcl.carservicing.carservice.dao.service.ServiceTypeDaoService;
import com.hcl.carservicing.carservice.mapper.ServiceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.service.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTypeServiceImpl.class);

    private final ServiceTypeDaoService serviceTypeDaoService;

    public ServiceTypeServiceImpl(ServiceTypeDaoService serviceTypeDaoService) {
        this.serviceTypeDaoService = serviceTypeDaoService;
    }

    @Override
    @Transactional
    public void createServiceType(ServiceTypeDTO serviceTypeDTO) {
    	logger.info("Creating service type with name: {}", serviceTypeDTO.getServiceName());
        ServiceType serviceType = ServiceTypeMapper.convertToEntity(serviceTypeDTO);

        ServiceType savedServiceType = serviceTypeDaoService.save(serviceType);
        logger.info("Service type created successfully with ID: {}", savedServiceType.getId());
    }

    @Override
    @Transactional
    public void updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO) {
    	logger.info("Updating service type with ID: {}", id);
    	ServiceType existing = serviceTypeDaoService.findById(id);

        existing.setServiceName(serviceTypeDTO.getServiceName());
        existing.setDescription(serviceTypeDTO.getDescription());

        ServiceType savedServiceType = serviceTypeDaoService.save(existing);
        logger.info("Service type updated successfully with ID: {}", savedServiceType.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeDTO getServiceTypeById(Long id) {
    	logger.info("Fetching service type with ID: {}", id);
        ServiceType serviceType = serviceTypeDaoService.findById(id);

        return ServiceTypeMapper.convertToDTO(serviceType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceTypeDTO> getAllServiceTypes() {
    	logger.info("Fetching all service types");
    	List<ServiceType> serviceTypes = serviceTypeDaoService.findAll();

    	logger.info("Fetched {} service types", serviceTypes.size());
    	return serviceTypes.stream().map(ServiceTypeMapper::convertToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeDTO findById(Long id) {
    	logger.info("Finding service type with ID: {}", id);
    	ServiceType serviceType = serviceTypeDaoService.findById(id);

    	logger.info("Service type found with ID: {}", id);
    	return ServiceTypeMapper.convertToDTO(serviceType);
    }

}
