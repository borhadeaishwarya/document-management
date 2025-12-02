package com.document.auhController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.document.authRequest.AuthRequest;
import com.document.security.JwtSecure;

@RestController
@RequestMapping("/auth")

public class AuthController {
	
    @Autowired
	private JwtSecure jwtSecure;
    
	@PostMapping("/login")
	public String login(@RequestBody AuthRequest request) {
		if(request.getUsername().equals("aish")&&request.getPassword().equals("1234"))
		{
		return jwtSecure.generateToken(request.getUsername()); 
		}
	return "Invalid username or password";
	}
}
