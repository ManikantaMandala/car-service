package com.hcl.carservicing.carservice.mapper;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceRequest;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryBoyMapper {

    public static DeliveryBoy toEntity(DeliveryBoyDTO deliveryBoyDTO) {
        DeliveryBoy deliveryBoy = new DeliveryBoy();

        deliveryBoy.setName(deliveryBoyDTO.getName());
        deliveryBoy.setContactNumber(deliveryBoyDTO.getContactNumber());

        return deliveryBoy;
    }

    // TODO: to have the list of ids of service request implement other toDto()
    public static DeliveryBoyDTO toDto(DeliveryBoy deliveryBoy) {
        DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();

        deliveryBoyDTO.setName(deliveryBoy.getName());
        deliveryBoyDTO.setContactNumber(deliveryBoy.getContactNumber());
        deliveryBoyDTO.setServiceCenterId(deliveryBoy.getServiceCenter().getId());

        if (deliveryBoy.getServicingRequest() != null) {
            List<Long> serviceRequestsId = deliveryBoy.getServicingRequest().stream()
                    .map(ServiceRequest::getId)
                    .collect(Collectors.toList());
            deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);
        }

        return deliveryBoyDTO;
    }



}
