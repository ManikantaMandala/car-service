package com.hcl.carservicing.carservice.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class CustomAuthenticationEntryPointTest {

    @Test
    void testCommence() throws ServletException, IOException {
        CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = new AuthenticationCredentialsNotFoundException("Authentication failed: Invalid credentials.");
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);

        entryPoint.commence(request, response, authException);

        verify(response).addHeader("ContentType", "application/json");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("Authentication failed: Invalid credentials.");
    }
}