package com.la.northwind_java.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;





@Entity
public class Category {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryID",unique = true)
	private int categoryID;
	@Column(name = "categoryName", nullable = false)
	private String categoryName;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "picture")
	private byte picture;

	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	List<Product> productos = new ArrayList<Product>();
	
	
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	

}
