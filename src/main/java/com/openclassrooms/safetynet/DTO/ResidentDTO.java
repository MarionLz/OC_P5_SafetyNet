package com.openclassrooms.safetynet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a resident's information.
 * This includes the resident's last name, phone number, age, medications, and allergies.
 * Fields with null values are excluded from JSON serialization.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResidentDTO {

	private String lastName;
	private String phone;
	private String age;
	private String[] medications;
	private String[] allergies;
	
	public ResidentDTO() {}
	
	public ResidentDTO(String lastName, String phone, String age, String[] medications, String[] allergies) {
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}
}
