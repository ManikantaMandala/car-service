package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServiceTypeDTO;
import com.hcl.carservicing.carservice.service.ServiceTypeService;
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

class ServiceTypeControllerTest {

	@Mock
	private ServiceTypeService serviceTypeService;

	@InjectMocks
	private ServiceTypeController serviceTypeController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateServiceType_ValidInput() {
		// Arrange
		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setServiceName("Oil Change");
		serviceTypeDTO.setDescription("Regular oil change service");

		// Act
		ResponseEntity<String> responseEntity = serviceTypeController.create(serviceTypeDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service type created successfully", responseEntity.getBody());

		verify(serviceTypeService, times(1)).createServiceType(serviceTypeDTO);
	}

	@Test
	void testCreateServiceType_EmptyName() {
		// Arrange
		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setServiceName(""); // Empty name, assuming validation in DTO or Service
		serviceTypeDTO.setDescription("Regular service");

		// Act
		ResponseEntity<String> responseEntity = serviceTypeController.create(serviceTypeDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // Adjust as necessary for your validation
																			// logic
		assertEquals("Service type created successfully", responseEntity.getBody());

		verify(serviceTypeService, times(1)).createServiceType(serviceTypeDTO);
	}

	@Test

	void testCreateServiceType_NullDescription() {
		// Arrange
		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setServiceName("Service");
		serviceTypeDTO.setDescription(null); // Null description

		// Act
		ResponseEntity<String> responseEntity = serviceTypeController.create(serviceTypeDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // Adjust as necessary
		assertEquals("Service type created successfully", responseEntity.getBody());

		verify(serviceTypeService, times(1)).createServiceType(serviceTypeDTO);
	}

	@Test

	void testUpdateServiceType_ValidInput() {
		// Arrange
		Long id = 1L;
		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setServiceName("Oil Change");
		serviceTypeDTO.setDescription("Regular oil change service");

		// Act
		ResponseEntity<String> responseEntity = serviceTypeController.update(id, serviceTypeDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service type updated successfully", responseEntity.getBody());

		verify(serviceTypeService, times(1)).updateServiceType(id, serviceTypeDTO);
	}

	@Test

	void testUpdateServiceType_EmptyName() {
		// Arrange
		Long id = 1L;

		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setServiceName("");
		serviceTypeDTO.setDescription("Regular service");

		// Act
		ResponseEntity<String> responseEntity = serviceTypeController.update(id, serviceTypeDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Adjust as necessary
		assertEquals("Service type updated successfully", responseEntity.getBody());

		verify(serviceTypeService, times(1)).updateServiceType(id, serviceTypeDTO);
	}

	@Test

	void testUpdateServiceType_NullDescription() {
		// Arrange
		Long id = 1L;
		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setServiceName("Service");
		serviceTypeDTO.setDescription(null);

		// Act
		ResponseEntity<String> responseEntity = serviceTypeController.update(id, serviceTypeDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Adjust as necessary
		assertEquals("Service type updated successfully", responseEntity.getBody());

		verify(serviceTypeService, times(1)).updateServiceType(id, serviceTypeDTO);
	}

	@Test
	void testGetServiceTypeById_ExistingId() {
		// Arrange
		Long id = 1L;
		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setId(id);
		serviceTypeDTO.setServiceName("Oil Change");
		serviceTypeDTO.setDescription("Regular oil change service");

		when(serviceTypeService.getServiceTypeById(id)).thenReturn(serviceTypeDTO);

		// Act
		ResponseEntity<ServiceTypeDTO> responseEntity = serviceTypeController.getById(id);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(serviceTypeDTO, responseEntity.getBody());

		verify(serviceTypeService, times(1)).getServiceTypeById(id);
	}

	@Test

	void testGetServiceTypeById_NonExistingId() {
		// Arrange
		Long id = 100L; // A non-existing ID

		when(serviceTypeService.getServiceTypeById(id)).thenReturn(null); // Or throw an exception, depending on your
																			// service implementation
		// Act
		ResponseEntity<ServiceTypeDTO> responseEntity = serviceTypeController.getById(id);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Adjust this based on how your service handles
																		// non-existent IDs. Could be OK with a null
																		// body, or NOT_FOUND
		assertEquals(null, responseEntity.getBody());

		verify(serviceTypeService, times(1)).getServiceTypeById(id);

	}

	@Test

	void testGetServiceTypeById_MaximumIdValue() {
		// Arrange
		Long id = Long.MAX_VALUE;
		ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO();

		serviceTypeDTO.setId(id);
		serviceTypeDTO.setServiceName("Extreme Service");
		serviceTypeDTO.setDescription("Last service");
		when(serviceTypeService.getServiceTypeById(id)).thenReturn(serviceTypeDTO);

		// Act

		ResponseEntity<ServiceTypeDTO> responseEntity = serviceTypeController.getById(id);

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(serviceTypeDTO, responseEntity.getBody());

		verify(serviceTypeService, times(1)).getServiceTypeById(id);

	}

	@Test

	void testGetAllServiceTypes_MultipleServices() {

		// Arrange

		List<ServiceTypeDTO> serviceTypeList = new ArrayList<>();

		ServiceTypeDTO serviceTypeDTO1 = new ServiceTypeDTO();

		serviceTypeDTO1.setId(1L);

		serviceTypeDTO1.setServiceName("Oil Change");

		serviceTypeDTO1.setDescription("Regular oil change service");

		ServiceTypeDTO serviceTypeDTO2 = new ServiceTypeDTO();

		serviceTypeDTO2.setId(2L);

		serviceTypeDTO2.setServiceName("Tire Rotation");

		serviceTypeDTO2.setDescription("Rotate tires");

		serviceTypeList.add(serviceTypeDTO1);

		serviceTypeList.add(serviceTypeDTO2);

		when(serviceTypeService.getAllServiceTypes()).thenReturn(serviceTypeList);

		// Act

		ResponseEntity<List<ServiceTypeDTO>> responseEntity = serviceTypeController.getAll();

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(serviceTypeList, responseEntity.getBody());

		verify(serviceTypeService, times(1)).getAllServiceTypes();

	}

	@Test

	void testGetAllServiceTypes_EmptyList() {

		// Arrange

		List<ServiceTypeDTO> serviceTypeList = new ArrayList<>(); // Empty list

		when(serviceTypeService.getAllServiceTypes()).thenReturn(serviceTypeList);

		// Act

		ResponseEntity<List<ServiceTypeDTO>> responseEntity = serviceTypeController.getAll();

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(serviceTypeList, responseEntity.getBody()); // Ensure it returns the empty list

		verify(serviceTypeService, times(1)).getAllServiceTypes();

	}

	@Test

	void testGetAllServiceTypes_SingleService() {

		// Arrange

		List<ServiceTypeDTO> serviceTypeList = new ArrayList<>();

		ServiceTypeDTO serviceTypeDTO1 = new ServiceTypeDTO();

		serviceTypeDTO1.setId(1L);

		serviceTypeDTO1.setServiceName("Oil Change");

		serviceTypeDTO1.setDescription("Regular oil change service");

		serviceTypeList.add(serviceTypeDTO1);

		when(serviceTypeService.getAllServiceTypes()).thenReturn(serviceTypeList);

		// Act

		ResponseEntity<List<ServiceTypeDTO>> responseEntity = serviceTypeController.getAll();

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(serviceTypeList, responseEntity.getBody());

		verify(serviceTypeService, times(1)).getAllServiceTypes();

	}

}
