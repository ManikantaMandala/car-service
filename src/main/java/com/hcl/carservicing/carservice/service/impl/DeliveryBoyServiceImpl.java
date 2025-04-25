package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;

import com.hcl.carservicing.carservice.exceptionhandler.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.repository.DeliveryBoyRepository;
import com.hcl.carservicing.carservice.service.DeliveryBoyService;
import com.hcl.carservicing.carservice.service.ServiceCenterService;

@Service
public class DeliveryBoyServiceImpl implements DeliveryBoyService {
    private final DeliveryBoyRepository deliveryBoyRepository;
    private final ServiceCenterService serviceCenterService;

    public DeliveryBoyServiceImpl(DeliveryBoyRepository deliveryBoyRepository, ServiceCenterService serviceCenterService) {
        this.deliveryBoyRepository = deliveryBoyRepository;
        this.serviceCenterService = serviceCenterService;
    }

    @Override
    @Transactional
    public DeliveryBoyDTO createDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO) {
        // Check for existing contact number
        Optional<DeliveryBoy> existingContactNumber = deliveryBoyRepository.findByContactNumber(deliveryBoyDTO.getContactNumber());
        if (existingContactNumber.isPresent()) {
            throw new ElementAlreadyExistException("Contact Number already exists: " + deliveryBoyDTO.getContactNumber());
        }

        DeliveryBoy deliveryBoy = new DeliveryBoy();
        deliveryBoy.setName(deliveryBoyDTO.getName());
        deliveryBoy.setContactNumber(deliveryBoyDTO.getContactNumber());

        ServiceCenterDTO serviceCenterDTO = serviceCenterService.findById(deliveryBoyDTO.getServiceCenterId());
        if (serviceCenterDTO == null) {
            throw new IllegalArgumentException("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId());
        }
        ServiceCenter serviceCenter = convertToEntity(serviceCenterDTO);
        deliveryBoy.setServiceCenter(serviceCenter);

        return toDto(deliveryBoyRepository.save(deliveryBoy));
    }



    @Override
    @Transactional
    public DeliveryBoyDTO updateDeliveryBoy(Long id, DeliveryBoyDTO deliveryBoyDTO) {
        DeliveryBoy existing = deliveryBoyRepository.findById(id)
            .orElseThrow(() -> new ElementNotFoundException("DeliveryBoy not found: " + id));
        existing.setName(deliveryBoyDTO.getName());
        existing.setContactNumber(deliveryBoyDTO.getContactNumber());

        ServiceCenterDTO serviceCenterDTO = serviceCenterService.findById(deliveryBoyDTO.getServiceCenterId());
        if (serviceCenterDTO == null) {
            throw new IllegalArgumentException("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId());
        }
        ServiceCenter serviceCenter = convertToEntity(serviceCenterDTO);
        existing.setServiceCenter(serviceCenter);

        return toDto(deliveryBoyRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryBoyDTO> getDeliveryBoysByCenter(Long serviceCenterId) {
        return deliveryBoyRepository.findByServiceCenterId(serviceCenterId).stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryBoyDTO> getAvailableDeliveryBoys() {
        return deliveryBoyRepository.findByServicingRequestIsNull().stream().map(this::toDto).toList();
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

    // TODO: to have the list of ids of service request implement other toDto()
    private DeliveryBoyDTO toDto(DeliveryBoy deliveryBoy) {
        DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();

        deliveryBoyDTO.setName(deliveryBoy.getName());
        deliveryBoyDTO.setContactNumber(deliveryBoy.getContactNumber());
        deliveryBoyDTO.setServiceCenterId(deliveryBoy.getServiceCenter().getId());

        return deliveryBoyDTO;
    }
}
