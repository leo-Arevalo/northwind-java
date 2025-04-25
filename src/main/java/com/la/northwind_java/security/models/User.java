package com.la.northwind_java.security.models;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
@Entity
@Table(name = "users") 
public class User implements UserDetails{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;
	
	@NotBlank(message = "Password cannot be blank")
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "username", nullable = false, unique = true, length = 50)
	@NotBlank(message = "userName cannot be blank")
	private String username;
	
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")	
	@Column(nullable = false, name = "email", unique = true, length = 255)
	private String email;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;

	@Column(name = "account_non_locked", nullable = false)
	private boolean accountNonLocked = true;

	@Column(name = "account_non_expired", nullable = false)
	private boolean accountNonExpired = true;
	
	
	@Column(name = "credentials_non_expired", nullable = false)
	private boolean credentialsNonExpired = true;
	
	@Column(name ="failed_attempts", nullable = false)
	private int failedAttempts = 0;
	
	@Column(name = "lock_time")
	private LocalDateTime lockTime;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name ="role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
								orphanRemoval = true,
								fetch = FetchType.LAZY)
	private Set<RefreshToken> refreshToken = new HashSet<>();

	/**
	 * Metodo para verificar si el usuario tiene un rol especifico
	 * 
	 */
	
	public boolean hasRole(String roleName) {
		return roles.stream().map(Role::getName).anyMatch(role -> role.equals(roleName));
	}
	
	/**
	 * METODOS DE USER DETAILS
	 */
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toSet());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	
}
