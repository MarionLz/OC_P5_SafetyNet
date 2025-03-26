package com.openclassrooms.safetynet.DTO.childAlert;

import java.util.List;

import lombok.Data;

@Data
public class ChildAtAdressResponseDTO {
	
	private List<ChildDTO> children;
	private List<OtherFamilyMembersDTO> otherFamilyMembers;
	
	public ChildAtAdressResponseDTO() {}
	
	public ChildAtAdressResponseDTO(List<ChildDTO> children, List<OtherFamilyMembersDTO> otherFamilyMembers) {
		this.children = children;
		this.otherFamilyMembers = otherFamilyMembers;
	}
}
