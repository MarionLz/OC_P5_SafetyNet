package com.openclassrooms.safetynet.DTO.fire;

import java.util.List;

import com.openclassrooms.safetynet.DTO.ResidentDTO;

import lombok.Data;

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
