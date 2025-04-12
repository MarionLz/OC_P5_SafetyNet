package com.openclassrooms.safetynet.DTO.personInfo;

import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing the response containing detailed information 
 * of all persons with the same last name.
 * This class holds a list of `PersonInfoPersonIdentityDTO` objects, each containing personal details.
 */
@Data
public class PersonInfoResponseDTO {

	List<PersonInfoPersonIdentityDTO> personsWithSameLastName;
	
	public PersonInfoResponseDTO(List<PersonInfoPersonIdentityDTO> persons) {
		this.personsWithSameLastName = persons;
	}
}
