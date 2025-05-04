package com.hcl.carservicing.carservice.dao.strategy;

import com.hcl.carservicing.carservice.model.DeliveryBoy;

public interface DeliveryBoyAllocationStrategy {
    DeliveryBoy getAvailableDeliveryBoy();
}
