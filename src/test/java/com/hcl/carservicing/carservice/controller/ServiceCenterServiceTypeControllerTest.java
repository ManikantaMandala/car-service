package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterServiceTypeService;
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

class ServiceCenterServiceTypeControllerTest {

	@Mock
	private ServiceCenterServiceTypeService service;

	@InjectMocks
	private ServiceCenterServiceTypeController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddServiceTypeToCenter_Success() {
		// Arrange
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(1L);
		dto.setServiceTypeId(2L);

		// Act
		ResponseEntity<String> responseEntity = controller.add(dto);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service type assigned to service center successfully", responseEntity.getBody());

		verify(service, times(1)).addServiceTypeToCenter(dto);
	}

	@Test
	void testAddServiceTypeToCenter_InvalidInput() {
		// Arrange
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();
		dto.setServiceCenterId(null); // Invalid input

		// Act
		ResponseEntity<String> responseEntity = controller.add(dto);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service type assigned to service center successfully", responseEntity.getBody());

		verify(service, times(1)).addServiceTypeToCenter(dto);
	}

	@Test
	void testUpdateServiceCenterServiceType_Success() {
		// Arrange
		Long id = 10L;
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(3L);
		dto.setServiceTypeId(4L);

		// Act
		ResponseEntity<String> responseEntity = controller.update(id, dto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center service mapping updated successfully", responseEntity.getBody());

		verify(service, times(1)).updateServiceCenterServiceType(id, dto);
	}

	@Test
	void testUpdateServiceCenterServiceType_InvalidInput() {
		// Arrange
		Long id = 10L;
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(3L);

		// Act
		ResponseEntity<String> responseEntity = controller.update(id, dto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center service mapping updated successfully", responseEntity.getBody());

		verify(service, times(1)).updateServiceCenterServiceType(id, dto);
	}

	@Test
	void testUpdateServiceCenterServiceType_MaxIdValue() {
		// Arrange
		Long id = Long.MAX_VALUE;
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(3L);
		dto.setServiceTypeId(4L);

		// Act
		ResponseEntity<String> responseEntity = controller.update(id, dto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center service mapping updated successfully", responseEntity.getBody());

		verify(service, times(1)).updateServiceCenterServiceType(id, dto);
	}

	@Test
	void testGetByServiceCenter_ExistingCenter() {
		// Arrange
		Long centerId = 1L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();
		ServiceCenterServiceTypeDTO dto1 = new ServiceCenterServiceTypeDTO();

		dto1.setServiceCenterId(centerId);
		dto1.setServiceTypeId(101L);

		expectedList.add(dto1);

		when(service.getByServiceCenter(centerId)).thenReturn(expectedList);

		// Act
		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byCenter(centerId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceCenter(centerId);
	}

	@Test
	void testGetByServiceCenter_NoServices() {
		// Arrange
		Long centerId = 2L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();

		when(service.getByServiceCenter(centerId)).thenReturn(expectedList);

		// Act
		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byCenter(centerId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceCenter(centerId);
	}

	@Test
	void testGetByServiceCenter_InvalidCenter() {
		// Arrange
		Long centerId = -1L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();

		when(service.getByServiceCenter(centerId)).thenReturn(expectedList);

		// Act
		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byCenter(centerId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceCenter(centerId);
	}

	@Test
	void testGetByServiceType_ExistingServiceType() {
		// Arrange
		Long serviceTypeId = 2L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();
		ServiceCenterServiceTypeDTO dto1 = new ServiceCenterServiceTypeDTO();

		dto1.setServiceTypeId(serviceTypeId);
		dto1.setServiceCenterId(201L);

		expectedList.add(dto1);

		when(service.getByServiceType(serviceTypeId)).thenReturn(expectedList);

		// Act
		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byService(serviceTypeId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceType(serviceTypeId);
	}

	@Test
	void testGetByServiceType_NoCenters() {
		// Arrange
		Long serviceTypeId = 3L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();

		when(service.getByServiceType(serviceTypeId)).thenReturn(expectedList);

		// Act
		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byService(serviceTypeId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceType(serviceTypeId);
	}

	@Test
	void testGetByServiceType_InvalidServiceType() {
		// Arrange
		Long serviceTypeId = -2L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();
		when(service.getByServiceType(serviceTypeId)).thenReturn(expectedList);

		// Act
		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byService(serviceTypeId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());
		verify(service, times(1)).getByServiceType(serviceTypeId);
	}
}
