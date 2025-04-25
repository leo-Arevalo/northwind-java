package com.la.northwind_java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
@Table(name = "territory")
public class Territory {
	
	@Id
	@GeneratedValue
	@NotBlank(message = "Territory's Id cannot be blank.")
	@Column(name = "territoryID", unique = true)
	private String territoryID;
	@Column
	private String territoryDescription;
	@ManyToOne
	private Region region;

	

}
