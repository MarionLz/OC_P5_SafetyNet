package com.openclassrooms.safetynet.DTO.flood;

import java.util.List;

import lombok.Data;

@Data
public class StationDTO {

	private String stationNumber;
	private List<HouseholdDTO> households;
	
	public StationDTO(String stationNumber, List<HouseholdDTO> households) {
		
		this.stationNumber = stationNumber;
		this.households = households;
	}
}
