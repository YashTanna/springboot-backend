package com.example.jwt_example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt_example.dto.AuthRequest;
import com.example.jwt_example.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService service;
	
	@PostMapping("/login")
	public Map<String, String> loginAndJwt(@RequestBody AuthRequest authRequest) {
		
		Authentication authTocken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
		
		Authentication auth = authManager.authenticate(authTocken);
		
		if(auth.isAuthenticated()) {
			String token =  service.generateToken(auth);
			Map<String, String> map = new HashMap<String, String>();
			map.put("JwtToken", token);
			return map;
		}else {
			throw new UsernameNotFoundException("Invalid Username or Password");
		}
	}
	
}








