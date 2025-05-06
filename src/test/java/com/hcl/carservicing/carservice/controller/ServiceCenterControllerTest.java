package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServiceCenterDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterService;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCenterControllerTest {

	@Mock
	private ServiceCenterService serviceCenterService;

	@InjectMocks
	private ServiceCenterController serviceCenterController;

	@Test
	void testAddServiceCenter_Success() {
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("Test Center");
		serviceCenterDTO.setAddress("test_address");

		ResponseEntity<String> responseEntity = serviceCenterController.addServiceCenter(serviceCenterDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service center created successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).createServiceCenter(serviceCenterDTO);
	}

	@Test
	void testAddServiceCenter_InvalidInput() {
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName(null);

		ResponseEntity<String> responseEntity = serviceCenterController.addServiceCenter(serviceCenterDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service center created successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).createServiceCenter(serviceCenterDTO);
	}

	@Test
	void testAddServiceCenter_EmptyName() {
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("");
		serviceCenterDTO.setAddress("test_address");

		ResponseEntity<String> responseEntity = serviceCenterController.addServiceCenter(serviceCenterDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service center created successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).createServiceCenter(serviceCenterDTO);
	}

	@Test
	void testUpdateServiceCenter_Success() {
		Long id = 1L;
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("Updated Center");
		serviceCenterDTO.setAddress("test_address");

		ResponseEntity<String> responseEntity = serviceCenterController.updateServiceCenter(id, serviceCenterDTO);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateServiceCenter(id, serviceCenterDTO);
	}

	@Test
	void testUpdateServiceCenter_InvalidInput() {
		Long id = 1L;
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName(null);

		ResponseEntity<String> responseEntity = serviceCenterController.updateServiceCenter(id, serviceCenterDTO);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateServiceCenter(id, serviceCenterDTO);
	}

	@Test
	void testUpdateServiceCenter_EmptyName() {
		Long id = 1L;
		ServiceCenterDTO serviceCenterDTO = new ServiceCenterDTO();
		serviceCenterDTO.setName("");
		serviceCenterDTO.setAddress("test_address");

		ResponseEntity<String> responseEntity = serviceCenterController.updateServiceCenter(id, serviceCenterDTO);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateServiceCenter(id, serviceCenterDTO);
	}

	@Test
	void testGetAllServiceCenters_MultipleCenters() {
		List<ServiceCenterDTO> centers = new ArrayList<>();
		ServiceCenterDTO center1 = new ServiceCenterDTO();

		center1.setId(1L);
		center1.setName("Center 1");
		center1.setAddress("test_address_1");

		centers.add(center1);

		ServiceCenterDTO center2 = new ServiceCenterDTO();

		center2.setId(2L);
		center2.setName("Center 2");
		center1.setAddress("test_address_2");

		centers.add(center2);

		when(serviceCenterService.getAllServiceCenters()).thenReturn(centers);

		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController.getAllServiceCenters();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(centers, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAllServiceCenters();
	}

	@Test
	void testGetAllServiceCenters_NoCenters() {
		List<ServiceCenterDTO> centers = new ArrayList<>();
		when(serviceCenterService.getAllServiceCenters()).thenReturn(centers);

		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController.getAllServiceCenters();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(centers, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAllServiceCenters();
	}

	@Test
	void testGetAllServiceCenters_SingleCenter() {
		List<ServiceCenterDTO> centers = new ArrayList<>();
		ServiceCenterDTO center1 = new ServiceCenterDTO();

		center1.setId(1L);
		center1.setName("Center 1");
		center1.setAddress("test_address_1");

		centers.add(center1);

		when(serviceCenterService.getAllServiceCenters()).thenReturn(centers);

		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController.getAllServiceCenters();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(centers, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAllServiceCenters();
	}

	@Test
	void testGetAvailableServiceCenters_AvailableCenters() {
		List<ServiceCenterDTO> availableCenters = new ArrayList<>();
		ServiceCenterDTO center1 = new ServiceCenterDTO();

		center1.setId(1L);
		center1.setName("Center 1");
		center1.setAvailable(true);

		availableCenters.add(center1);

		when(serviceCenterService.getAvailableServiceCenters(true)).thenReturn(availableCenters);

		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController
				.getAvailableServiceCenters(true);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(availableCenters, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAvailableServiceCenters(true);
	}

	@Test
	void testGetAvailableServiceCenters_UnavailableCenters() {
		List<ServiceCenterDTO> unavailableCenters = new ArrayList<>();
		ServiceCenterDTO center2 = new ServiceCenterDTO();
		center2.setId(2L);
		center2.setName("Center 2");
		center2.setAvailable(false);
		unavailableCenters.add(center2);

		when(serviceCenterService.getAvailableServiceCenters(false)).thenReturn(unavailableCenters);

		ResponseEntity<List<ServiceCenterDTO>> responseEntity = serviceCenterController
				.getAvailableServiceCenters(false);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(unavailableCenters, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getAvailableServiceCenters(false);
	}

	@Test
	void testGetServiceCenterById_ExistingCenter() {
		Long id = 1L;
		ServiceCenterDTO center = new ServiceCenterDTO();
		center.setId(id);
		center.setName("Test Center");
		center.setAddress("Test Location");

		when(serviceCenterService.getServiceCenterById(id)).thenReturn(center);

		ResponseEntity<ServiceCenterDTO> responseEntity = serviceCenterController.getServiceCenterById(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(center, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getServiceCenterById(id);
	}

	@Test
	void testGetServiceCenterById_NonExistingCenter() {
		Long id = 100L;
		when(serviceCenterService.getServiceCenterById(id)).thenReturn(null);

		ResponseEntity<ServiceCenterDTO> responseEntity = serviceCenterController.getServiceCenterById(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
		verify(serviceCenterService, times(1)).getServiceCenterById(id);
	}

	@Test
	void testGetServiceCenterById_AnotherCenter() {
		Long id = 2L;
		ServiceCenterDTO center = new ServiceCenterDTO();
		center.setId(id);
		center.setName("Another Center");
		center.setAddress("Another Location");

		when(serviceCenterService.getServiceCenterById(id)).thenReturn(center);

		ResponseEntity<ServiceCenterDTO> responseEntity = serviceCenterController.getServiceCenterById(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(center, responseEntity.getBody());
		verify(serviceCenterService, times(1)).getServiceCenterById(id);
	}

	@Test
	void testUpdateStatusOfServiceCenter_Success() {
		Long id = 1L;
		Boolean status = true;

		ResponseEntity<String> responseEntity = serviceCenterController.updateStatusOfServiceCenter(id, status);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center status updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateStatusOfServiceCenter(id, status);
	}

	@Test
	void testUpdateStatusOfServiceCenter_Failure() {
		Long id = 1L;
		Boolean status = false;

		ResponseEntity<String> responseEntity = serviceCenterController.updateStatusOfServiceCenter(id, status);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center status updated successfully", responseEntity.getBody());
		verify(serviceCenterService, times(1)).updateStatusOfServiceCenter(id, status);
	}
}
