package com.example.jwt_example.service;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
	@Value("${security.jwt.expiration-time}")
	private long jwtExpirationTime;
	
	
	private Key getSignKey() {
		byte [] key = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(key);
	}
	
	
	public  String generateToken(Authentication authentication) {
		
		String authorities = authentication.getAuthorities()
								.stream()
								.map((auth)->auth.getAuthority())
								.collect(Collectors.joining(","));
		
		return Jwts.builder()
					.subject(authentication.getName())
					.claim("roles",authorities)
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis()+jwtExpirationTime))
					.signWith(getSignKey())
					.compact();
					
		
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
					.verifyWith((SecretKey) getSignKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();
	}
	
	public <T> T extractClaim(String token,Function<Claims, T>claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean validateToken(String token,UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}










