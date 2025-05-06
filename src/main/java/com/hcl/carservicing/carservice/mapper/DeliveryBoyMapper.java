package com.hcl.carservicing.carservice.mapper;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
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
