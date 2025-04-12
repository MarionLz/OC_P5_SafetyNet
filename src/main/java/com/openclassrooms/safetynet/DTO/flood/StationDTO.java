package com.openclassrooms.safetynet.DTO.flood;

import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing a fire station in the flood response context.
 * This class holds information about the station number and the households covered by the station.
 */
@Data
public class StationDTO {

	private String stationNumber;
	private List<HouseholdDTO> households;

	public StationDTO(String stationNumber, List<HouseholdDTO> households) {
		
		this.stationNumber = stationNumber;
		this.households = households;
	}
}
