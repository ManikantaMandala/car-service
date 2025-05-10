package com.hcl.carservicing.carservice.service.impl;

import java.util.List;

import com.hcl.carservicing.carservice.dao.service.DeliveryBoyDaoService;
import com.hcl.carservicing.carservice.dao.service.ServiceCenterDaoService;
import com.hcl.carservicing.carservice.mapper.DeliveryBoyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.model.DeliveryBoy;
import com.hcl.carservicing.carservice.model.ServiceCenter;
import com.hcl.carservicing.carservice.service.DeliveryBoyService;

@Service
public class DeliveryBoyServiceImpl implements DeliveryBoyService {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryBoyServiceImpl.class);

    private final ServiceCenterDaoService serviceCenterDaoService;
    private final DeliveryBoyDaoService deliveryBoyDaoService;

    public DeliveryBoyServiceImpl(ServiceCenterDaoService serviceCenterDaoService,
                                  DeliveryBoyDaoService deliveryBoyDaoService) {
        this.serviceCenterDaoService = serviceCenterDaoService;
        this.deliveryBoyDaoService = deliveryBoyDaoService;
    }

    @Override
    @Transactional
    public void createDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO) {
    	logger.info("Creating delivery boy with contact number: {}", deliveryBoyDTO.getContactNumber());

        deliveryBoyDaoService.throwIfContactExists(deliveryBoyDTO.getContactNumber());

        ServiceCenter serviceCenter = serviceCenterDaoService.findById(
                deliveryBoyDTO.getServiceCenterId());

        DeliveryBoy deliveryBoy = DeliveryBoyMapper.toEntity(deliveryBoyDTO);
        deliveryBoy.setServiceCenter(serviceCenter);

        DeliveryBoy savedDeliveryBoy = deliveryBoyDaoService.save(deliveryBoy);
        logger.info("Delivery boy created successfully with ID: {}", savedDeliveryBoy.getId());
    }

    @Override
    @Transactional
    public DeliveryBoyDTO updateDeliveryBoy(Long id, DeliveryBoyDTO deliveryBoyDTO) {
    	logger.info("Updating delivery boy with ID: {}", id);
        DeliveryBoy deliveryBoy = deliveryBoyDaoService.findById(id);

        ServiceCenter serviceCenter = serviceCenterDaoService.findById(
                deliveryBoyDTO.getServiceCenterId());

        deliveryBoy.setName(deliveryBoyDTO.getName());
        deliveryBoy.setContactNumber(deliveryBoyDTO.getContactNumber());
        deliveryBoy.setServiceCenter(serviceCenter);

        DeliveryBoy updatedDeliveryBoy = deliveryBoyDaoService.save(deliveryBoy);
        logger.info("Delivery boy updated successfully with ID: {}", updatedDeliveryBoy.getId());
        return DeliveryBoyMapper.toDto(updatedDeliveryBoy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryBoyDTO> getDeliveryBoysByCenter(Long serviceCenterId) {
    	logger.info("Fetching delivery boys for service center ID: {}", serviceCenterId);

        if(serviceCenterId < 1) {
            throw new IllegalArgumentException("service center id: {}", null);
        }

    	List<DeliveryBoyDTO> deliveryBoys = deliveryBoyDaoService.findByServiceCenterId(serviceCenterId)
                .stream()
                .map(DeliveryBoyMapper::toDto)
                .toList();

    	logger.info("Fetched {} delivery boys for service center ID: {}", deliveryBoys.size(), serviceCenterId);
    	return deliveryBoys;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryBoyDTO> getAvailableDeliveryBoys() {
    	logger.info("Fetching available delivery boys");

        List<DeliveryBoyDTO> availableDeliveryBoys = deliveryBoyDaoService.findAvailableDeliveryBoys()
                .stream()
                .map(DeliveryBoyMapper::toDto)
                .toList();

    	logger.info("Fetched {} available delivery boys", availableDeliveryBoys.size());
    	return availableDeliveryBoys;
    }

    // TODO: get available delivery boys to a service center

}
