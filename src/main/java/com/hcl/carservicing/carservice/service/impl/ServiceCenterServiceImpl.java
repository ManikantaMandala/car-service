package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ServiceCenterServiceImpl(ServiceCenterRepository serviceCenterRepository) {
        this.serviceCenterRepository = serviceCenterRepository;
    }

    @Override
    public ServiceCenterDTO createServiceCenter(ServiceCenterDTO serviceCenterDTO) {
    	logger.info("Creating service center with name: {}", serviceCenterDTO.getName());
        ServiceCenter serviceCenter = convertToEntity(serviceCenterDTO);
        ServiceCenter savedServiceCenter = serviceCenterRepository.save(serviceCenter);
        logger.info("Service center created successfully with ID: {}", savedServiceCenter.getId());
        return convertToDTO(savedServiceCenter);
    }

    @Override
    public ServiceCenterDTO updateServiceCenter(Long id, ServiceCenterDTO serviceCenterDTO) {
    	logger.info("Updating service center with ID: {}", id);
        Optional<ServiceCenter> existingServiceCenter = serviceCenterRepository.findById(id);
        if (existingServiceCenter.isPresent()) {
            ServiceCenter updatedServiceCenter = existingServiceCenter.get();
            updatedServiceCenter.setName(serviceCenterDTO.getName());
            updatedServiceCenter.setAddress(serviceCenterDTO.getAddress());
            updatedServiceCenter.setRating(serviceCenterDTO.getRating());
            updatedServiceCenter.setAvailable(serviceCenterDTO.getAvailable());
            ServiceCenter savedServiceCenter = serviceCenterRepository.save(updatedServiceCenter);
            return convertToDTO(savedServiceCenter);
        }
        return null; // or throw exception as needed
    }

    @Override
    public List<ServiceCenterDTO> getAllServiceCenters() {
    	logger.info("Fetching all service centers");
        List<ServiceCenter> serviceCenters = serviceCenterRepository.findAll();
        logger.info("Fetched {} service centers", serviceCenters.size());

        return serviceCenters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceCenterDTO> getAvailableServiceCenters(Boolean available) {
    	logger.info("Fetching service centers with availability: {}", available);
        List<ServiceCenter> serviceCenters = serviceCenterRepository.findByAvailable(available);
        logger.info("Fetched {} service centers with availability: {}", serviceCenters.size(), available);

        return serviceCenters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCenterDTO getServiceCenterById(Long id) {
        // TODO: Custom exception
    	logger.info("Fetching service center with ID: {}", id);
    	return serviceCenterRepository.findById(id).map(serviceCenter -> {
    		logger.info("Service center found with ID: {}", id);
    		return convertToDTO(serviceCenter);
    		})
    			.orElseGet(() -> {logger.error("Service center not found with ID: {}", id);
    		return null; // or throw exception
    		});

    }

//    public ResponseEntity<ServiceCenterDTO> updateStatusOfServiceCenter(Long id, Boolean status) {
    @Override
    public void updateStatusOfServiceCenter(Long id, Boolean status) {
        Optional<ServiceCenter> existingServiceCenter = serviceCenterRepository.findById(id);

        if (existingServiceCenter.isEmpty()) {
            logger.error("Service center not found with ID: {}", id);
            throw new ElementNotFoundException("Service center not found: " + id);
        }

        ServiceCenter updatedServiceCenter = existingServiceCenter.get();
        updatedServiceCenter.setAvailable(status);

//        ServiceCenter savedServiceCenter = serviceCenterRepository.save(updatedServiceCenter);
        ServiceCenter savedServiceCenter = serviceCenterRepository.save(updatedServiceCenter);
        logger.info("Service center status updated successfully with ID: {}", savedServiceCenter.getId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedServiceCenter));
    }

    @Override
    public ServiceCenterDTO findById(Long id) {
    	logger.info("Finding service center with ID: {}", id);
    	return serviceCenterRepository.findById(id)
    			.map(serviceCenter -> {
    				logger.info("Service center found with ID: {}", id);
    				return convertToDTO(serviceCenter);
    			}).orElseThrow(() -> {
    				logger.error("Service center not found with ID: {}", id);
    				return new IllegalArgumentException("ServiceCenter not found: " + id);
    			});
    }

    private ServiceCenter convertToEntity(ServiceCenterDTO serviceCenterDTO) {
        ServiceCenter serviceCenter = new ServiceCenter();

        serviceCenter.setId(serviceCenterDTO.getId());
        serviceCenter.setName(serviceCenterDTO.getName());
        serviceCenter.setAddress(serviceCenterDTO.getAddress());
        serviceCenter.setRating(serviceCenterDTO.getRating());
        serviceCenter.setAvailable(serviceCenterDTO.getAvailable());

        return serviceCenter;
    }

    private ServiceCenterServiceTypeDTO convertServiceTypeToDTO(ServiceCenterServiceType serviceType) {
    	ServiceCenterServiceTypeDTO scstDTO = new ServiceCenterServiceTypeDTO();
        scstDTO.setId(serviceType.getId());
        scstDTO.setServiceCenterId(serviceType.getServiceCenter().getId());
        scstDTO.setServiceTypeId(serviceType.getServiceType().getId());
        scstDTO.setCost(serviceType.getCost());
        return scstDTO;
    }

    private ServiceCenterDTO convertToDTO(ServiceCenter serviceCenter) {
        ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
        serviceCenterDTO.setId(serviceCenter.getId());
        serviceCenterDTO.setName(serviceCenter.getName());
        serviceCenterDTO.setAddress(serviceCenter.getAddress());
        serviceCenterDTO.setRating(serviceCenter.getRating());
        serviceCenterDTO.setAvailable(serviceCenter.getAvailable());

        if (serviceCenter.getServiceCenterServiceTypes() != null && !serviceCenter.getServiceCenterServiceTypes().isEmpty()) {
            List<ServiceCenterServiceTypeDTO> serviceTypeDTOs = serviceCenter.getServiceCenterServiceTypes().stream()
                .map(this::convertServiceTypeToDTO)
                .collect(Collectors.toList());
            serviceCenterDTO.setServiceCenterServiceTypes(serviceTypeDTOs);
        }

        return serviceCenterDTO;
    }

}