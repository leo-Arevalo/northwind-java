
package com.la.northwind_java.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JWTUtils {

	private final String SECRET_KEY = "3D1qKjfP9hOqpXn3tHfs+7YX4Q9z6IlrZaM7vM3XNkA";
	private final long EXPIRATION_TIME = 86400000; //24 horas
	
	
	public String generateToken(UserDetails userDetails) {
		String roles = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(","));
		
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("roles", roles)//agregamos roles al token
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() +EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		final List<String> tokenRoles = extractRoles(token);
		final List<String> userRoles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		return username.equals(userDetails.getUsername()) 
				&& !isTokenExpired(token)
				&& userRoles.containsAll(tokenRoles);//comparamos los roles
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.build().parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public List<String> extractRoles(String token){
		String roles = Jwts.parser()
							.setSigningKey(SECRET_KEY)
							.build()
							.parseClaimsJws(token)
							.getBody()
							.get("roles", String.class);
		return Arrays.asList(roles.split(","));
	}
	
	public boolean isTokenExpired(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getExpiration()
				.before(new Date());
	}
}
