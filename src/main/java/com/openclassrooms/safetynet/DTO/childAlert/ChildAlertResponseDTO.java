package com.openclassrooms.safetynet.DTO.childAlert;

import java.util.List;

import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;

import lombok.Data;

@Data
public class ChildAlertResponseDTO {
	
	private List<ChildDTO> children;
	private List<PersonIdentityDTO> otherFamilyMembers;
	
	public ChildAlertResponseDTO() {}
	
	public ChildAlertResponseDTO(List<ChildDTO> children, List<PersonIdentityDTO> otherFamilyMembers) {
		this.children = children;
		this.otherFamilyMembers = otherFamilyMembers;
	}
}
