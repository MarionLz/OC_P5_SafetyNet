package com.openclassrooms.safetynet.DTO.fire;

import java.util.List;

import com.openclassrooms.safetynet.DTO.ResidentDTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the response of a fire-related request.
 * This class contains the list of residents living at a given address and the corresponding fire station number.
 */
@Data
public class FireResponseDTO {

	private List<ResidentDTO> personsLivingAtGivenAddress;
	private String stationNumber;
	
	public FireResponseDTO() {}
	
	public FireResponseDTO(List<ResidentDTO> persons, String stationNumber) {
		
		this.personsLivingAtGivenAddress = persons;
		this.stationNumber = stationNumber;
	}
}
