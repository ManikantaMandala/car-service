package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import com.hcl.carservicing.carservice.dto.UserLoginRequestDTO;
import com.hcl.carservicing.carservice.enums.UserRole;
import com.hcl.carservicing.carservice.service.UserService;
import com.hcl.carservicing.carservice.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

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
		String username = "testUser";
		String password = "password";
		String token = "mockedToken";
		UserRole role = UserRole.USER;
		Date expiration = new Date();

//		HashMap<String, UserRole> authorities = new HashMap<>();
//		authorities.put("role", role);

		UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(username, password);

		UserLoginDTO userLoginDTO = new UserLoginDTO(token, expiration);

		when(userService.login(username, password)).thenReturn(userLoginDTO);
		when(jwtUtil.generateToken(username)).thenReturn(token);

		// Act
		ResponseEntity<UserLoginDTO> responseEntity = userController.login(userLoginRequestDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userLoginDTO, responseEntity.getBody());

		verify(userService, times(1)).login(username, password);
	}

	@Test
	void testLogin_InvalidCredentials() {

		// Arrange
		String username = "invalidUser";
		String password = "wrongPassword";

		UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(username, password);

		doThrow(new RuntimeException("Invalid credentials"))
				.when(userService).login(username, password);

		// Act & Assert
		assertThrows(RuntimeException.class,
				() -> userController.login(userLoginRequestDTO)); // Use assertThrows

		verify(userService, times(1)).login(username, password);
//		verify(jwtUtil, times(0)).generateToken(userId, new HashMap<>());
		verify(jwtUtil, times(0)).generateToken(username);
	}

	@Test
	void testLogin_EmptyUserId() {

		// Arrange
		String username = "";
		String password = "password";
		String token = "mockedToken";
		Date expiration = new Date();

		UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(username, password);
		UserLoginDTO userLoginDTO = new UserLoginDTO(token, expiration);

		when(userService.login(username, password)).thenReturn(userLoginDTO);
		when(jwtUtil.generateToken(username)).thenReturn(token);
		when(jwtUtil.extractExpiration(token)).thenReturn(new Date());

		// Act
		ResponseEntity<UserLoginDTO> responseEntity = userController.login(userLoginRequestDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userLoginDTO, responseEntity.getBody());

		verify(userService, times(1)).login(username, password);
	}

}
