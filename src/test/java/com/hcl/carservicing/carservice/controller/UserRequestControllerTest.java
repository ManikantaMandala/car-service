package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServicingRequestDTO;

import com.hcl.carservicing.carservice.service.ServicingRequestService;

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

	private ServicingRequestService service;

	@InjectMocks

	private UserRequestController userRequestController;

	@BeforeEach

	void setUp() {

		MockitoAnnotations.openMocks(this);

	}

	@Test

	void testCreateRequest_Success() {

		// Arrange

		ServicingRequestDTO requestDTO = new ServicingRequestDTO();

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

		ServicingRequestDTO requestDTO = new ServicingRequestDTO();

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

		ServicingRequestDTO requestDTO = new ServicingRequestDTO();

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

		List<ServicingRequestDTO> requestList = new ArrayList<>();

		ServicingRequestDTO requestDTO1 = new ServicingRequestDTO();

		requestDTO1.setUsername(username);

		requestDTO1.setServiceCenterId(1L);

		requestDTO1.setRequestId(101L);

		requestList.add(requestDTO1);

		when(service.getRequestsByUser(username)).thenReturn(requestList);

		// Act

		ResponseEntity<List<ServicingRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(requestList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);

	}

	@Test

	void testGetRequestsByUser_NoRequests() {

		// Arrange

		String username = "testUser";

		List<ServicingRequestDTO> emptyList = new ArrayList<>();

		when(service.getRequestsByUser(username)).thenReturn(emptyList);

		// Act

		ResponseEntity<List<ServicingRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(emptyList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);

	}

	@Test

	void testGetRequestsByUser_DifferentUser() {

		// Arrange

		String username = "anotherUser";

		List<ServicingRequestDTO> emptyList = new ArrayList<>();

		when(service.getRequestsByUser(username)).thenReturn(emptyList);

		// Act

		ResponseEntity<List<ServicingRequestDTO>> responseEntity = userRequestController.getRequestsByUser(username);

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(emptyList, responseEntity.getBody());

		verify(service, times(1)).getRequestsByUser(username);

	}

}
