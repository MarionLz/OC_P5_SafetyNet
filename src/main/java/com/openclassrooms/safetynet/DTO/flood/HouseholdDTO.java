package com.openclassrooms.safetynet.DTO.flood;

import java.util.List;

import com.openclassrooms.safetynet.DTO.ResidentDTO;

import lombok.Data;

@Data
public class HouseholdDTO {

	private String address;
	private List<ResidentDTO> residents;
	
	public HouseholdDTO(String address, List<ResidentDTO> residents) {
	
		this.address = address;
		this.residents = residents;
	}
}
