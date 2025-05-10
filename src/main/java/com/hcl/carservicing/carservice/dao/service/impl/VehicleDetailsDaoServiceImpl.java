package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.dao.service.VehicleDetailsDaoService;
import com.hcl.carservicing.carservice.model.VehicleDetails;
import com.hcl.carservicing.carservice.repository.VehicleDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VehicleDetailsDaoServiceImpl implements VehicleDetailsDaoService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleDetailsDaoServiceImpl.class);
    private final VehicleDetailsRepository vehicleDetailsRepository;

    public VehicleDetailsDaoServiceImpl(VehicleDetailsRepository vehicleDetailsRepository) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
    }

    @Override
    public VehicleDetails save(VehicleDetails vehicleDetails) {
        logger.info("saving vehicle details");
        return vehicleDetailsRepository.save(vehicleDetails);
    }
}
