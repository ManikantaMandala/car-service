package com.hcl.carservicing.carservice.dao.service.impl;

import com.hcl.carservicing.carservice.dao.service.AppUserDaoService;
import com.hcl.carservicing.carservice.exception.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.model.AppUser;
import com.hcl.carservicing.carservice.model.VehicleDetails;
import com.hcl.carservicing.carservice.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserDaoServiceImpl implements AppUserDaoService {
    private static final Logger logger = LoggerFactory.getLogger(AppUserDaoServiceImpl.class);

    private final AppUserRepository appUserRepository;

    public AppUserDaoServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser findByUsername(String username) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isEmpty()) {
            logger.error("User not found with username: {}", username);
            throw new ElementNotFoundException("User not found with username: " + username);
        }

        return appUserOptional.get();
    }

    @Override
    public void throwIfUsernameExists(String username) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isPresent()) {
            logger.error("User found with username: {}", username);
            throw new ElementAlreadyExistException("User found with username: " + username);
        }
    }

    @Override
    public void throwIfContactExists(String contactNumber) {
        Optional<AppUser> appUserOptional = appUserRepository.findByContactNumber(contactNumber);
        if (appUserOptional.isPresent()) {
            logger.error("User found with contact number: {}", contactNumber);
            throw new ElementAlreadyExistException("User found with contact number: " + contactNumber);
        }
    }

    public void addVehicleDetailsToUser(String username, VehicleDetails vehicleDetails) {
        AppUser appUser = findByUsername(username);

        List<VehicleDetails> vehicleDetailsList = appUser.getVehicleDetails();
        vehicleDetailsList.add(vehicleDetails);

        appUser.setVehicleDetails(vehicleDetailsList);

        save(appUser);
    }

    @Override
    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }
}
