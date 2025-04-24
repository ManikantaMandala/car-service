package com.hcl.carservicing.carservice.service;

import java.util.List;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.model.DeliveryBoy;

public interface DeliveryBoyService {

    DeliveryBoy createDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO);

    DeliveryBoy updateDeliveryBoy(Long id, DeliveryBoyDTO deliveryBoyDTO);

    List<DeliveryBoy> getDeliveryBoysByCenter(Long centerId);

    List<DeliveryBoy> getAvailableDeliveryBoys();
}

