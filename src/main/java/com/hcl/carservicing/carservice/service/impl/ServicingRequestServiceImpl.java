package com.hcl.carservicing.carservice.service.impl;

import java.util.List;
import java.util.Optional;

import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
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
    public ServicingRequestDTO createRequest(ServicingRequestDTO requestDTO) {
        ServicingRequest request = new ServicingRequest();
        request.setStartDate(requestDTO.getStartDate());
        request.setEndDate(requestDTO.getEndDate());
        request.setStatus(RequestStatus.PENDING);

        AppUser user = appUserRepository.findByUsername(requestDTO.getUsername())
            .orElseThrow(() -> new ElementNotFoundException("User not found: " + requestDTO.getUsername()));
        request.setUser(user);

        ServiceCenterServiceType service = serviceCenterServiceTypeRepository.findById(requestDTO.getServiceId())
            .orElseThrow(() -> new ElementNotFoundException("Service not found: " + requestDTO.getServiceId()));
        request.setService(service);

        // Retrieve the ServiceCenter from the ServiceCenterServiceType
        ServiceCenter serviceCenter = service.getServiceCenter();
        request.setServiceCenter(serviceCenter);

        // Check if deliveryBoyId is provided
        if (requestDTO.getDeliveryBoyId() != null) {
            DeliveryBoy deliveryBoy = deliveryBoyRepository.findById(requestDTO.getDeliveryBoyId())
                .orElseThrow(() -> new ElementNotFoundException("DeliveryBoy not found: " + requestDTO.getDeliveryBoyId()));
            request.setDeliveryBoy(deliveryBoy);
        }

        return toDto(repository.save(request));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicingRequestDTO> getRequestsByUser(String username) {
        List<ServicingRequest> servicingRequests = repository.findByUserUsername(username);

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

        if(servicingRequest.getStatus() != RequestStatus.PENDING) {
            servicingRequestDto.setDeliveryBoyId(servicingRequest.getDeliveryBoy().getId());
        }

        return servicingRequestDto;
    }

    @Override
    public ServicingRequestDTO updateRequestStatus(Long requestId, String status, Long deliveryBoyId) {
        ServicingRequest existing = repository.findById(requestId)
            .orElseThrow(() -> new ElementNotFoundException("Request not found: " + requestId));
        existing.setStatus(RequestStatus.valueOf(status));

        // TODO: create a strategy to allocate the deliveryBoy without admin specifying deliveryBoyId
        Optional<DeliveryBoy> deliveryBoy = deliveryBoyRepository.findById(deliveryBoyId);

//        if (deliveryBoy.isEmpty() && existing.getStatus() == RequestStatus.ACCEPTED) {
//            throw new ElementNotFoundException("Delivery boy not found: " + deliveryBoyId);
//        }
//        deliveryBoy.ifPresent(existing::setDeliveryBoy);
//        existing.setDeliveryBoy(deliveryBoy.get());

        deliveryBoy.ifPresentOrElse(existing::setDeliveryBoy, () -> {
            // TODO: can I do Predicate here
            if (existing.getStatus() == RequestStatus.ACCEPTED) {
                throw new ElementNotFoundException("Delivery boy not found: " + deliveryBoyId);
            }
            // TODO: think about this, for other type of requests what to do?
        });

        return toDto(repository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicingRequestDTO> getAllRequests() {
        return repository.findAll().stream().map(this::toDto).toList();
    }
}