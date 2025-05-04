package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServiceRequestDTO;
import com.hcl.carservicing.carservice.service.ServiceRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRequestControllerTest {

	@Mock
	private ServiceRequestService service;

	@InjectMocks
	private UserRequestController userRequestController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateRequest_Success() {
		// Arrange
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername("testUser");
		requestDTO.setServiceCenterId(1L);

		// Act
		ResponseEntity<String> responseEntity = userRequestController.createRequest(requestDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service request created successfully", responseEntity.getBody());

		verify(service, times(1)).createRequest(requestDTO);
	}

	@Test
	void testCreateRequest_InvalidInput() {
		// Arrange
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername(null); // Invalid input, userName is @NotNull
		requestDTO.setServiceCenterId(1L);

		// Act
		ResponseEntity<String> responseEntity = userRequestController.createRequest(requestDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service request created successfully", responseEntity.getBody());

		verify(service, times(1)).createRequest(requestDTO);
	}

	@Test
	void testCreateRequest_WithZeroServiceId() {
		// Arrange
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername("testUser");
		requestDTO.setServiceCenterId(0L);

		// Act
		ResponseEntity<String> responseEntity = userRequestController.createRequest(requestDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service request created successfully", responseEntity.getBody());

		verify(service, times(1)).createRequest(requestDTO);
	}

	@Test
	void testGetRequestsByUser_ExistingUser() {
		// Arrange
		String username = "testUser";
		List<ServiceRequestDTO> requestList = new ArrayList<>();
		ServiceRequestDTO requestDTO = new ServiceRequestDTO();

		requestDTO.setUsername(username);
		requestDTO.setServiceCenterId(1L);
		requestDTO.setServiceId(1L);
		requestList.add(requestDTO);
		
		when(service.getRequestsByUser(username)).thenReturn(requestList);

		// Act
		ResponseEntity<List<ServiceRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(requestList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);
	}

	@Test
	void testGetRequestsByUser_NoRequests() {
		// Arrange
		String username = "testUser";
		List<ServiceRequestDTO> emptyList = new ArrayList<>();

		when(service.getRequestsByUser(username)).thenReturn(emptyList);

		// Act
		ResponseEntity<List<ServiceRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(emptyList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);
	}

	@Test
	void testGetRequestsByUser_DifferentUser() {
		// Arrange
		String username = "anotherUser";
		List<ServiceRequestDTO> emptyList = new ArrayList<>();

		when(service.getRequestsByUser(username)).thenReturn(emptyList);

		// Act
		ResponseEntity<List<ServiceRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(emptyList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);
	}
}
