package com.document.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.test.web.servlet.MockMvc;

import com.document.exception.CustumException;
import com.document.exception.GlobalExceptionHandler;
import com.document.security.JwtFilter;
import com.document.security.JwtSecure;
import com.document.service.DocService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DocController.class)
@Import(GlobalExceptionHandler.class)
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DocService docService;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtSecure jwtSecure;

	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	void testLoginSuccess() throws Exception {

		when(jwtSecure.generateToken("aish")).thenReturn("mock-token");

		mockMvc.perform(post("/api/document/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"aish\",\"password\":\"1234\"}")).andExpect(status().isOk())
				.andExpect(content().string("mock-token"));
	}

	void handleCustumException() throws Exception {
		when(docService.getAllDocument()).thenThrow(new CustumException("custum error"));
		mockMvc.perform(get("/api/document/alldoc")).andExpect(status().isBadRequest())
				.andExpect(content().string("custum error"));
	}

//	@Test
//	void testWelcome() throws Exception {
//	    mockMvc.perform(get("/api/document/welcome"))
//	           .andExpect(status().isOk())
//	           .andExpect(content().string("welcome postman"));
//	}

}
