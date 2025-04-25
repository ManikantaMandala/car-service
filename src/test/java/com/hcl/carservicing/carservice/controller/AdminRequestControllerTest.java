package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServicingRequestDTO;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
import com.hcl.carservicing.carservice.service.ServicingRequestService;
import com.hcl.carservicing.carservice.service.impl.ServiceCenterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminRequestControllerTest {

    @Mock
    ServiceCenterService serviceCenterService;

    @Mock
    ServicingRequestService servicingRequestService;

    @InjectMocks
    AdminRequestController adminRequestController;

    @Test
    void updateStatusWithOkStatus() {
        Long requestId = 1L;
        String status = "ACCEPTED";
        Long deliveryBoyId = 1L;

        Long serviceId = 1L;
        Long serviceCenterId = 1L;
        String username = "test_username";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);

        ServicingRequestDTO servicingRequestDTO = new ServicingRequestDTO();
        servicingRequestDTO.setId(requestId);
        servicingRequestDTO.setStatus(RequestStatus.valueOf(status));
        servicingRequestDTO.setStartDate(startDate);
        servicingRequestDTO.setEndDate(endDate);
        servicingRequestDTO.setUsername(username);
        servicingRequestDTO.setServiceId(serviceId);
        servicingRequestDTO.setServiceCenterId(serviceCenterId);
        servicingRequestDTO.setDeliveryBoyId(deliveryBoyId);

        when(servicingRequestService.updateRequestStatus(requestId, status, deliveryBoyId))
                .thenReturn(servicingRequestDTO);

        ResponseEntity<String> result = adminRequestController.updateStatus(requestId, status, deliveryBoyId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Servicing request status updated successfully", result.getBody());
        verify(servicingRequestService, times(1))
                .updateRequestStatus(requestId, status, deliveryBoyId);
    }

    @Test
    void updateStatusWhichThrowsElementNotFoundExceptionForServicingRequest() {
        Long requestId = 100L;
        String status = "ACCEPTED";
        Long deliveryBoyId = 1L;

        when(servicingRequestService.updateRequestStatus(requestId, status, deliveryBoyId))
                .thenThrow(new ElementNotFoundException("Request not found: " + requestId));


        ElementNotFoundException thrownException = assertThrows(ElementNotFoundException.class, () -> adminRequestController.updateStatus(requestId, status, deliveryBoyId));

        assertEquals("Request not found: " + requestId, thrownException.getMessage());
        verify(servicingRequestService, times(1))
                .updateRequestStatus(requestId, status, deliveryBoyId);
    }

    @Test
    void updateStatusWhichThrowsElementNotFoundExceptionForDeliveryBoy() {
        Long requestId = 100L;
        String status = "ACCEPTED";
        Long deliveryBoyId = 1L;

        when(servicingRequestService.updateRequestStatus(requestId, status, deliveryBoyId))
                .thenThrow(new ElementNotFoundException("Delivery boy not found: " + deliveryBoyId));

        ElementNotFoundException thrownException = assertThrows(ElementNotFoundException.class,
                () -> adminRequestController.updateStatus(requestId, status, deliveryBoyId));

        assertEquals("Delivery boy not found: " + deliveryBoyId, thrownException.getMessage());
        verify(servicingRequestService, times(1))
                .updateRequestStatus(requestId, status, deliveryBoyId);
    }

    @Test
    void getAllWithOkStatus() {
        List<ServicingRequestDTO> resultBody = new ArrayList<>();

        when(servicingRequestService.getAllRequests()).thenReturn(resultBody);

        ResponseEntity<List<ServicingRequestDTO>> result = adminRequestController.getAll();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resultBody, result.getBody());
        verify(servicingRequestService, times(1)).getAllRequests();
    }

    @Test
    void updateStatusOfServiceCenter() {
        Long serviceCenterId = 1L;
        Boolean status = false;

//        when(serviceCenterService.updateStatusOfServiceCenter(serviceCenterId, status)).thenReturn(void);
        doNothing().when(serviceCenterService).updateStatusOfServiceCenter(serviceCenterId, status);

        ResponseEntity<String> result = adminRequestController.updateStatusOfServiceCenter(serviceCenterId, status);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Service center status updated successfully", result.getBody());
    }

    @Test
    void updateStatusOfServiceCenterWhichThrowsElementNotFoundException() {
        Long serviceCenterId = 1L;
        Boolean status = false;

        doThrow(new ElementNotFoundException("Service center not found: " + serviceCenterId)).when(serviceCenterService).updateStatusOfServiceCenter(serviceCenterId, status);


        ElementNotFoundException thrownException = assertThrows(ElementNotFoundException.class,
                () -> adminRequestController.updateStatusOfServiceCenter(serviceCenterId, status));

        assertEquals("Service center not found: " + serviceCenterId, thrownException.getMessage());
        verify(serviceCenterService, times(1))
                .updateStatusOfServiceCenter(serviceCenterId, status);
    }
}
