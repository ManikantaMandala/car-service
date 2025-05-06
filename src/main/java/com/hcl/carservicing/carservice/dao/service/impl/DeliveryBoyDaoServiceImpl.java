package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.dao.service.DeliveryBoyDaoService;
import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.repository.DeliveryBoyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryBoyDaoServiceImpl implements DeliveryBoyDaoService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryBoyDaoServiceImpl.class);

    private final DeliveryBoyRepository deliveryBoyRepository;

    public DeliveryBoyDaoServiceImpl(DeliveryBoyRepository deliveryBoyRepository) {
        this.deliveryBoyRepository = deliveryBoyRepository;
    }

    @Override
    public DeliveryBoy findById(Long deliveryBoyId) {
        Optional<DeliveryBoy> deliveryBoyOptional = deliveryBoyRepository.findById(deliveryBoyId);
        if (deliveryBoyOptional.isEmpty()) {
            logger.error("DeliveryBoy not found: {}", deliveryBoyId);
            throw new ElementNotFoundException("Delivery boy not found: " + deliveryBoyId);
        }

        return deliveryBoyOptional.get();
    }

    @Override
    public List<DeliveryBoy> findByServiceCenterId(Long serviceCenterId) {
        return deliveryBoyRepository.findByServiceCenterId(serviceCenterId);
    }

    @Override
    public List<DeliveryBoy> findAvailableDeliveryBoys() {
        return deliveryBoyRepository.findWithLessThanFourServiceRequests();
    }

    @Override
    public void throwIfContactExists(String contactNumber) {
        Optional<DeliveryBoy> deliveryBoyOptional = deliveryBoyRepository.findByContactNumber(contactNumber);
        if (deliveryBoyOptional.isPresent()) {
            logger.error("Contact number already exists: {}", contactNumber);
            throw new ElementAlreadyExistException("Contact Number already exists: " + contactNumber);
        }
    }

    @Override
    public DeliveryBoy save(DeliveryBoy deliveryBoy) {
        return deliveryBoyRepository.save(deliveryBoy);
    }
}
