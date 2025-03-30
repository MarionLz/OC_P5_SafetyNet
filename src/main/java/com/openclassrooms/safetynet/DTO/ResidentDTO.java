package com.openclassrooms.safetynet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

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
