package com.hcl.carservicing.carservice.mapper;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.model.ServiceRequest;

public class ServiceRequestMapper {

    public static ServiceRequestDTO toDto(ServiceRequest serviceRequest) {
        ServiceRequestDTO serviceRequestDto = new ServiceRequestDTO();

        serviceRequestDto.setId(serviceRequest.getId());
        serviceRequestDto.setStatus(serviceRequest.getStatus());
        serviceRequestDto.setStartDate(serviceRequest.getStartDate());
        serviceRequestDto.setEndDate(serviceRequest.getEndDate());
        serviceRequestDto.setUsername(serviceRequest.getUser().getUsername());
        serviceRequestDto.setServiceId(serviceRequest.getService().getId());
        serviceRequestDto.setServiceCenterId(serviceRequest.getServiceCenter().getId());

        if (serviceRequest.getDeliveryBoy() != null) {
            serviceRequestDto.setDeliveryBoyId(serviceRequest.getDeliveryBoy().getId());
        }
//        else {
//            serviceRequestDto.setDeliveryBoyId(null); // or handle it as needed
//        }

        return serviceRequestDto;
    }

    public static ServiceRequest toEntity(ServiceRequestDTO requestDTO) {
        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setStartDate(requestDTO.getStartDate());
        serviceRequest.setEndDate(requestDTO.getEndDate());
        serviceRequest.setStatus(RequestStatus.PENDING);

        return serviceRequest;
    }
}
