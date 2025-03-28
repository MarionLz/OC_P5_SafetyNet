package com.openclassrooms.safetynet.DTO.fire;

import java.util.List;

import lombok.Data;

@Data
public class FireResponseDTO {

	private List<FirePersonsAtAddressDTO> personsLivingAtGivenAddress;
	private String stationNumber;
	
	public FireResponseDTO() {}
	
	public FireResponseDTO(List<FirePersonsAtAddressDTO> persons, String stationNumber) {
		this.personsLivingAtGivenAddress = persons;
		this.stationNumber = stationNumber;
	}
}
