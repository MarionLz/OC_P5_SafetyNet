package com.openclassrooms.safetynet.DTO.fire;

import java.util.List;

import lombok.Data;

@Data
public class FireResponseDTO {

	private List<FirePersonsAtAddressDTO> habitants;
	private String stationNumber;
	
	public FireResponseDTO() {}
	
	public FireResponseDTO(List<FirePersonsAtAddressDTO> habitants, String stationNumber) {
		this.habitants = habitants;
		this.stationNumber = stationNumber;
	}
}
