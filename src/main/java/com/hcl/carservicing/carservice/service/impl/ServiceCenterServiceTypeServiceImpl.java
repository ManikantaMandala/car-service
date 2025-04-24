package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
    private final ServiceCenterServiceTypeRepository repository;
    private final ServiceCenterService serviceCenterService;
    private final ServiceTypeService serviceTypeService;

    public ServiceCenterServiceTypeServiceImpl(ServiceCenterServiceTypeRepository repository,
                                               ServiceCenterService serviceCenterService,
                                               ServiceTypeService serviceTypeService) {
        this.repository = repository;
        this.serviceCenterService = serviceCenterService;
        this.serviceTypeService = serviceTypeService;
    }

    @Override
    @Transactional
    public ServiceCenterServiceTypeDTO addServiceTypeToCenter(ServiceCenterServiceTypeDTO scstDTO) {
        ServiceCenterServiceType scst = convertToEntity(scstDTO);
        ServiceCenterServiceType savedScst = repository.save(scst);
        return convertToDTO(savedScst);
    }

    @Override
    @Transactional
    public ServiceCenterServiceTypeDTO updateServiceCenterServiceType(Long id, ServiceCenterServiceTypeDTO scstDTO) {
        ServiceCenterServiceType existing = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ServiceCenterServiceType not found: " + id));
        existing.setServiceCenter(convertToEntity(serviceCenterService.findById(scstDTO.getServiceCenterId())));
        existing.setServiceType(convertToEntity(serviceTypeService.findById(scstDTO.getServiceTypeId())));
        existing.setCost(scstDTO.getCost());
        ServiceCenterServiceType savedScst = repository.save(existing);
        return convertToDTO(savedScst);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCenterServiceTypeDTO> getByServiceCenter(Long serviceCenterId) {
        List<ServiceCenterServiceType> scstList = repository.findByServiceCenterId(serviceCenterId);
        return scstList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCenterServiceTypeDTO> getByServiceType(Long serviceTypeId) {
        List<ServiceCenterServiceType> scstList = repository.findByServiceTypeId(serviceTypeId);
        return scstList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ServiceCenterServiceType convertToEntity(ServiceCenterServiceTypeDTO scstDTO) {
        ServiceCenterServiceType scst = new ServiceCenterServiceType();
        scst.setId(scstDTO.getId());
        scst.setServiceCenter(convertToEntity(serviceCenterService.findById(scstDTO.getServiceCenterId())));
        scst.setServiceType(convertToEntity(serviceTypeService.findById(scstDTO.getServiceTypeId())));
        scst.setCost(scstDTO.getCost());
        return scst;
    }

    private ServiceCenterServiceTypeDTO convertToDTO(ServiceCenterServiceType scst) {
        ServiceCenterServiceTypeDTO scstDTO = new ServiceCenterServiceTypeDTO();
        scstDTO.setId(scst.getId());
        scstDTO.setServiceCenterId(scst.getServiceCenter().getId());
        scstDTO.setServiceTypeId(scst.getServiceType().getId());
        scstDTO.setCost(scst.getCost());
        return scstDTO;
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

    private ServiceType convertToEntity(ServiceTypeDTO serviceTypeDTO) {
        ServiceType serviceType = new ServiceType();
        serviceType.setId(serviceTypeDTO.getId());
        serviceType.setServiceName(serviceTypeDTO.getServiceName());
        return serviceType;
    }
}