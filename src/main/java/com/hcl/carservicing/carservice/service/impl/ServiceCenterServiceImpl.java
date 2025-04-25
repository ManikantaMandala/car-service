package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import com.hcl.carservicing.carservice.service.ServiceCenterService;

@Service
public class ServiceCenterServiceImpl implements ServiceCenterService {

    private final ServiceCenterRepository serviceCenterRepository;

    public ServiceCenterServiceImpl(ServiceCenterRepository serviceCenterRepository) {
        this.serviceCenterRepository = serviceCenterRepository;
    }

    @Override
    public ServiceCenterDTO createServiceCenter(ServiceCenterDTO serviceCenterDTO) {
        ServiceCenter serviceCenter = convertToEntity(serviceCenterDTO);
        ServiceCenter savedServiceCenter = serviceCenterRepository.save(serviceCenter);
        return convertToDTO(savedServiceCenter);
    }

    @Override
    public ServiceCenterDTO updateServiceCenter(Long id, ServiceCenterDTO serviceCenterDTO) {
        Optional<ServiceCenter> existingServiceCenter = serviceCenterRepository.findById(id);

        if (existingServiceCenter.isEmpty()) {
            throw new ElementNotFoundException("Service center not found: " + id);
        }

        ServiceCenter updatedServiceCenter = existingServiceCenter.get();

        updatedServiceCenter.setName(serviceCenterDTO.getName());
        updatedServiceCenter.setAddress(serviceCenterDTO.getAddress());
        updatedServiceCenter.setRating(serviceCenterDTO.getRating());
        updatedServiceCenter.setAvailable(serviceCenterDTO.getAvailable());

        ServiceCenter savedServiceCenter = serviceCenterRepository.save(updatedServiceCenter);

        return convertToDTO(savedServiceCenter);
    }

    @Override
    public List<ServiceCenterDTO> getAllServiceCenters() {
        List<ServiceCenter> serviceCenters = serviceCenterRepository.findAll();

        return serviceCenters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceCenterDTO> getAvailableServiceCenters(Boolean available) {
        List<ServiceCenter> serviceCenters = serviceCenterRepository.findByAvailable(available);

        return serviceCenters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCenterDTO getServiceCenterById(Long id) {
        return serviceCenterRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null); // or throw exception
    }

//    public ResponseEntity<ServiceCenterDTO> updateStatusOfServiceCenter(Long id, Boolean status) {
    @Override
    public void updateStatusOfServiceCenter(Long id, Boolean status) {
        Optional<ServiceCenter> existingServiceCenter = serviceCenterRepository.findById(id);

        if (existingServiceCenter.isEmpty()) {
            throw new ElementNotFoundException("Service center not found: " + id);
        }

        ServiceCenter updatedServiceCenter = existingServiceCenter.get();
        updatedServiceCenter.setAvailable(status);

//        ServiceCenter savedServiceCenter = serviceCenterRepository.save(updatedServiceCenter);
        serviceCenterRepository.save(updatedServiceCenter);
//        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedServiceCenter));
    }

    @Override
    public ServiceCenterDTO findById(Long id) {
        return serviceCenterRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ElementNotFoundException("Service center not found: " + id));
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

    private ServiceCenterDTO convertToDTO(ServiceCenter serviceCenter) {
        ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();

        serviceCenterDTO.setId(serviceCenter.getId());
        serviceCenterDTO.setName(serviceCenter.getName());
        serviceCenterDTO.setAddress(serviceCenter.getAddress());
        serviceCenterDTO.setRating(serviceCenter.getRating());
        serviceCenterDTO.setAvailable(serviceCenter.getAvailable());

        return serviceCenterDTO;
    }
}