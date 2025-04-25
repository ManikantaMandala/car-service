package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.controller.ServiceCenterController;
import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
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

class ServiceCenterControllerTest {

	@Mock
	private ServiceCenterService serviceCenterService;

	@InjectMocks
	private ServiceCenterController serviceCenterController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddServiceCenter_Success() {
		// Arrange
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("Test Center");
		serviceCenterDTO.setLocation("Test Location");

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.addServiceCenter(serviceCenterDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service center created successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).createServiceCenter(serviceCenterDTO);
	}

	@Test
	void testAddServiceCenter_InvalidInput() {
		// Arrange
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName(null); // Invalid input

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.addServiceCenter(serviceCenterDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service center created successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).createServiceCenter(serviceCenterDTO);
	}

	@Test
	void testAddServiceCenter_EmptyName() {
		// Arrange
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("");
		serviceCenterDTO.setLocation("Test Location");

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.addServiceCenter(serviceCenterDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service center created successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).createServiceCenter(serviceCenterDTO);
	}

	@Test
	void testUpdateServiceCenter_Success() {
		// Arrange
		Long id = 1L;
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("Updated Center");
		serviceCenterDTO.setLocation("Updated Location");

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.updateServiceCenter(id, serviceCenterDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateServiceCenter(id, serviceCenterDTO);
	}

	@Test
	void testUpdateServiceCenter_InvalidInput() {
		// Arrange
		Long id = 1L;
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName(null); // Invalid

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.updateServiceCenter(id, serviceCenterDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateServiceCenter(id, serviceCenterDTO);
	}

	@Test
	void testUpdateServiceCenter_EmptyName() {
		// Arrange
		Long id = 1L;
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("");
		serviceCenterDTO.setLocation("Location");

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.updateServiceCenter(id, serviceCenterDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateServiceCenter(id, serviceCenterDTO);
	}

	@Test
	void testGetAllServiceCenters_MultipleCenters() {
		// Arrange
		List<ServiceCenterDTO> centers = new ArrayList<>();
		ServiceCenterDTO center1 = new ServiceCenterDTO();
		center1.setId(1L);
		center1.setName("Center 1");
		center1.setLocation("Location 1");
		centers.add(center1);
		ServiceCenterDTO center2 = new ServiceCenterDTO();
		center2.setId(2L);
		center2.setName("Center 2");
		center2.setLocation("Location 2");
		centers.add(center2);

		when(serviceCenterService.getAllServiceCenters()).thenReturn(centers);

		// Act
		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController.getAllServiceCenters();

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(centers, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAllServiceCenters();
	}

	@Test
	void testGetAllServiceCenters_NoCenters() {
		// Arrange
		List<ServiceCenterDTO> centers = new ArrayList<>();
		when(serviceCenterService.getAllServiceCenters()).thenReturn(centers);

		// Act
		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController.getAllServiceCenters();

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(centers, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAllServiceCenters();
	}

	@Test
	void testGetAllServiceCenters_SingleCenter() {
		// Arrange
		List<ServiceCenterDTO> centers = new ArrayList<>();
		ServiceCenterDTO center1 = new ServiceCenterDTO();
		center1.setId(1L);
		center1.setName("Center 1");
		center1.setLocation("Location 1");
		centers.add(center1);

		when(serviceCenterService.getAllServiceCenters()).thenReturn(centers);

		// Act
		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController.getAllServiceCenters();

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(centers, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAllServiceCenters();
	}

	@Test
	void testGetAvailableServiceCenters_AvailableCenters() {
		// Arrange
		List<ServiceCenterDTO> availableCenters = new ArrayList<>();
		ServiceCenterDTO center1 = new ServiceCenterDTO();
		center1.setId(1L);
		center1.setName("Center 1");
		center1.setAvailable(true);
		availableCenters.add(center1);

		when(serviceCenterService.getAvailableServiceCenters(true)).thenReturn(availableCenters);

		// Act
		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController
				.getAvailableServiceCenters(true);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(availableCenters, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAvailableServiceCenters(true);
	}

	@Test
	void testGetAvailableServiceCenters_UnavailableCenters() {
		// Arrange
		List<ServiceCenterDTO> unavailableCenters = new ArrayList<>();
		ServiceCenterDTO center2 = new ServiceCenterDTO();
		center2.setId(2L);
		center2.setName("Center 2");
		center2.setAvailable(false);
		unavailableCenters.add(center2);

		when(serviceCenterService.getAvailableServiceCenters(false)).thenReturn(unavailableCenters);

		// Act
		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController
				.getAvailableServiceCenters(false);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(unavailableCenters, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAvailableServiceCenters(false);
	}

	
	@Test
	void testGetServiceCenterById_ExistingCenter() {
		// Arrange
		Long id = 1L;
		ServiceCenterDTO center = new ServiceCenterDTO();
		center.setId(id);
		center.setName("Test Center");
		center.setLocation("Test Location");

		when(serviceCenterService.getServiceCenterById(id)).thenReturn(center);

		// Act
		ResponseEntity<ServiceCenterDTO> responseEntity = serviceCenterController.getServiceCenterById(id);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(center, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getServiceCenterById(id);
	}

	@Test
	void testGetServiceCenterById_NonExistingCenter() {
		// Arrange
		Long id = 100L;
		when(serviceCenterService.getServiceCenterById(id)).thenReturn(null);

		// Act
		ResponseEntity<ServiceCenterDTO> responseEntity = serviceCenterController.getServiceCenterById(id);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getServiceCenterById(id);
	}

	@Test
	void testGetServiceCenterById_AnotherCenter() {
		// Arrange
		Long id = 2L;
		ServiceCenterDTO center = new ServiceCenterDTO();
		center.setId(id);
		center.setName("Another Center");
		center.setLocation("Another Location");

		when(serviceCenterService.getServiceCenterById(id)).thenReturn(center);

		// Act
		ResponseEntity<ServiceCenterDTO> responseEntity = serviceCenterController.getServiceCenterById(id);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(center, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getServiceCenterById(id);
	}

	@Test
	void testUpdateStatusOfServiceCenter_Success() {
		// Arrange
		Long id = 1L;
		Boolean status = true;

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.updateStatusOfServiceCenter(id, status);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center status updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateStatusOfServiceCenter(id, status);
	}

	@Test
	void testUpdateStatusOfServiceCenter_Failure() {
		// Arrange
		Long id = 1L;
		Boolean status = false;

		// Act
		ResponseEntity<String> responseEntity = serviceCenterController.updateStatusOfServiceCenter(id, status);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center status updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateStatusOfServiceCenter(id, status);
	}
}