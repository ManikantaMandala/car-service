package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;

public interface DeliveryBoyService {

//    DeliveryBoyDTO createDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO);
    void createDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO);

    DeliveryBoyDTO updateDeliveryBoy(Long id, DeliveryBoyDTO deliveryBoyDTO);

    List<DeliveryBoyDTO> getDeliveryBoysByCenter(Long centerId);

    List<DeliveryBoyDTO> getAvailableDeliveryBoys();
}

