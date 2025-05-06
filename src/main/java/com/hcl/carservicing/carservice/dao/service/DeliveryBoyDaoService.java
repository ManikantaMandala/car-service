package com.hcl.carservicing.carservice.dao.service;

import com.hcl.carservicing.carservice.model.DeliveryBoy;

import java.util.List;

public interface DeliveryBoyDaoService {
    DeliveryBoy findById(Long deliveryBoyId);
    List<DeliveryBoy> findByServiceCenterId(Long serviceCenterId);
    List<DeliveryBoy> findAvailableDeliveryBoys();
    void throwIfContactExists(String contactNumber);
    DeliveryBoy save(DeliveryBoy deliveryBoy);
}
