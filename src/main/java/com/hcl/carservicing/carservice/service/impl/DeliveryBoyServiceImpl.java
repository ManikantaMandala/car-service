package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcl.carservicing.carservice.exceptionhandler.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServicingRequest;
import com.hcl.carservicing.carservice.repository.DeliveryBoyRepository;
import com.hcl.carservicing.carservice.service.DeliveryBoyService;
import com.hcl.carservicing.carservice.service.ServiceCenterService;

@Service
public class DeliveryBoyServiceImpl implements DeliveryBoyService {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryBoyServiceImpl.class);

    private final DeliveryBoyRepository deliveryBoyRepository;
    private final ServiceCenterRepository serviceCenterRepository;

    public DeliveryBoyServiceImpl(DeliveryBoyRepository deliveryBoyRepository,
                                  ServiceCenterRepository serviceCenterRepository) {
        this.deliveryBoyRepository = deliveryBoyRepository;
        this.serviceCenterRepository = serviceCenterRepository;
    }

    @Override
    @Transactional
//    public DeliveryBoyDTO createDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO) {
    public void createDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO) {
    	logger.info("Creating delivery boy with contact number: {}", deliveryBoyDTO.getContactNumber());
        // Check for existing contact number
        Optional<DeliveryBoy> existingContactNumber = deliveryBoyRepository.findByContactNumber(deliveryBoyDTO.getContactNumber());
        if (existingContactNumber.isPresent()) {
        	logger.error("Contact number already exists: {}", deliveryBoyDTO.getContactNumber());
            throw new ElementAlreadyExistException("Contact Number already exists: " + deliveryBoyDTO.getContactNumber());
        }

        DeliveryBoy deliveryBoy = new DeliveryBoy();
        deliveryBoy.setName(deliveryBoyDTO.getName());
        deliveryBoy.setContactNumber(deliveryBoyDTO.getContactNumber());

        Optional<ServiceCenter> serviceCenterOptional = serviceCenterRepository.findById(deliveryBoyDTO.getServiceCenterId());
        if (serviceCenterOptional.isEmpty()) {
        	logger.error("Invalid Service Center ID: {}", deliveryBoyDTO.getServiceCenterId());
            throw new IllegalArgumentException("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId());
        }

        deliveryBoy.setServiceCenter(serviceCenterOptional.get());

        DeliveryBoy savedDeliveryBoy = deliveryBoyRepository.save(deliveryBoy);
        logger.info("Delivery boy created successfully with ID: {}", savedDeliveryBoy.getId());
    }

    @Override
    @Transactional
    public DeliveryBoyDTO updateDeliveryBoy(Long id, DeliveryBoyDTO deliveryBoyDTO) {
    	logger.info("Updating delivery boy with ID: {}", id);
        DeliveryBoy existing = deliveryBoyRepository.findById(id).orElseThrow(() -> {
        	logger.error("Delivery boy not found with ID: {}", id);
            return new ElementNotFoundException("DeliveryBoy not found: " + id);
        });

        existing.setName(deliveryBoyDTO.getName());
        existing.setContactNumber(deliveryBoyDTO.getContactNumber());

        Optional<ServiceCenter> serviceCenterOptional = serviceCenterRepository.findById(id);
        if (serviceCenterOptional.isEmpty()) {
            logger.error("Invalid Service Center ID: {}", deliveryBoyDTO.getServiceCenterId());
            throw new IllegalArgumentException("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId());
        }
        existing.setServiceCenter(serviceCenterOptional.get());

        DeliveryBoy updatedDeliveryBoy = deliveryBoyRepository.save(existing);
        logger.info("Delivery boy updated successfully with ID: {}", updatedDeliveryBoy.getId());
        return toDto(updatedDeliveryBoy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryBoyDTO> getDeliveryBoysByCenter(Long serviceCenterId) {
    	logger.info("Fetching delivery boys for service center ID: {}", serviceCenterId);

        if(serviceCenterId == null || serviceCenterId < 1) {
            throw new IllegalArgumentException("service center id: {}", null);
        }

    	List<DeliveryBoyDTO> deliveryBoys = deliveryBoyRepository.findByServiceCenterId(serviceCenterId).stream().map(this::toDto).toList();
    	logger.info("Fetched {} delivery boys for service center ID: {}", deliveryBoys.size(), serviceCenterId);
    	return deliveryBoys;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryBoyDTO> getAvailableDeliveryBoys() {
    	logger.info("Fetching available delivery boys");

    	List<DeliveryBoyDTO> availableDeliveryBoys = deliveryBoyRepository.findByServicingRequestIsNull().stream().map(this::toDto).toList();

    	logger.info("Fetched {} available delivery boys", availableDeliveryBoys.size());
    	return availableDeliveryBoys;
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

        if (deliveryBoy.getServicingRequest() != null) {
            List<Long> serviceRequestsId = deliveryBoy.getServicingRequest().stream()
                .map(ServicingRequest::getId)
                .collect(Collectors.toList());
            deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);
        }

        return deliveryBoyDTO;
    }

}
