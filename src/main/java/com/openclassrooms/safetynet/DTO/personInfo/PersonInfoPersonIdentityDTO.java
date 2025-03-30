package com.openclassrooms.safetynet.DTO.personInfo;

import lombok.Data;

@Data
public class PersonInfoPersonIdentityDTO {

	private String lastName;
	private String address;
	private String age;
	private String email;
	private String[] medications;
	private String[] allergies;
	
	public PersonInfoPersonIdentityDTO(String lastName, String address, String age, String email, String[] medications, String[] allergies) {
		this.lastName = lastName;
		this.address = address;
		this.age = age;
		this.email = email;
		this.medications = medications;
		this.allergies = allergies;
	}
}
