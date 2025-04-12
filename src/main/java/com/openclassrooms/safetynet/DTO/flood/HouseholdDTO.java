package com.openclassrooms.safetynet.DTO.flood;

import java.util.List;

import com.openclassrooms.safetynet.DTO.ResidentDTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing a household in the flood response context.
 * This class holds information about the address of the household and the residents living at that address.
 */
@Data
public class HouseholdDTO {

	private String address;
	private List<ResidentDTO> residents;
	
	public HouseholdDTO(String address, List<ResidentDTO> residents) {
	
		this.address = address;
		this.residents = residents;
	}
}
