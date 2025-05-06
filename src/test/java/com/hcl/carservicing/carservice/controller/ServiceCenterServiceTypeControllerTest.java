package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.ServiceCenterServiceTypeDTO;
import com.hcl.carservicing.carservice.service.ServiceCenterServiceTypeService;
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
class ServiceCenterServiceTypeControllerTest {

	@Mock
	private ServiceCenterServiceTypeService service;

	@InjectMocks
	private ServiceCenterServiceTypeController controller;

	@Test
	void testAddServiceTypeToCenter_Success() {
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(1L);
		dto.setServiceTypeId(2L);

		ResponseEntity<String> responseEntity = controller.add(dto);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service type assigned to service center successfully", responseEntity.getBody());

		verify(service, times(1)).addServiceTypeToCenter(dto);
	}

	@Test
	void testAddServiceTypeToCenter_InvalidInput() {
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();
		dto.setServiceCenterId(null);

		ResponseEntity<String> responseEntity = controller.add(dto);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Service type assigned to service center successfully", responseEntity.getBody());

		verify(service, times(1)).addServiceTypeToCenter(dto);
	}

	@Test
	void testUpdateServiceCenterServiceType_Success() {
		Long id = 10L;
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(3L);
		dto.setServiceTypeId(4L);

		ResponseEntity<String> responseEntity = controller.update(id, dto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center service mapping updated successfully", responseEntity.getBody());

		verify(service, times(1)).updateServiceCenterServiceType(id, dto);
	}

	@Test
	void testUpdateServiceCenterServiceType_InvalidInput() {
		Long id = 10L;
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(3L);

		ResponseEntity<String> responseEntity = controller.update(id, dto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center service mapping updated successfully", responseEntity.getBody());

		verify(service, times(1)).updateServiceCenterServiceType(id, dto);
	}

	@Test
	void testUpdateServiceCenterServiceType_MaxIdValue() {
		Long id = Long.MAX_VALUE;
		ServiceCenterServiceTypeDTO dto = new ServiceCenterServiceTypeDTO();

		dto.setServiceCenterId(3L);
		dto.setServiceTypeId(4L);

		ResponseEntity<String> responseEntity = controller.update(id, dto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Service center service mapping updated successfully", responseEntity.getBody());

		verify(service, times(1)).updateServiceCenterServiceType(id, dto);
	}

	@Test
	void testGetByServiceCenter_ExistingCenter() {
		Long centerId = 1L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();
		ServiceCenterServiceTypeDTO dto1 = new ServiceCenterServiceTypeDTO();

		dto1.setServiceCenterId(centerId);
		dto1.setServiceTypeId(101L);

		expectedList.add(dto1);

		when(service.getByServiceCenter(centerId)).thenReturn(expectedList);

		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byCenter(centerId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceCenter(centerId);
	}

	@Test
	void testGetByServiceCenter_NoServices() {
		Long centerId = 2L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();

		when(service.getByServiceCenter(centerId)).thenReturn(expectedList);

		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byCenter(centerId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceCenter(centerId);
	}

	@Test
	void testGetByServiceCenter_InvalidCenter() {
		Long centerId = -1L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();

		when(service.getByServiceCenter(centerId)).thenReturn(expectedList);

		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byCenter(centerId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceCenter(centerId);
	}

	@Test
	void testGetByServiceType_ExistingServiceType() {
		Long serviceTypeId = 2L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();
		ServiceCenterServiceTypeDTO dto1 = new ServiceCenterServiceTypeDTO();

		dto1.setServiceTypeId(serviceTypeId);
		dto1.setServiceCenterId(201L);

		expectedList.add(dto1);

		when(service.getByServiceType(serviceTypeId)).thenReturn(expectedList);

		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byService(serviceTypeId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceType(serviceTypeId);
	}

	@Test
	void testGetByServiceType_NoCenters() {
		Long serviceTypeId = 3L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();

		when(service.getByServiceType(serviceTypeId)).thenReturn(expectedList);

		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byService(serviceTypeId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());

		verify(service, times(1)).getByServiceType(serviceTypeId);
	}

	@Test
	void testGetByServiceType_InvalidServiceType() {
		Long serviceTypeId = -2L;
		List<ServiceCenterServiceTypeDTO> expectedList = new ArrayList<>();
		when(service.getByServiceType(serviceTypeId)).thenReturn(expectedList);

		ResponseEntity<List<ServiceCenterServiceTypeDTO>> responseEntity = controller.byService(serviceTypeId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedList, responseEntity.getBody());
		verify(service, times(1)).getByServiceType(serviceTypeId);
	}
}
