package com.openclassrooms.safetynet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResidentDTO {

	private String lastName;
	private String phone;
	private String address;
	private String age;
	private String email;
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
	
	private ResidentDTO(String lastName, String address, String age, String email, String[] medications, String[] allergies) {
		this.lastName = lastName;
		this.address = address;
		this.age = age;
		this.email = email;
		this.medications = medications;
		this.allergies = allergies;
	}
}
