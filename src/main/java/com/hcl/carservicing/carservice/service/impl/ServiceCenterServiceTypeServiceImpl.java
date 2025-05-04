package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hcl.carservicing.carservice.dao.service.ServiceCenterDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceCenterServiceTypeDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceTypeDaoService;
import com.hcl.carservicing.carservice.mapper.ServiceCenterServiceTypeMapper;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import com.hcl.carservicing.carservice.repository.ServiceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceCenterServiceTypeRepository;
import com.hcl.carservicing.carservice.service.ServiceCenterServiceTypeService;

@Service
public class ServiceCenterServiceTypeServiceImpl implements ServiceCenterServiceTypeService {

	private static final Logger logger = LoggerFactory.getLogger(ServiceCenterServiceTypeServiceImpl.class);

    private final ServiceTypeDaoService serviceTypeDaoService;
    private final ServiceCenterServiceTypeDaoService serviceCenterServiceTypeDaoService;
    private final ServiceCenterDaoService serviceCenterDaoService;

    public ServiceCenterServiceTypeServiceImpl(ServiceCenterServiceTypeRepository repository, ServiceCenterServiceTypeDaoService serviceCenterServiceTypeDaoService,
                                               ServiceTypeDaoService serviceTypeDaoService,
                                               ServiceCenterDaoService serviceCenterDaoService ) {
        this.serviceTypeDaoService = serviceTypeDaoService;
        this.serviceCenterDaoService = serviceCenterDaoService;
        this.serviceCenterServiceTypeDaoService = serviceCenterServiceTypeDaoService;
    }

    @Override
    @Transactional
    public void addServiceTypeToCenter(ServiceCenterServiceTypeDTO scstDTO) {
    	logger.info("Adding service type to service center with ID: {}", scstDTO.getServiceCenterId());

        ServiceCenterServiceType serviceCenterServiceType = convertToEntity(scstDTO);
        ServiceCenterServiceType savedServiceCenterServiceType = serviceCenterServiceTypeDaoService.save(serviceCenterServiceType);

        logger.info("Service type added successfully with ID: {}", savedServiceCenterServiceType.getId());
    }

    @Override
    @Transactional
    public void updateServiceCenterServiceType(Long id, ServiceCenterServiceTypeDTO serviceCenterServiceTypeDTO) {
    	logger.info("Updating service center service type with ID: {}", id);

    	ServiceCenterServiceType existing = serviceCenterServiceTypeDaoService.findById(id);

        ServiceCenter serviceCenter = serviceCenterDaoService.findById(serviceCenterServiceTypeDTO.getServiceCenterId());
        ServiceType serviceType = serviceTypeDaoService.findById(serviceCenterServiceTypeDTO.getServiceTypeId());

        existing.setServiceCenter(serviceCenter);
        existing.setServiceType(serviceType);
        existing.setCost(serviceCenterServiceTypeDTO.getCost());

        serviceCenterServiceTypeDaoService.save(existing);
        logger.info("Service center service type updated successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCenterServiceTypeDTO> getByServiceCenter(Long serviceCenterId) {
    	logger.info("Fetching service types for service center ID: {}", serviceCenterId);
        List<ServiceCenterServiceType> scstList = serviceCenterServiceTypeDaoService.findByServiceCenterId(serviceCenterId);

        logger.info("Fetched {} service types for service center ID: {}", scstList.size(), serviceCenterId);
        return scstList.stream()
                .map(ServiceCenterServiceTypeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCenterServiceTypeDTO> getByServiceType(Long serviceTypeId) {
    	logger.info("Fetching service centers for service type ID: {}", serviceTypeId);
        List<ServiceCenterServiceType> scstList = serviceCenterServiceTypeDaoService.findByServiceTypeId(serviceTypeId);

        logger.info("Fetched {} service centers for service type ID: {}", scstList.size(), serviceTypeId);
        return scstList.stream()
                .map(ServiceCenterServiceTypeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    ServiceCenterServiceType convertToEntity(ServiceCenterServiceTypeDTO serviceCenterServiceTypeDTO) {
        ServiceCenter serviceCenter = serviceCenterDaoService.findById(serviceCenterServiceTypeDTO.getServiceCenterId());
        ServiceType serviceType = serviceTypeDaoService.findById(serviceCenterServiceTypeDTO.getServiceTypeId());

        ServiceCenterServiceType serviceCenterServiceType = ServiceCenterServiceTypeMapper.convertToEntity(serviceCenterServiceTypeDTO);

        serviceCenterServiceType.setServiceCenter(serviceCenter);
        serviceCenterServiceType.setServiceType(serviceType);

        return serviceCenterServiceType;
    }

}