
package com.la.northwind_java.security.controllers;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.la.northwind_java.security.jwt.JWTUtils;
import com.la.northwind_java.security.models.RefreshToken;
import com.la.northwind_java.security.services.RefreshTokenService;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	
	private final JWTUtils jwtUtils;
	
	private final UserDetailsService userDetailsService;
	
	private final RefreshTokenService refreshTokenService;
	
	
	public AuthController(AuthenticationManager authenticationManager, JWTUtils jwtUtils,
			UserDetailsService userDetailsService, RefreshTokenService refreshTokenService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
		this.refreshTokenService = refreshTokenService;
	}
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody Map<String, String> request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.get("username"), request.get("password")));
				System.out.println("Aca deberia mostrar username y password");
				System.out.println(request.get("username"));
				System.out.println(request.get("password"));
				
		String token = jwtUtils.generateToken(userDetailsService.loadUserByUsername(request.get("username")));
		
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.get("username"));
		return Map.of("accessToken", token, "refreshToken", refreshToken.getToken());
		
	}
	
	public Map<String, String> refresh(@RequestBody Map<String, String> request){
		String refreshToken = request.get("refreshToken");
		
		
		return refreshTokenService.findByToken(refreshToken)
				.map(token -> {
					String newAccessToken = jwtUtils.generateToken(userDetailsService.loadUserByUsername(token.getUser().getUsername()));
					return Map.of("accessToken",newAccessToken);
				})
				.orElseThrow(()-> new RuntimeException("Invalid refresh token"));
	}
	
	
	
	
	
}
