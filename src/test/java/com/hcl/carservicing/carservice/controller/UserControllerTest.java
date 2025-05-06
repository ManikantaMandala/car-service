package com.hcl.carservicing.carservice.controller;

import com.hcl.carservicing.carservice.dto.AppUserDTO;
import com.hcl.carservicing.carservice.dto.UserLoginDTO;
import com.hcl.carservicing.carservice.dto.UserLoginRequestDTO;
import com.hcl.carservicing.carservice.service.UserService;
import com.hcl.carservicing.carservice.config.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private UserController userController;

	@Test
	void testRegister_Success() {
		AppUserDTO userDTO = new AppUserDTO();

		userDTO.setUsername("testUser");
		userDTO.setPassword("password");

		ResponseEntity<String> responseEntity = userController.register(userDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("User registered successfully", responseEntity.getBody());

		verify(userService, times(1)).register(userDTO);
	}

	@Test
	void testRegister_InvalidInput() {
		AppUserDTO userDTO = new AppUserDTO();
		userDTO.setUsername(null);

		ResponseEntity<String> responseEntity = userController.register(userDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("User registered successfully", responseEntity.getBody());
		verify(userService, times(1)).register(userDTO);
	}

	@Test
	void testRegister_EmptyPassword() {
		AppUserDTO userDTO = new AppUserDTO();
		userDTO.setUsername("testUser");
		userDTO.setPassword("");

		ResponseEntity<String> responseEntity = userController.register(userDTO);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("User registered successfully", responseEntity.getBody());

		verify(userService, times(1)).register(userDTO);
	}

	@Test
	void testLogin_Success() {
		String username = "testUser";
		String password = "password";
		String token = "mockedToken";
		Date expiration = new Date();

		UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(username, password);

		UserLoginDTO userLoginDTO = new UserLoginDTO(token, expiration);

		when(userService.login(username, password)).thenReturn(userLoginDTO);

		ResponseEntity<UserLoginDTO> responseEntity = userController.login(userLoginRequestDTO);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userLoginDTO, responseEntity.getBody());

		verify(userService, times(1)).login(username, password);
	}

	@Test
	void testLogin_InvalidCredentials() {
		String username = "invalidUser";
		String password = "wrongPassword";

		UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(username, password);

		doThrow(new RuntimeException("Invalid credentials"))
				.when(userService).login(username, password);

		assertThrows(RuntimeException.class,
				() -> userController.login(userLoginRequestDTO));

		verify(userService, times(1)).login(username, password);
		verify(jwtUtil, times(0)).generateToken(username);
	}

	@Test
	void testLogin_EmptyUserId() {
		String username = "";
		String password = "password";
		String token = "mockedToken";
		Date expiration = new Date();

		UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(username, password);
		UserLoginDTO userLoginDTO = new UserLoginDTO(token, expiration);

		when(userService.login(username, password)).thenReturn(userLoginDTO);

		ResponseEntity<UserLoginDTO> responseEntity = userController.login(userLoginRequestDTO);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userLoginDTO, responseEntity.getBody());

		verify(userService, times(1)).login(username, password);
	}

}
