package com.hcl.carservicing.carservice.service.impl;

import java.util.List;

import com.hcl.carservicing.carservice.dao.service.*;
import com.hcl.carservicing.carservice.dao.strategy.DeliveryBoyAllocationStrategy;
import com.hcl.carservicing.carservice.mapper.ServiceRequestMapper;
import com.hcl.carservicing.carservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.service.ServiceRequestService;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {
	private static final Logger logger = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);

    private final ServiceRequestDaoService serviceRequestDaoService;
    private final AppUserDaoService appUserDaoService;
    private final DeliveryBoyDaoService deliveryBoyDaoService;
    private final ServiceCenterServiceTypeDaoService serviceCenterServiceTypeDaoService;
    private final DeliveryBoyAllocationStrategy deliveryBoyAllocationStrategy;
    private final VehicleDetailsDaoService vehicleDetailsDaoService;

    public ServiceRequestServiceImpl(AppUserDaoService appUserDaoService,
                                     ServiceRequestDaoService serviceRequestDaoService,
                                     DeliveryBoyDaoService deliveryBoyDaoService,
                                     ServiceCenterServiceTypeDaoService serviceCenterServiceTypeDaoService,
                                     DeliveryBoyAllocationStrategy deliveryBoyAllocationStrategy,
                                     VehicleDetailsDaoService vehicleDetailsDaoService) {

        this.appUserDaoService = appUserDaoService;
        this.serviceRequestDaoService = serviceRequestDaoService;
        this.deliveryBoyDaoService = deliveryBoyDaoService;
        this.serviceCenterServiceTypeDaoService = serviceCenterServiceTypeDaoService;
        this.deliveryBoyAllocationStrategy = deliveryBoyAllocationStrategy;
        this.vehicleDetailsDaoService = vehicleDetailsDaoService;
    }

    @Override
    @Transactional
    public void createRequest(ServiceRequestDTO requestDTO) {
    	logger.info("Creating servicing request for user: {}", requestDTO.getUsername());

        AppUser user = appUserDaoService.findByUsername(requestDTO.getUsername());
        ServiceCenterServiceType service = serviceCenterServiceTypeDaoService.findById(requestDTO.getServiceId());

        ServiceRequest request = ServiceRequestMapper.toEntity(requestDTO);

        request.setUser(user);
        request.setService(service);
        request.setServiceCenter(service.getServiceCenter());

        VehicleDetails vehicleDetails = new VehicleDetails(user, requestDTO.getVehicleName());
        vehicleDetailsDaoService.save(vehicleDetails);
        request.setVehicleDetails(vehicleDetails);

        if (requestDTO.getDeliveryBoyId() != null) {
            DeliveryBoy deliveryBoy = deliveryBoyDaoService.findById(requestDTO.getDeliveryBoyId());
            request.setDeliveryBoy(deliveryBoy);
        }

        ServiceRequest savedRequest = serviceRequestDaoService.save(request);
        logger.info("Servicing request created successfully with ID: {}", savedRequest.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> getRequestsByUser(String username) {
    	logger.info("Fetching servicing requests for user: {}", username);
    	List<ServiceRequest> serviceRequests = serviceRequestDaoService.findByUserUsername(username);
        logger.debug(serviceRequests.toString());

    	logger.info("Fetched {} servicing requests for user: {}", serviceRequests.size(), username);
    	return serviceRequests.stream().map(ServiceRequestMapper::toDto).toList();
    }

    @Transactional
    public ServiceRequestDTO updateRequestStatusWithStrategy(Long requestId, RequestStatus status) {
        ServiceRequest serviceRequest = serviceRequestDaoService.findById(requestId);

        if (status == RequestStatus.ACCEPTED) {
            DeliveryBoy deliveryBoy = deliveryBoyAllocationStrategy.getAvailableDeliveryBoy();
            serviceRequest.setDeliveryBoy(deliveryBoy);
        }
        serviceRequest.setStatus(status);

        ServiceRequest updatedRequest = serviceRequestDaoService.save(serviceRequest);
        logger.info("Servicing request status updated successfully with ID: {} in updateRequestStatusWithStrategy",
                updatedRequest.getId());
        return ServiceRequestMapper.toDto(updatedRequest);
    }

    @Override
    @Transactional
    public ServiceRequestDTO updateRequestStatus(Long requestId, RequestStatus status, Long deliveryBoyId) {
    	logger.info("Updating status of servicing request with ID: {}", requestId);
    	ServiceRequest existing = serviceRequestDaoService.findById(requestId);

        if (status == RequestStatus.ACCEPTED) {
            if (deliveryBoyId == null) {
                logger.error("DeliveryBoyId must be provided when status is ACCEPTED");
                throw new IllegalArgumentException("DeliveryBoyId must be provided when status is ACCEPTED");
            }

            DeliveryBoy deliveryBoy = deliveryBoyDaoService.findById(deliveryBoyId);
            existing.setDeliveryBoy(deliveryBoy);
        }

        // TODO: if rejected how does user knows the reason of rejecting, think of this problem
        existing.setStatus(status);

        ServiceRequest updatedRequest = serviceRequestDaoService.save(existing);
        logger.info("Servicing request status updated successfully with ID: {} in updateRequestStatus", updatedRequest.getId());
        return ServiceRequestMapper.toDto(updatedRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> getAllRequests() {
        logger.info("Fetching all servicing requests");
        List<ServiceRequest> requests = serviceRequestDaoService.findAll();

        logger.info("Fetched {} servicing requests", requests.size());
        return requests.stream().map(ServiceRequestMapper::toDto).toList();
    }

}