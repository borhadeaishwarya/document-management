package com.document.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.context.annotation.Import;

import com.document.testconfig.SecurityConfigTest;

public class JwtSecureTest {
	
   JwtSecure jwtSecure=new JwtSecure();
   
    void testGenerateAndValidateToken() {
    	
    	String token=jwtSecure.generateToken("aish");
    	assertNotNull(token);
    	
    	String username = jwtSecure.extractUsername(token);
    	assertEquals("aish", username);
    	
    	assertTrue(jwtSecure.validateToken(token));
    }

}
