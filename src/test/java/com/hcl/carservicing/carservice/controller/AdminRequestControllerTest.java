package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.service.ServiceCenterService;
import com.hcl.carservicing.carservice.service.ServicingRequestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class AdminRequestControllerTest {

    @Mock
    ServiceCenterService serviceCenterService;

    @Mock
    ServicingRequestService servicingRequestService;

    @InjectMocks
    AdminRequestController adminRequestController;

    @Test
    void updateStatusWithOkStatus() {
    }

    @Test
    void updateStatusWhichThrowsException() {

    }

    @Test
    void getAll() {
    }

    @Test
    void updateStatusOfServiceCenter() {
    }
}
