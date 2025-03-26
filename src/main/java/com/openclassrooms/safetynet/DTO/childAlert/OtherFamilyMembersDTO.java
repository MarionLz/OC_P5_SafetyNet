package com.openclassrooms.safetynet.DTO.childAlert;

import lombok.Data;

@Data
public class OtherFamilyMembersDTO {
	
	private String firstName;
	private String lastName;
	
	public OtherFamilyMembersDTO() {}
	
	public OtherFamilyMembersDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
