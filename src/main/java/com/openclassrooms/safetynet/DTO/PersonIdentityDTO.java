package com.openclassrooms.safetynet.DTO;

import lombok.Data;

@Data
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
