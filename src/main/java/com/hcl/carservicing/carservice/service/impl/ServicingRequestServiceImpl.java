package com.hcl.carservicing.carservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.dto.ServicingRequestDTO;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.model.ServiceCenterServiceType;
import com.hcl.carservicing.carservice.model.ServicingRequest;
import com.hcl.carservicing.carservice.repository.AppUserRepository;
import com.hcl.carservicing.carservice.repository.DeliveryBoyRepository;
import com.hcl.carservicing.carservice.repository.ServiceCenterRepository;
import com.hcl.carservicing.carservice.repository.ServiceCenterServiceTypeRepository;
import com.hcl.carservicing.carservice.repository.ServicingRequestRepository;
import com.hcl.carservicing.carservice.service.ServicingRequestService;

@Service
public class ServicingRequestServiceImpl implements ServicingRequestService {
	private static final Logger logger = LoggerFactory.getLogger(ServicingRequestServiceImpl.class);
	
    private final ServicingRequestRepository repository;
    private final AppUserRepository appUserRepository;
    private final DeliveryBoyRepository deliveryBoyRepository;
    private final ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository;
    public ServicingRequestServiceImpl(ServicingRequestRepository repository,
    		AppUserRepository appUserRepository,DeliveryBoyRepository deliveryBoyRepository,
            ServiceCenterServiceTypeRepository serviceCenterServiceTypeRepository,
            ServiceCenterRepository serviceCenterRepository) 
    {
        this.repository = repository;
        this.appUserRepository = appUserRepository;
        this.deliveryBoyRepository = deliveryBoyRepository;
        this.serviceCenterServiceTypeRepository = serviceCenterServiceTypeRepository;
    }

    @Override
    @Transactional
    public ServicingRequestDTO createRequest(ServicingRequestDTO requestDTO) {
    	logger.info("Creating servicing request for user: {}", requestDTO.getUsername());
        ServicingRequest request = new ServicingRequest();
        request.setStartDate(requestDTO.getStartDate());
        request.setEndDate(requestDTO.getEndDate());
        request.setStatus(RequestStatus.PENDING);
        
        AppUser user = appUserRepository.findByUsername(requestDTO.getUsername())
        	.orElseThrow(() -> {
        		logger.error("User not found: {}", requestDTO.getUsername());
        		return new IllegalArgumentException("User not found: " + requestDTO.getUsername());
        	});
        request.setUser(user);
        
        ServiceCenterServiceType service = serviceCenterServiceTypeRepository.findById(requestDTO.getServiceId())
        	.orElseThrow(() -> {
        		logger.error("Service not found: {}", requestDTO.getServiceId());
        		return new IllegalArgumentException("Service not found: " + requestDTO.getServiceId());
        	});
        request.setService(service);


        // Retrieve the ServiceCenter from the ServiceCenterServiceType
        ServiceCenter serviceCenter = service.getServiceCenter();
        request.setServiceCenter(serviceCenter);

        // Check if deliveryBoyId is provided
        if (requestDTO.getDeliveryBoyId() != null) {
        	DeliveryBoy deliveryBoy = deliveryBoyRepository.findById(requestDTO.getDeliveryBoyId())
        		.orElseThrow(() -> {
        			logger.error("DeliveryBoy not found: {}", requestDTO.getDeliveryBoyId());
        			return new IllegalArgumentException("DeliveryBoy not found: " + requestDTO.getDeliveryBoyId());
        		});
        	request.setDeliveryBoy(deliveryBoy);
        }
        
        ServicingRequest savedRequest = repository.save(request);
        logger.info("Servicing request created successfully with ID: {}", savedRequest.getId());
        return toDto(savedRequest);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicingRequestDTO> getRequestsByUser(String username) {
    	logger.info("Fetching servicing requests for user: {}", username);
    	List<ServicingRequest> servicingRequests = repository.findByUserUsername(username);
    	logger.info("Fetched {} servicing requests for user: {}", servicingRequests.size(), username);
    	return servicingRequests.stream().map(this::toDto).toList();

    }

    public ServicingRequestDTO toDto(ServicingRequest servicingRequest) {
        ServicingRequestDTO servicingRequestDto = new ServicingRequestDTO();

        servicingRequestDto.setId(servicingRequest.getId());
        servicingRequestDto.setStatus(servicingRequest.getStatus());
        servicingRequestDto.setStartDate(servicingRequest.getStartDate());
        servicingRequestDto.setEndDate(servicingRequest.getEndDate());
        servicingRequestDto.setUsername(servicingRequest.getUser().getUsername());
        servicingRequestDto.setServiceId(servicingRequest.getService().getId());
        servicingRequestDto.setServiceCenterId(servicingRequest.getServiceCenter().getId());

        if (servicingRequest.getDeliveryBoy() != null) {
            servicingRequestDto.setDeliveryBoyId(servicingRequest.getDeliveryBoy().getId());
        } else {
            servicingRequestDto.setDeliveryBoyId(null); // or handle it as needed
        }

        return servicingRequestDto;
    }


    @Override
    @Transactional
    public ServicingRequestDTO updateRequestStatus(Long requestId, String status, Long deliveryBoyId) {
    	logger.info("Updating status of servicing request with ID: {}", requestId);
    	ServicingRequest existing = repository.findById(requestId)
    		.orElseThrow(() -> {
    			logger.error("Request not found: {}", requestId);
    			return new IllegalArgumentException("Request not found: " + requestId);
    		});

        if (RequestStatus.ACCEPTED.name().equals(status) && deliveryBoyId == null) {
        	logger.error("DeliveryBoyId must be provided when status is ACCEPTED");
        	throw new IllegalArgumentException("DeliveryBoyId must be provided when status is ACCEPTED");
        }
        
        existing.setStatus(RequestStatus.valueOf(status));


        if (deliveryBoyId != null) {
        	DeliveryBoy deliveryBoy = deliveryBoyRepository.findById(deliveryBoyId)
        		.orElseThrow(() -> {
        			logger.error("DeliveryBoy not found: {}", deliveryBoyId);
        			return new IllegalArgumentException("DeliveryBoy not found: " + deliveryBoyId);
        		});
        	
        existing.setDeliveryBoy(deliveryBoy);

            
            // Assuming DeliveryBoyDTO has a method setServiceRequestsId
            DeliveryBoyDTO deliveryBoyDTO = toDtoDeliveyBoy(deliveryBoy);
            List<Long> serviceRequestsIds = deliveryBoyDTO.getServiceRequestsId();
            if (serviceRequestsIds == null) {
                serviceRequestsIds = new ArrayList<>();
            }
            serviceRequestsIds.add(requestId);
            deliveryBoyDTO.setServiceRequestsId(serviceRequestsIds);
        }
        
        ServicingRequest updatedRequest = repository.save(existing);
        logger.info("Servicing request status updated successfully with ID: {}", updatedRequest.getId());
        return toDto(updatedRequest);

    }
    
    private DeliveryBoyDTO toDtoDeliveyBoy(DeliveryBoy deliveryBoy) {
    	DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();

        deliveryBoyDTO.setName(deliveryBoy.getName());
        deliveryBoyDTO.setContactNumber(deliveryBoy.getContactNumber());
        deliveryBoyDTO.setServiceCenterId(deliveryBoy.getServiceCenter().getId());

        return deliveryBoyDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicingRequestDTO> getAllRequests() {
    	logger.info("Fetching all servicing requests");
    	List<ServicingRequest> requests = repository.findAll();
    	logger.info("Fetched {} servicing requests", requests.size());
    	return requests.stream().map(this::toDto).toList();

    }
}