package com.example.jwt_example.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jwt_example.service.JwtService;
import com.example.jwt_example.service.impl.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService service;
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		Authentication authentication = null;
		
		if(header!= null &&  header.startsWith("Bearer ")) {
			jwtToken = header.substring(7);
			username = service.extractUsername(jwtToken);
		}
		
		authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(username != null && authentication == null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			Boolean isValid = service.validateToken(jwtToken, userDetails);
			
			if(isValid) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}



