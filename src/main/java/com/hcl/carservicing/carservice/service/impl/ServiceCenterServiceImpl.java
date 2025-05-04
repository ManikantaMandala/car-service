package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hcl.carservicing.carservice.dao.service.ServiceCenterDaoService;
import com.hcl.carservicing.carservice.mapper.ServiceCenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import org.springframework.stereotype.Service;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import com.hcl.carservicing.carservice.service.ServiceCenterService;

@Service
public class ServiceCenterServiceImpl implements ServiceCenterService {

	private static final Logger logger = LoggerFactory.getLogger(ServiceCenterServiceImpl.class);

    private final ServiceCenterRepository serviceCenterRepository;
    private final ServiceCenterDaoService serviceCenterDaoService;

    public ServiceCenterServiceImpl(ServiceCenterRepository serviceCenterRepository,
                                    ServiceCenterDaoService serviceCenterDaoService) {
        this.serviceCenterRepository = serviceCenterRepository;
        this.serviceCenterDaoService = serviceCenterDaoService;
    }

    @Override
    public void createServiceCenter(ServiceCenterDTO serviceCenterDTO) {
    	logger.info("Creating service center with name: {}", serviceCenterDTO.getName());
        ServiceCenter serviceCenter = ServiceCenterMapper.convertToEntity(serviceCenterDTO);

        ServiceCenter savedServiceCenter = serviceCenterRepository.save(serviceCenter);
        logger.info("Service center created successfully with ID: {}", savedServiceCenter.getId());
    }

    @Override
    public void updateServiceCenter(Long id, ServiceCenterDTO serviceCenterDTO) {
        ServiceCenter serviceCenter = serviceCenterDaoService.findById(id);

        logger.info("Updating service center with ID: {}", id);
        serviceCenter.setName(serviceCenterDTO.getName());
        serviceCenter.setAddress(serviceCenterDTO.getAddress());
        serviceCenter.setRating(serviceCenterDTO.getRating());
        serviceCenter.setAvailable(serviceCenterDTO.getAvailable());

        serviceCenterRepository.save(serviceCenter);
        logger.info("updated the service center");
    }

    @Override
    public List<ServiceCenterDTO> getAllServiceCenters() {
    	logger.info("Fetching all service centers");
        List<ServiceCenter> serviceCenters = serviceCenterDaoService.findAll();
        logger.info("Fetched {} service centers", serviceCenters.size());

        return serviceCenters.stream()
                .map(ServiceCenterMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceCenterDTO> getAvailableServiceCenters(Boolean available) {
    	logger.info("Fetching service centers with availability: {}", available);
        List<ServiceCenter> serviceCenters = serviceCenterDaoService.findByAvailable(available);

        logger.info("Fetched {} service centers with availability: {}", serviceCenters.size(), available);
        return serviceCenters.stream()
                .map(ServiceCenterMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCenterDTO getServiceCenterById(Long id) {
        ServiceCenter serviceCenter = serviceCenterDaoService.findById(id);

        return ServiceCenterMapper.convertToDTO(serviceCenter);
    }

    @Override
    public void updateStatusOfServiceCenter(Long id, Boolean status) {
        ServiceCenter serviceCenter = serviceCenterDaoService.findById(id);
        serviceCenter.setAvailable(status);

        serviceCenterRepository.save(serviceCenter);
        logger.info("Service center status updated successfully with ID: {}", id);
    }

}