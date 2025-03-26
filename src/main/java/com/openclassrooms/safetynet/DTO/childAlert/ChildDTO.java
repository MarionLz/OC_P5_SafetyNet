package com.openclassrooms.safetynet.DTO.childAlert;

import lombok.Data;

@Data
public class ChildDTO {

	private String firstName;
	private String lastName;
	private String age;
	
	public ChildDTO() {}
	
	public ChildDTO(String firstName, String lastName, String age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
}
