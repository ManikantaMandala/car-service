package com.hcl.carservicing.carservice.dao.strategy.impl;

import com.hcl.carservicing.carservice.dao.service.DeliveryBoyDaoService;
import com.hcl.carservicing.carservice.dao.strategy.DeliveryBoyAllocationStrategy;
import com.hcl.carservicing.carservice.exception.DeliveryBoyNotAvailable;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryBoyAllocationStrategyImpl implements DeliveryBoyAllocationStrategy {

    private final DeliveryBoyDaoService deliveryBoyDaoService;

    public DeliveryBoyAllocationStrategyImpl(DeliveryBoyDaoService deliveryBoyDaoService) {
        this.deliveryBoyDaoService = deliveryBoyDaoService;
    }

    @Override
    public DeliveryBoy getAvailableDeliveryBoy() {
        List<DeliveryBoy> availableDeliveryBoys = deliveryBoyDaoService.findAvailableDeliveryBoys();

        Optional<DeliveryBoy> firstDeliveryBoy = availableDeliveryBoys.stream().findFirst();
        if (firstDeliveryBoy.isEmpty()) {
            throw new DeliveryBoyNotAvailable("no available delivery boys");
        }

        return firstDeliveryBoy.get();
    }
}
