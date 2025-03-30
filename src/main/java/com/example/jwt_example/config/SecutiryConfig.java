package com.example.jwt_example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.jwt_example.filter.JwtAuthenticationFilter;
import com.example.jwt_example.service.impl.UserDetailServiceImpl;

@Configuration
public class SecutiryConfig {

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	private JwtAuthenticationFilter authFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(req -> 
					req.requestMatchers(HttpMethod.GET,"/api/students")
						.hasAnyRole("STUDENT","TEACHER","ADMIN")
						.requestMatchers(HttpMethod.GET,"/api/students/**")
						.hasAnyRole("STUDENT","TEACHER","ADMIN")
						.requestMatchers(HttpMethod.POST,"/api/students")
						.hasRole("TEACHER")
						.requestMatchers(HttpMethod.PUT,"/api/students/**")
						.hasRole("TEACHER")
						.requestMatchers(HttpMethod.DELETE,"api/students/**")
						.hasRole("ADMIN")
						.requestMatchers("api/auth/login").permitAll()
					);
		http.httpBasic(Customizer.withDefaults());
		
		// Add jwt filter before UsernamePasswordAuthenticationFilter
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		
		//Disable session creation
		http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.csrf(csrf->csrf.disable());
		
		return http.build();
	}
	
	
	@Bean
	AuthenticationProvider provider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setUserDetailsService(userDetailServiceImpl);
		dao.setPasswordEncoder(passwordEncoder());
		return dao;
	}
	
	@Bean
	AuthenticationManager manager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	UserDetailsService detailsService() {
		return userDetailServiceImpl;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}







