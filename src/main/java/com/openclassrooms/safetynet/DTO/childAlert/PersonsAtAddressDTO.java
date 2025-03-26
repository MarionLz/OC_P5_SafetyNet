package com.openclassrooms.safetynet.DTO.childAlert;

import lombok.Data;

@Data
public class PersonsAtAddressDTO {
	
	private String firstName;
	private String lastName;
	
	public PersonsAtAddressDTO() {}
	
	public PersonsAtAddressDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
