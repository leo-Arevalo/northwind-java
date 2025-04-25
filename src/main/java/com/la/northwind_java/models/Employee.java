package com.la.northwind_java.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * 
 * @author LeO
 *Entity representing an Employee
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeID;
	private String lastName;
	private String firstName;
	private String title;
	private String titleOfCourtesy;
	private Timestamp birthDate;
	private Timestamp hireDate;
	private String address;
	private String city;
	private String region;
	private String postalCode;
	private String country;
	private String homePhone;
	private String extension;
	private byte photo;
	private String notes;
	@ManyToOne
	private Employee reportsTo;
	private String photoPath;
	private float salary;
	@OneToMany(mappedBy = "reportsTo")
	List<Employee> employees = new ArrayList<>();
	@OneToMany(mappedBy = "employee")
	List<Order> orders = new ArrayList<>();
	@OneToMany
	List<Territory> territories = new ArrayList<>();
	
	

	
	
}
