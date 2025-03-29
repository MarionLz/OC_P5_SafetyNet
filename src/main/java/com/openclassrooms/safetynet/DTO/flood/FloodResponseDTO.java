package com.openclassrooms.safetynet.DTO.flood;

import java.util.List;

import lombok.Data;

@Data
public class FloodResponseDTO {

	List<StationDTO> stationsSearched;
	
	public FloodResponseDTO(List<StationDTO> stationsSearched) {
		
		this.stationsSearched = stationsSearched;
	}
}
