package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ServicingRequest createRequest(ServicingRequestDTO requestDTO) {
        ServicingRequest request = new ServicingRequest();
        request.setStartDate(requestDTO.getStartDate());
        request.setEndDate(requestDTO.getEndDate());
        request.setStatus(RequestStatus.PENDING);

        AppUser user = appUserRepository.findByUsername(requestDTO.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + requestDTO.getUsername()));
        request.setUser(user);

        ServiceCenterServiceType service = serviceCenterServiceTypeRepository.findById(requestDTO.getServiceId())
            .orElseThrow(() -> new IllegalArgumentException("Service not found: " + requestDTO.getServiceId()));
        request.setService(service);

        // Retrieve the ServiceCenter from the ServiceCenterServiceType
        ServiceCenter serviceCenter = service.getServiceCenter();
        request.setServiceCenter(serviceCenter);

        // Check if deliveryBoyId is provided
        if (requestDTO.getDeliveryBoyId() != null) {
            DeliveryBoy deliveryBoy = deliveryBoyRepository.findById(requestDTO.getDeliveryBoyId())
                .orElseThrow(() -> new IllegalArgumentException("DeliveryBoy not found: " + requestDTO.getDeliveryBoyId()));
            request.setDeliveryBoy(deliveryBoy);
        }

        return repository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicingRequest> getRequestsByUser(String username) {
        return repository.findByUserUsername(username);
    }

    @Override
    @Transactional
    public ServicingRequest updateRequestStatus(Long requestId, String status, Long deliveryBoyId) {
        ServicingRequest existing = repository.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));
        existing.setStatus(RequestStatus.valueOf(status));

        Optional<DeliveryBoy> deliveryBoy = deliveryBoyRepository.findById(deliveryBoyId);
        deliveryBoy.ifPresent(existing::setDeliveryBoy);

        return repository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicingRequest> getAllRequests() {
        return repository.findAll();
    }
}