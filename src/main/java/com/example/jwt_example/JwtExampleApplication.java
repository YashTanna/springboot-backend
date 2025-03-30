package com.example.jwt_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtExampleApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(JwtExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Encrypted password : ");
		System.out.println("yash123 : "+passwordEncoder.encode("yash123"));
		System.out.println("harsh123 : "+passwordEncoder.encode("harsh123"));
		System.out.println("parth123 : "+passwordEncoder.encode("parth123"));
		System.out.println("sanjana123 : "+passwordEncoder.encode("sanjana123"));
	}

}
