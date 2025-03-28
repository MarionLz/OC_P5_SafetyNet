package com.openclassrooms.safetynet.DTO.fire;

import lombok.Data;

@Data
public class FirePersonsAtAddressDTO {

	private String lastName;
	private String phone;
	private String age;
	private String[] medications;
	private String[] allergies;
	
	public FirePersonsAtAddressDTO() {}
	
	public FirePersonsAtAddressDTO(String lastName, String phone, String age, String[] medications, String[] allergies) {
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}
}
