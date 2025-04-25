

package com.la.northwind_java.mappers;


import org.mapstruct.AfterMapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import com.la.northwind_java.dtos.CustomerCreateDTO;
import com.la.northwind_java.dtos.CustomerDTO;
import com.la.northwind_java.dtos.CustomerUpdateDTO;

import com.la.northwind_java.models.Customer;


/*
 * Mapper interface for converting between Customer entites and DTOs.
 * Uses MapStruct for automatic mapping.
 */

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	/**
	 * Converts a Customer entity to a CustomerDTO.
	 * Maps nested objects to their corresponding IDs.
	 */
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "company", target = "company")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "jobTitle", target = "jobTitle")
	@Mapping(source = "email", target = "email")
	@Mapping(source = "businessPhone", target = "businessPhone")
	@Mapping(source = "homePhone", target = "homePhone")
	@Mapping(source = "mobilePhone", target = "mobilePhone")
	@Mapping(source = "faxNumber", target = "faxNumber")
	@Mapping(source = "address", target = "address")
	@Mapping(source = "city", target = "city")
	@Mapping(source = "stateProvince", target = "stateProvince")
	@Mapping(source = "postalCode", target = "postalCode")
	@Mapping(source = "countryRegion", target = "countryRegion")
	@Mapping(source = "webPage", target = "webPage")
	@Mapping(source = "notes", target = "notes")
	@Mapping(source = "attachments", target = "attachments")

	//@Mapping(target = "orders", ignore = true)

	CustomerDTO toCustomerDTO(Customer customer);
	
	/**
	 * Converts a CustomerDTO back to a Customer entity.
	 * Uses the inverse of the mappings defined in to CustomerDTO().
	 */
	//@InheritInverseConfiguration
	//@Mapping(target = "orders", ignore = true)//ignoramos la relacion con las ordenes
	//Customer toCustomer(CustomerDTO customerDTO);
	
	
	/**
	 * Creates a new Customer entity with values from a CustomerCreateDTO
	 */
	@Mapping(target = "id", ignore = true) //Id will generated automatically.
	@Mapping(target = "orders", ignore = true) //there will not asigned orders at create.
	Customer toCustomer(CustomerCreateDTO customerCreateDTO);
	
	/**
	 * Updates an existing Customer entity with values from a CustomerUpdateDTO.
	 * This does not replace associations unless explicitly provided.
	 */
	@Mapping( target = "orders", ignore = true) //No sobrescribir pedidos
	void updateEntity(CustomerUpdateDTO customerUpdateDTO, @MappingTarget Customer customer);
	
	/**
	 * Maps a list of Order entities to a list of OrderDTO.
	 */
	//@Named("mapOrdersList")
	//default List<OrderDTO> mapOrdersList(List<Order> orders){
	//	return orders != null ? orders.stream().map(this::toOrderDTO).toList() : null;
	//}
	
	/**
	 * Converts an Order entity to an OrderDTO (for nested mapping in CustomerDTO)
	 */
	
	/*
	@Named("toOrderDTO")
	@Mapping(source = "id", target = "orderID")
	@Mapping(source = "customer", target = "customer")
	@Mapping(source = "employee", target = "employee")
	@Mapping(source = "orderDate", target = "orderDate")
	@Mapping(source = "shippedDate", target = "shipperDate")
	@Mapping(source = "shipper", target = "shipper")
	@Mapping(source = "shipName", target = "shipName")
	@Mapping(source = "shipAddress", target = "shipAddress")
	@Mapping(source = "shipCity", target = "shipCity")
	@Mapping(source = "shipStateProvince", target = "shipStateProvince")
	@Mapping(source = "shipPostalCode", target = "shipPostalCode")
	@Mapping(source = "shipCountryRegion", target = "shipCountryRegion")
	@Mapping(source = "shippingFee", target = "shippingFee")
	@Mapping(source = "taxes", target = "taxes")
	@Mapping(source = "paymentType", target = "paymentType")
	@Mapping(source = "paidDate", target = "paidDate")
	@Mapping(source = "notes", target = "notes")
	@Mapping(source = "taxRate", target = "taxRate")
	@Mapping(source = "taxStatus", target = "taxStatus")
	@Mapping(source = "status", target = "status")
	@Mapping(source = "orderDetails", target = "orderDetails", qualifiedByName = "mapOrderDetailsList")
	default OrderDTO toOrderDTO(Order order) {
		if(order == null) return null;
		return new OrderDTO(
				  order.getOrderID(),
		            null, // Customer will be handled in OrderMapper
		            null, // Employee will be handled in OrderMapper
		            order.getOrderDate(),
		            order.getShippedDate(),
		            null, // Shipper will be handled in OrderMapper
		            order.getShipName(),
		            order.getShipAddress(),
		            order.getShipCity(),
		            order.getShipStateProvince(),
		            order.getShipPostalCode(),
		            order.getShipCountryRegion(),
		            order.getShippingFee(),
		            order.getTaxes(),
		            order.getPaymentType(),
		            order.getPaidDate(),
		            order.getNotes(),
		            order.getTaxRate(),
		            null, // TaxStatus will be handled in OrderMapper
		            null, // Status will be handled in OrderMapper
		            null // OrderDetails will be handled in OrderMapper
		            );
	}
	
	*/
	
	/**
	 * Normalizes data after mapping a CustomerDTO to a Customer entity.
	 * Trims and formats certain string fields.
	 */
	
	@AfterMapping
	default void normalizeData(@MappingTarget Customer customer) {
		if(customer.getFirstName() != null) {
			customer.setFirstName(customer.getFirstName().trim());
		}
		if(customer.getLastName() != null) {
			customer.setLastName(customer.getLastName().trim());
		}
		if(customer.getEmail() != null) {
			customer.setEmail(customer.getEmail().toLowerCase().trim());
		}
	}
	
	
	
	
	
	
	
}
