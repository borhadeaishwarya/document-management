package com.document.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.ServletException;

public class JWTAuthEntryPointTest {

    @Test
    void commence_shouldReturnUnauthorized() throws IOException, ServletException {

        JwtAuthEntryPoint entryPoint = new JwtAuthEntryPoint();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException authException =
                new AuthenticationException("Invalid token") {};

        entryPoint.commence(request, response, authException);

        assertEquals(401, response.getStatus());
        assertEquals(
                "Unauthorized :token missing or invalid",
                response.getErrorMessage()
        );
    }
}
