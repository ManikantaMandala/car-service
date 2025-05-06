package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.service.ServiceRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRequestControllerTest {

	@Mock
	private ServiceRequestService service;

	@InjectMocks
	private UserRequestController userRequestController;

	@Test
	void testCreateRequest_Success() {
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername("testUser");
		requestDTO.setServiceCenterId(1L);

		ResponseEntity<String> responseEntity = userRequestController.createRequest(requestDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service request created successfully", responseEntity.getBody());

		verify(service, times(1)).createRequest(requestDTO);
	}

	@Test
	void testCreateRequest_InvalidInput() {
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername(null);
		requestDTO.setServiceCenterId(1L);

		ResponseEntity<String> responseEntity = userRequestController.createRequest(requestDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service request created successfully", responseEntity.getBody());

		verify(service, times(1)).createRequest(requestDTO);
	}

	@Test
	void testCreateRequest_WithZeroServiceId() {
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername("testUser");
		requestDTO.setServiceCenterId(0L);

		ResponseEntity<String> responseEntity = userRequestController.createRequest(requestDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service request created successfully", responseEntity.getBody());

		verify(service, times(1)).createRequest(requestDTO);
	}

	@Test
	void testGetRequestsByUser_ExistingUser() {
		String username = "testUser";
		List<ServiceRequestDTO> requestList = new ArrayList<>();
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername(username);
		requestDTO.setServiceCenterId(1L);
		requestDTO.setServiceId(1L);
		requestList.add(requestDTO);
		
		when(service.getRequestsByUser(username)).thenReturn(requestList);

		ResponseEntity<List<ServiceRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(requestList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);
	}

	@Test
	void testGetRequestsByUser_NoRequests() {
		String username = "testUser";
		List<ServiceRequestDTO> emptyList = new ArrayList<>();

		when(service.getRequestsByUser(username)).thenReturn(emptyList);

		ResponseEntity<List<ServiceRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(emptyList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);
	}

	@Test
	void testGetRequestsByUser_DifferentUser() {
		String username = "anotherUser";
		List<ServiceRequestDTO> emptyList = new ArrayList<>();

		when(service.getRequestsByUser(username)).thenReturn(emptyList);

		ResponseEntity<List<ServiceRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(emptyList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);
	}
}
