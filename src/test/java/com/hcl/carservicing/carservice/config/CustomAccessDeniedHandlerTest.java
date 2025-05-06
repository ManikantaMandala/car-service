package com.hcl.carservicing.carservice.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomAccessDeniedHandlerTest {

    @Test
    void testHandleAccessDeniedException() throws IOException, ServletException {
        CustomAccessDeniedHandler handler = new CustomAccessDeniedHandler();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AccessDeniedException accessDeniedException = new AccessDeniedException("Access Denied: You are not authorized.");
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);

        handler.handle(request, response, accessDeniedException);

        verify(response).addHeader("ContentType", "application/json");
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write("Access Denied: You are not authorized.");
    }
}
