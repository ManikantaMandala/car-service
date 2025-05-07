package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.enums.RequestStatus;
import com.hcl.carservicing.carservice.exception.ElementNotFoundException;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
import com.hcl.carservicing.carservice.service.ServiceRequestService;
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
	ServiceRequestService serviceRequestService;

	@InjectMocks
	AdminRequestController adminRequestController;

	private String reason;

	@Test
	void updateStatusWithOkStatus() {
		Long requestId = 1L;
		RequestStatus status = RequestStatus.ACCEPTED;
		Long deliveryBoyId = 1L;

		Long serviceId = 1L;
		Long serviceCenterId = 1L;
		String username = "test_username";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(2);

		reason = "reason";

		ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO();
		serviceRequestDTO.setId(requestId);
		serviceRequestDTO.setStatus(status);
		serviceRequestDTO.setStartDate(startDate);
		serviceRequestDTO.setEndDate(endDate);
		serviceRequestDTO.setUsername(username);
		serviceRequestDTO.setServiceId(serviceId);
		serviceRequestDTO.setServiceCenterId(serviceCenterId);
		serviceRequestDTO.setDeliveryBoyId(deliveryBoyId);

		when(serviceRequestService.updateRequestStatus(requestId, status, deliveryBoyId, reason))
				.thenReturn(serviceRequestDTO);

		ResponseEntity<String> result = adminRequestController.updateStatus(requestId, status, deliveryBoyId, reason);

		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Servicing request status updated successfully", result.getBody());
		verify(serviceRequestService, times(1))
				.updateRequestStatus(requestId, status, deliveryBoyId, reason);
	}

	@Test
	void updateStatusWhichThrowsElementNotFoundExceptionForServicingRequest() {
		Long requestId = 100L;
		RequestStatus status = RequestStatus.ACCEPTED;
		Long deliveryBoyId = 1L;
		reason = "reason";

		when(serviceRequestService.updateRequestStatus(requestId, status, deliveryBoyId, reason))
				.thenThrow(new ElementNotFoundException("Request not found: " + requestId));

		ElementNotFoundException thrownException = assertThrows(ElementNotFoundException.class,
				() -> adminRequestController.updateStatus(requestId, status, deliveryBoyId, reason));

		assertEquals("Request not found: " + requestId, thrownException.getMessage());
		verify(serviceRequestService, times(1))
				.updateRequestStatus(requestId, status, deliveryBoyId, reason);
	}

	@Test
	void updateStatusWhichThrowsElementNotFoundExceptionForDeliveryBoy() {
		Long requestId = 100L;
		RequestStatus status = RequestStatus.ACCEPTED;
		Long deliveryBoyId = 1L;
		reason = "reason";

		when(serviceRequestService.updateRequestStatus(requestId, status, deliveryBoyId, reason))
				.thenThrow(new ElementNotFoundException("Delivery boy not found: " + deliveryBoyId));

		ElementNotFoundException thrownException = assertThrows(ElementNotFoundException.class,
				() -> adminRequestController.updateStatus(requestId, status, deliveryBoyId, reason));

		assertEquals("Delivery boy not found: " + deliveryBoyId, thrownException.getMessage());
		verify(serviceRequestService, times(1))
				.updateRequestStatus(requestId, status, deliveryBoyId, reason);
	}

	@Test
	void getAllWithOkStatus() {
		List<ServiceRequestDTO> resultBody = new ArrayList<>();

		when(serviceRequestService.getAllRequests()).thenReturn(resultBody);

		ResponseEntity<List<ServiceRequestDTO>> result = adminRequestController.getAll();

		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(resultBody, result.getBody());
		verify(serviceRequestService, times(1)).getAllRequests();
	}

	@Test
	void updateStatusOfServiceCenter() {
		Long serviceCenterId = 1L;
		Boolean status = false;

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

		doThrow(new ElementNotFoundException("Service center not found: " + serviceCenterId)).when(serviceCenterService)
				.updateStatusOfServiceCenter(serviceCenterId, status);

		ElementNotFoundException thrownException = assertThrows(ElementNotFoundException.class,
				() -> adminRequestController.updateStatusOfServiceCenter(serviceCenterId, status));

		assertEquals("Service center not found: " + serviceCenterId, thrownException.getMessage());
		verify(serviceCenterService, times(1))
				.updateStatusOfServiceCenter(serviceCenterId, status);
	}
}
