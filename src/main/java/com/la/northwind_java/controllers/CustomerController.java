
package com.la.northwind_java.controllers;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.la.northwind_java.services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.la.northwind_java.dtos.*;
import com.la.northwind_java.security.models.Role;



@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "REST API for Customer management.")
@CrossOrigin("*")
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Operation(summary = "Get a paginated list of customers",
			description = "Requires ADMIN or USER role")
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
	public ResponseEntity<Page<CustomerDTO>> getAllCustomers(@ParameterObject Pageable pageable) {
		Page<CustomerDTO> customers = customerService.getAllCustomers(pageable);
		return ResponseEntity.ok(customers);
	}
	@Operation(summary = "Get a customer by ID", description = "Requires ADMIN or USER role")
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
		CustomerDTO customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}
	
	
	
	
	
	
	
	//PARA IMPLEMENTAR
	/**
	@GetMapping("customer/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id,
													Authentication authentication){
		//getPrincipal devuelve el objeto principal Autenticado puede ser un UserDetails
		User user = (User) authentication.getPrincipal();
		
		
		
		
		
		if(user.hasRole("ROLE_ADMIN") || user.hasRole("ROLE_EMPLOYEE")) {
			return ResponseEntity.ok(customerService.findById(id));
		}
		
		
		
		 // Un admin o employee puede ver cualquier cliente
		 
		System.out.println(user.hasRole("hola"));
		if(user.hasRole("ROLE_ADMIN") || user.getRoles().contains("ROLE_EMPLOYEE")) {
			return ResponseEntity.ok(customerService.findById(id));
		}
		
		//Un cliente solo puede ver su propia informacion
		if(user.getRoles().contains("ROLE_CUSTOMER") && user.getId().equals(id)) {
			return ResponseEntity.ok((customerService.findById());
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	
	*/
	
	
	
	
	@Operation(summary = "Create a new customer", description = "Requires ADMIN role")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CustomerDTO> createCustomer( 
			@Valid @RequestBody CustomerCreateDTO customerCreateDTO ){
		
		CustomerDTO customer = customerService.createCustomer(customerCreateDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}
	
	@Operation(summary = "Update an existing customer", description = "Requires ADMIN role")
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CustomerDTO> updateCustomer(
			@PathVariable Long id,
			@Valid @RequestBody CustomerUpdateDTO customerUpdateDTO) {
		CustomerDTO  customer = customerService.updateCustomer(id, customerUpdateDTO);
		return ResponseEntity.ok(customer);
	}
	
	@Operation(summary = "Delete a customer by ID", description = "Requires ADMIN role")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
}
