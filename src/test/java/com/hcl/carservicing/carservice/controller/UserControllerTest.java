package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.AppUserDTO;

import com.hcl.carservicing.carservice.service.UserService;

import com.hcl.carservicing.carservice.config.JwtUtil;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows; // Import assertThrows

import static org.mockito.Mockito.*;

class UserControllerTest {

	@Mock

	private UserService userService;

	@Mock

	private JwtUtil jwtUtil;

	@InjectMocks

	private UserController userController;

	@BeforeEach

	void setUp() {

		MockitoAnnotations.openMocks(this);

	}

	@Test

	void testRegister_Success() {

		// Arrange

		AppUserDTO userDTO = new AppUserDTO();

		userDTO.setUsername("testUser");

		userDTO.setPassword("password");


		// Act

		ResponseEntity<String> responseEntity = userController.register(userDTO);

		// Assert

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		assertEquals("User registered successfully", responseEntity.getBody());

		verify(userService, times(1)).register(userDTO);

	}

	@Test

	void testRegister_InvalidInput() {

		// Arrange

		AppUserDTO userDTO = new AppUserDTO();

		userDTO.setUsername(null); // Invalid input

		// Act

		ResponseEntity<String> responseEntity = userController.register(userDTO);

		// Assert

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		assertEquals("User registered successfully", responseEntity.getBody());

		verify(userService, times(1)).register(userDTO);

	}

	@Test

	void testRegister_EmptyPassword() {

		// Arrange

		AppUserDTO userDTO = new AppUserDTO();

		userDTO.setUsername("testUser");

		userDTO.setPassword("");


		// Act

		ResponseEntity<String> responseEntity = userController.register(userDTO);

		// Assert

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		assertEquals("User registered successfully", responseEntity.getBody());

		verify(userService, times(1)).register(userDTO);

	}

	@Test

	void testLogin_Success() {

		// Arrange

		String userId = "testUser";

		String password = "password";

		String token = "mockedToken";

		doNothing().when(userService).login(userId, password);

		when(jwtUtil.generateToken(userId)).thenReturn(token);

		// Act

		ResponseEntity<String> responseEntity = userController.login(userId, password);

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals("Login Successfully......!", responseEntity.getBody());

		verify(userService, times(1)).login(userId, password);

		verify(jwtUtil, times(1)).generateToken(userId);

	}

	@Test

	void testLogin_InvalidCredentials() {

		// Arrange

		String userId = "invalidUser";

		String password = "wrongPassword";

		doThrow(new RuntimeException("Invalid credentials"))

				.when(userService).login(userId, password);

		// Act & Assert

		assertThrows(RuntimeException.class, () -> userController.login(userId, password)); // Use assertThrows

		verify(userService, times(1)).login(userId, password);

		verify(jwtUtil, times(0)).generateToken(userId);

	}

	@Test

	void testLogin_EmptyUserId() {

		// Arrange

		String userId = "";

		String password = "password";

		String token = "mockedToken";

		doNothing().when(userService).login(userId, password);

		when(jwtUtil.generateToken(userId)).thenReturn(token);

		// Act

		ResponseEntity<String> responseEntity = userController.login(userId, password);

		// Assert

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals("Login Successfully......!", responseEntity.getBody());

		verify(userService, times(1)).login(userId, password);

		verify(jwtUtil, times(1)).generateToken(userId);

	}

}
