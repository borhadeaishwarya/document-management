package com.document.security;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.document.config.SecurityConfig;
import com.document.controller.ControllerTest;
import com.document.controller.JwtControllerTest;

@WebMvcTest(controllers=JwtControllerTest.class)
@Import({SecurityConfig.class, JwtFilter.class,JwtAuthEntryPoint.class})
public class JwtfilterTest {
	
	@Autowired
	private MockMvc mockMvc;

	 @MockBean
	    private JwtSecure jwtSecure;

	  @MockBean
	    private UserDetailsService userDetailsService;
	  // ---------- 1. Public endpoint should pass ----------
	    @Test
	    void whenPublicEndpoint_thenNoAuthRequired() throws Exception {
	        mockMvc.perform(get("/api/document/login"))
	                .andExpect(status().isOk());
	    }

	    // ---------- 2. Valid token ----------
	    @Test
	    void whenValidToken_thenAuthenticationSuccess() throws Exception {

	        String token = "valid-token";
	        String username = "testuser";

	        when(jwtSecure.extractUsername(token)).thenReturn(username);
	        when(jwtSecure.validateToken(token)).thenReturn(true);

	        when(userDetailsService.loadUserByUsername(username))
	                .thenReturn(new User(
	                        username,
	                        "password",
	                        Collections.emptyList()
	                ));

	        mockMvc.perform(
	                get("/api/document/save")
	                        .header("Authorization", "Bearer " + token)
	        ).andExpect(status().isOk());
	    }

	    // ---------- 3. Invalid token ----------
	    @Test
	    void whenInvalidToken_thenUnauthorized() throws Exception {

	        String token = "invalid-token";

	        when(jwtSecure.extractUsername(token)).thenReturn("testuser");
	        when(jwtSecure.validateToken(token)).thenReturn(false);

	        mockMvc.perform(
	                get("/api/document/save")
	                        .header("Authorization", "Bearer " + token)
	        ).andExpect(status().isUnauthorized());
	    }
	  
}
