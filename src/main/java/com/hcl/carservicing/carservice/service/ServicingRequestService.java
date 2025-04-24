package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.ServicingRequestDTO;
import com.hcl.carservicing.carservice.model.ServicingRequest;

public interface ServicingRequestService {

    /**
     * Create a new servicing request by a user
     */
    ServicingRequest createRequest(ServicingRequestDTO requestDTO);

    /**
     * Retrieve all requests placed by a given user
     */
    List<ServicingRequest> getRequestsByUser(String userId);

    /**
     * Admin: update status and optionally assign a delivery boy
     */
    ServicingRequest updateRequestStatus(Long requestId, String status, Long deliveryBoyId);

    /**
     * Admin: fetch all requests
     */
    List<ServicingRequest> getAllRequests();
}

