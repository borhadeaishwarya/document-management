package com.document.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/document")
public class JwtControllerTest {

	 @GetMapping("/login")
	    public String login() {
	        return "login";
	    }

	    @GetMapping("/save")
	    public String save() {
	        return "save";
	    }
}
