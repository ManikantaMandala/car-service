package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.ServicingRequestDTO;

public interface ServicingRequestService {

    /**
     * Create a new servicing request by a user
     */
    ServicingRequestDTO createRequest(ServicingRequestDTO requestDTO);

    /**
     * Retrieve all requests placed by a given user
     */
    List<ServicingRequestDTO> getRequestsByUser(String userId);

    /**
     * Admin: update status and optionally assign a delivery boy
     */
    ServicingRequestDTO updateRequestStatus(Long requestId, String status, Long deliveryBoyId);

    /**
     * Admin: fetch all requests
     */
    List<ServicingRequestDTO> getAllRequests();
}

