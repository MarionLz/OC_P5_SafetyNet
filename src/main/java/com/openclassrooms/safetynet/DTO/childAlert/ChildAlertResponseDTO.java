package com.openclassrooms.safetynet.DTO.childAlert;

import java.util.List;

import lombok.Data;

@Data
public class ChildAlertResponseDTO {
	
	private List<ChildDTO> children;
	private List<PersonsAtAddressDTO> otherFamilyMembers;
	
	public ChildAlertResponseDTO() {}
	
	public ChildAlertResponseDTO(List<ChildDTO> children, List<PersonsAtAddressDTO> otherFamilyMembers) {
		this.children = children;
		this.otherFamilyMembers = otherFamilyMembers;
	}
}
