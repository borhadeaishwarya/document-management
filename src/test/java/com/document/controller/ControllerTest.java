package com.document.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(com.document.testconfig.SecurityConfigTest.class) 
@WebMvcTest(controllers=DocController.class)
public class ControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void testLoginSuccess()throws Exception{
		String json= "{\"username\":\"aish\",\"password\":\"1234\"}";
		mockMvc.perform(
             post("/api/document/login") 
             .contentType("application/json")
             .content(json)).andExpectAll(status().isOk());
				
	}

}
