package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceRequestService {

    /**
     * Create a new servicing request by a user
     */
    void createRequest(ServiceRequestDTO requestDTO);

    /**
     * Retrieve all requests placed by a given user
     */
    List<ServiceRequestDTO> getRequestsByUser(String userId);

    /**
     * Admin: update status and optionally assign a delivery boy
     */
    ServiceRequestDTO updateRequestStatus(Long requestId, RequestStatus status, Long deliveryBoyId);

    /**
     * Admin: fetch all requests
     */
    List<ServiceRequestDTO> getAllRequests();
}

