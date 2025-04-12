package com.openclassrooms.safetynet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the identity of a person.
 * This includes the person's first name, last name, and phone number.
 * Fields with null values are excluded from JSON serialization.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonIdentityDTO {
	
	private String firstName;
	private String lastName;
	private String phone;
	
	public PersonIdentityDTO() {}

	public PersonIdentityDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public PersonIdentityDTO(String firstName, String lastName, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
}
