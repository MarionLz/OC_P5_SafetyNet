package com.openclassrooms.safetynet.DTO.personInfo;

import java.util.List;

import com.openclassrooms.safetynet.DTO.ResidentDTO;

import lombok.Data;

@Data
public class PersonInfoResponseDTO {

	List<ResidentDTO> residents;
	
	public PersonInfoResponseDTO(List<ResidentDTO> residents) {
		this.residents = residents;
	}
}
