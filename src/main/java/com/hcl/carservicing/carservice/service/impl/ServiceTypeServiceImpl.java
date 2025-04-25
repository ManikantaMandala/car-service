package com.hcl.carservicing.carservice.service.impl;

import java.util.List;

import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.model.ServiceType;
import com.hcl.carservicing.carservice.repository.ServiceTypeRepository;
import com.hcl.carservicing.carservice.service.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    @Transactional
    public ServiceTypeDTO createServiceType(ServiceTypeDTO serviceTypeDTO) {
        ServiceType serviceType = new ServiceType();

        serviceType.setServiceName(serviceTypeDTO.getServiceName());
        serviceType.setDescription(serviceTypeDTO.getDescription());

        return convertToDTO(serviceTypeRepository.save(serviceType));
    }

    @Override
    @Transactional
    public ServiceTypeDTO updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO) {
        ServiceType existing = serviceTypeRepository.findById(id)
            .orElseThrow(() -> new ElementNotFoundException("Service type not found: " + id));
        existing.setServiceName(serviceTypeDTO.getServiceName());
        existing.setDescription(serviceTypeDTO.getDescription());
        return convertToDTO(serviceTypeRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeDTO getServiceTypeById(Long id) {
        return serviceTypeRepository.findById(id).map(this::convertToDTO)
            .orElseThrow(() -> new ElementNotFoundException("Service type not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceTypeDTO> getAllServiceTypes() {
        return serviceTypeRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceTypeDTO findById(Long id) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
            .orElseThrow(() -> new ElementNotFoundException("Service type not found: " + id));
        return convertToDTO(serviceType);
    }

    private ServiceTypeDTO convertToDTO(ServiceType serviceType) {
        ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();
        serviceTypeDTO.setId(serviceType.getId());
        serviceTypeDTO.setServiceName(serviceType.getServiceName());
        return serviceTypeDTO;
    }
}
