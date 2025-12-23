package com.document.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.document.testconfig.SecurityConfigTest;

@SpringBootTest
@AutoConfigureMockMvc
public class SecuredApiTest {
     
	@Autowired
	MockMvc mockMvc;
     
	@Autowired
	JwtSecure jwtSecure;
	
	@Test
	void testAccessProtectedApiWithToken()throws Exception{
		
		String token = jwtSecure.generateToken("aish");
		
		mockMvc.perform(
				get("/api/document")
				.header("Authorization","Bearer " + token)
				)
		         .andExpectAll(status().isOk());
	}
}
