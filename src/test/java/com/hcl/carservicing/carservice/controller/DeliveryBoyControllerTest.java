package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.DeliveryBoyDTO;
import com.hcl.carservicing.carservice.exceptionhandler.ElementAlreadyExistException;
import com.hcl.carservicing.carservice.exceptionhandler.ElementNotFoundException;
import com.hcl.carservicing.carservice.service.DeliveryBoyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryBoyControllerTest {

	@Mock
	private DeliveryBoyService deliveryBoyService;

	@InjectMocks
	private DeliveryBoyController deliveryBoyController;

	@Test
	void createWithCreatedStatus() {
		String name = "test_name";
		String contactNumber = "1809439134";
		Long serviceCenterId = 1L;
		List<Long> serviceRequestsId = new ArrayList<>();

		DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();

		deliveryBoyDTO.setName(name);
		deliveryBoyDTO.setContactNumber(contactNumber);
		deliveryBoyDTO.setServiceCenterId(serviceCenterId);
		deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);

		doNothing().when(deliveryBoyService).createDeliveryBoy(deliveryBoyDTO);

		ResponseEntity<String> result = deliveryBoyController.create(deliveryBoyDTO);

		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertEquals("Delivery boy created successfully", result.getBody());
		verify(deliveryBoyService, times(1))
				.createDeliveryBoy(deliveryBoyDTO);
	}

	@Test
	void createWhichThrowsElementAlreadyExistExceptionForContactNumber() {
		String name = "test_name";
		String contactNumber = "1809439134";
		Long serviceCenterId = 1L;
		List<Long> serviceRequestsId = new ArrayList<>();

		DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();

		deliveryBoyDTO.setName(name);
		deliveryBoyDTO.setContactNumber(contactNumber);
		deliveryBoyDTO.setServiceCenterId(serviceCenterId);
		deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);

		doThrow(new ElementAlreadyExistException("Contact Number already exists: " + deliveryBoyDTO.getContactNumber()))
				.when(deliveryBoyService).createDeliveryBoy(deliveryBoyDTO);

		ElementAlreadyExistException thrownException = assertThrows(ElementAlreadyExistException.class,
				() -> deliveryBoyController.create(deliveryBoyDTO));

		assertEquals("Contact Number already exists: " + deliveryBoyDTO.getContactNumber(),
				thrownException.getMessage());
		verify(deliveryBoyService, times(1))
				.createDeliveryBoy(deliveryBoyDTO);
	}

	@Test
	void createWhichThrowsIllegalArgumentExceptionForServiceCenterDTOBeingNull() {
		String name = "test_name";
		String contactNumber = "1809439134";
		Long serviceCenterId = 1L;
		List<Long> serviceRequestsId = new ArrayList<>();

		DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();

		deliveryBoyDTO.setName(name);
		deliveryBoyDTO.setContactNumber(contactNumber);
		deliveryBoyDTO.setServiceCenterId(serviceCenterId);
		deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);

		doThrow(new IllegalArgumentException("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId()))
				.when(deliveryBoyService).createDeliveryBoy(deliveryBoyDTO);

		IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class,
				() -> deliveryBoyController.create(deliveryBoyDTO));

		assertEquals("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId(),
				thrownException.getMessage());
		verify(deliveryBoyService, times(1))
				.createDeliveryBoy(deliveryBoyDTO);
	}

	@Test
	void updateWithOkStatus() {
		String name = "test_name_used";
		String contactNumber = "1809439134";
		Long serviceCenterId = 2L;
		Long deliveryBoyId = 1L;
		List<Long> serviceRequestsId = new ArrayList<>();

		DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();

		deliveryBoyDTO.setName(name);
		deliveryBoyDTO.setContactNumber(contactNumber);
		deliveryBoyDTO.setServiceCenterId(serviceCenterId);
		deliveryBoyDTO.setServiceRequestsId(serviceRequestsId);

		when(deliveryBoyService
				.updateDeliveryBoy(deliveryBoyId, deliveryBoyDTO))
				.thenReturn(deliveryBoyDTO);

		ResponseEntity<String> updatedDeliveryBoy = deliveryBoyController.update(deliveryBoyId, deliveryBoyDTO);

		assertNotNull(updatedDeliveryBoy);
		assertEquals(HttpStatus.OK, updatedDeliveryBoy.getStatusCode());
		assertEquals("Delivery boy updated successfully", updatedDeliveryBoy.getBody());
		verify(deliveryBoyService, times(1)).updateDeliveryBoy(deliveryBoyId, deliveryBoyDTO);
	}

	@Test
	void updateDeliveryBoyDetailsWhichThrowsElementNotFoundExceptionForDeliveryBoy() {
		Long deliveryBoyId = 100L;

		DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();
		deliveryBoyDTO.setName("John");
		deliveryBoyDTO.setContactNumber("9876543210");
		deliveryBoyDTO.setServiceCenterId(1L);

		doThrow(new ElementNotFoundException("DeliveryBoy not found: " + deliveryBoyId))
				.when(deliveryBoyService).updateDeliveryBoy(deliveryBoyId, deliveryBoyDTO);

		ElementNotFoundException thrownException = assertThrows(ElementNotFoundException.class,
				() -> deliveryBoyController.update(deliveryBoyId, deliveryBoyDTO));

		assertEquals("DeliveryBoy not found: " + deliveryBoyId, thrownException.getMessage());

		verify(deliveryBoyService, times(1)).updateDeliveryBoy(deliveryBoyId, deliveryBoyDTO);
	}

	@Test
	void updateDeliveryBoyDetailsWhichThrowsIllegalArgumentExceptionForServiceCenter() {
		Long deliveryBoyId = 1L;

		DeliveryBoyDTO deliveryBoyDTO = new DeliveryBoyDTO();
		deliveryBoyDTO.setName("test_user_invalid");
		deliveryBoyDTO.setContactNumber("9876543210");
		deliveryBoyDTO.setServiceCenterId(999L); // invalid ID

		doThrow(new IllegalArgumentException("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId()))
				.when(deliveryBoyService).updateDeliveryBoy(deliveryBoyId, deliveryBoyDTO);

		IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class,
				() -> deliveryBoyController.update(deliveryBoyId, deliveryBoyDTO));

		assertEquals("Invalid Service Center ID: " + deliveryBoyDTO.getServiceCenterId(), thrownException.getMessage());

		verify(deliveryBoyService, times(1)).updateDeliveryBoy(deliveryBoyId, deliveryBoyDTO);
	}

	@Test
	void getDeliveryBoysByCenterReturnsListSuccessfully() {
		Long centerId = 1L;

		List<DeliveryBoyDTO> mockList = new ArrayList<>();
		DeliveryBoyDTO boy1 = new DeliveryBoyDTO();
		boy1.setName("Ravi");

		DeliveryBoyDTO boy2 = new DeliveryBoyDTO();
		boy2.setName("Amit");

		mockList.add(boy1);
		mockList.add(boy2);

		when(deliveryBoyService.getDeliveryBoysByCenter(centerId)).thenReturn(mockList);

		ResponseEntity<List<DeliveryBoyDTO>> response = deliveryBoyController.byCenter(centerId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, Objects.requireNonNull(response.getBody()).size());
		assertEquals("Ravi", response.getBody().get(0).getName());

		verify(deliveryBoyService, times(1)).getDeliveryBoysByCenter(centerId);
	}

	@Test
	void getDeliveryBoysByCenterThrowsIllegalArgumentExceptionWhenCenterIdIsNull() {
		Long invalidCenterId = null;

		when(deliveryBoyService.getDeliveryBoysByCenter(invalidCenterId))
				.thenThrow(new IllegalArgumentException("service center id cannot be null"));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> deliveryBoyController.byCenter(invalidCenterId));

		assertEquals("service center id cannot be null", exception.getMessage());

		verify(deliveryBoyService, times(1)).getDeliveryBoysByCenter(invalidCenterId);
	}

	@Test
	void getAvailableDeliveryBoys_shouldReturnList() {
		List<DeliveryBoyDTO> mockList = List.of(
				new DeliveryBoyDTO("John", "9999999999", 1L, new ArrayList<>()),
				new DeliveryBoyDTO("Doe", "8888888888", 1L, new ArrayList<>()));

		when(deliveryBoyService.getAvailableDeliveryBoys()).thenReturn(mockList);

		ResponseEntity<List<DeliveryBoyDTO>> response = deliveryBoyController.available();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, Objects.requireNonNull(response.getBody()).size());
		verify(deliveryBoyService, times(1)).getAvailableDeliveryBoys();
	}

	@Test
	void getAvailableDeliveryBoys_shouldReturnEmptyList() {
		when(deliveryBoyService.getAvailableDeliveryBoys()).thenReturn(Collections.emptyList());

		ResponseEntity<List<DeliveryBoyDTO>> response = deliveryBoyController.available();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
		verify(deliveryBoyService, times(1)).getAvailableDeliveryBoys();
	}
}
