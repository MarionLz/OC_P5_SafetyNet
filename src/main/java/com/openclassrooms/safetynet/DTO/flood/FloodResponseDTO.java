package com.openclassrooms.safetynet.DTO.flood;

import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the response related to flood stations.
 * This class holds information about the stations that have been searched for flood-related data.
 */
@Data
public class FloodResponseDTO {

	List<StationDTO> stationsSearched;
	
	public FloodResponseDTO(List<StationDTO> stationsSearched) {
		
		this.stationsSearched = stationsSearched;
	}
}
