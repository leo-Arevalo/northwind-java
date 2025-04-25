package com.la.northwind_java.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author LeO
 *Entity representing an order.
 */

@Entity
@Table(name = "orders", indexes ={
		@Index(name = "idx_order_date", columnList = "orderDate"),
		@Index(name = "idx_status_id", columnList = "statusId"),
		@Index(name = "idx_customer_id", columnList = "customer_id"),
		@Index(name = "idx_employee_id", columnList = "employee_id"),
		@Index(name = "idx_shipped_date", columnList = "shippedDate")
		
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

	/**
	 * Unique identifier for the order.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer orderID;
	
	/**
	 * Customer who placed the order.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	/**
	 * Employee associated with the order.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	/**
	 * Date when the order was placed.
	 */
	@PastOrPresent(message = "Order date must be in the past or present")
	@Column(nullable = false)
	private LocalDateTime orderDate;

	/**
	 * Date when the order was shipped
	 */
	@PastOrPresent(message = "Shipped date must be in the past or present.")
	private LocalDateTime shippedDate;

	/**
	 * Shipper handling the order.
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipper_id")
	private Shipper shipper;

	/**
	 * Shipping Details
	 */
	@Size(max = 50)
	@NotBlank(message = "Ship Name cannot be empty.")
	@Column(nullable = false)
	private String shipName;

	/**
	 * type longtext in the database
	 */
	@Lob
	private String shipAddress;

	/**
	 * 
	 */
	@Size(max = 50)
	private String shipCity;

	/**
	 * 
	 */
	@Size(max = 50)
	private String shipStateProvince;

	/**
	 * 
	 */
	@Size(max = 50)
	private String shipPostalCode;

	/**
	 * 
	 */
	@Size(max = 50)
	private String shipCountryRegion;

	
	/**
	 * Shipping and tax details
	 */
	@DecimalMin(value = "0.00", message = "Shipping fee must be positive")
	@Column(nullable = false)
	private BigDecimal shippingFee;
	
	@DecimalMin(value = "0.00", message = "taxes must be positive")
	@Column(nullable = false)
	private BigDecimal taxes;
	
	
	/**
	 * Payment and order status
	 */
	@Size(max = 50)
	@Column(nullable = false)
	private String paymentType;
	
	private LocalDateTime paidDate;
	
	@Lob
	private String notes;
	
	@DecimalMin(value = "0.00", message = "Tax rate must be positive")
	private Double taxRate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tax_status_id")
	private OrderTaxStatus taxStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id", nullable = false)
	private OrderStatus status;
	
	/**
	 * Order details associated with this order.
	 */
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@BatchSize(size = 10)
	@OrderBy("id ASC")
	private List<OrderDetails> orderDetails = new ArrayList<>();
	
	   /**
     * Normalize fields before persisting or updating.
     */
    @PrePersist
    @PreUpdate
    private void normalizeData() {
        if (shipName != null) {
            shipName = shipName.trim().toUpperCase();
        }
        if (shipCity != null) {
            shipCity = shipCity.trim();
        }
        if (shippedDate != null && orderDate != null && shippedDate.isBefore(orderDate)) {
            throw new IllegalStateException("Shipped date cannot be before order date.");
        }
    }

	

	
	

	
	
}
