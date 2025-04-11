package com.openclassrooms.safetynet.DTO.firestation;

import java.util.List;

import lombok.Data;

@Data
public class FirestationResponseDTO {

    private List<PersonsByStationsDTO> persons;
	private int nbAdult;
	private int nbChildren;
    
    public FirestationResponseDTO(List<PersonsByStationsDTO> persons, int nbAdult, int nbChildren) {
        this.persons = persons;
		this.nbAdult = nbAdult;
		this.nbChildren = nbChildren;
    }
}
