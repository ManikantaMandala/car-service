package com.hcl.carservicing.carservice.dao.strategy.impl;

import com.hcl.carservicing.carservice.dao.service.DeliveryBoyDaoService;
import com.hcl.carservicing.carservice.exception.DeliveryBoyNotAvailable;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryBoyAllocationStrategyImplTest {

    @Mock
    private DeliveryBoyDaoService deliveryBoyDaoService;

    @InjectMocks
    private DeliveryBoyAllocationStrategyImpl allocationStrategy;

    @Test
    public void getAvailableDeliveryBoy_deliveryBoyAvailable_returnsDeliveryBoy() {
        List<DeliveryBoy> availableDeliveryBoys = new ArrayList<>();
        DeliveryBoy deliveryBoy = new DeliveryBoy();
        deliveryBoy.setId(1L);
        availableDeliveryBoys.add(deliveryBoy);

        when(deliveryBoyDaoService.findAvailableDeliveryBoys()).thenReturn(availableDeliveryBoys);

        DeliveryBoy result = allocationStrategy.getAvailableDeliveryBoy();

        assertEquals(deliveryBoy, result);
    }

    @Test
    public void getAvailableDeliveryBoy_noDeliveryBoyAvailable_throwsDeliveryBoyNotAvailable() {
        List<DeliveryBoy> availableDeliveryBoys = new ArrayList<>();

        when(deliveryBoyDaoService.findAvailableDeliveryBoys()).thenReturn(availableDeliveryBoys);

        DeliveryBoyNotAvailable exception = assertThrows(DeliveryBoyNotAvailable.class, () -> { // Changed exception type
            allocationStrategy.getAvailableDeliveryBoy();
        });
        assertEquals("no available delivery boys", exception.getMessage());
    }
}
