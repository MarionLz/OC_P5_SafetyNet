package com.openclassrooms.safetynet.DTO.fire;

import lombok.Data;

@Data
public class FirePersonsAtAddressDTO {

	private String lastName;
	private String age;
	private String[] medications;
	private String[] allergies;
	
	public FirePersonsAtAddressDTO() {}
	
	public FirePersonsAtAddressDTO(String lastName, String age, String[] medications, String[] allergies) {
		this.lastName = lastName;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}
}
