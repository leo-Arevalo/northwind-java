package com.la.northwind_java.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int regionID;
	private String regionDescription;
	@OneToMany(mappedBy = "region")
	List<Territory> territories = new ArrayList<>();
	


}
