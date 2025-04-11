package com.openclassrooms.safetynet.DTO.personInfo;

import java.util.List;

import lombok.Data;

@Data
public class PersonInfoResponseDTO {

	List<PersonInfoPersonIdentityDTO> personsWithSameLastName;
	
	public PersonInfoResponseDTO(List<PersonInfoPersonIdentityDTO> persons) {
		this.personsWithSameLastName = persons;
	}
}
