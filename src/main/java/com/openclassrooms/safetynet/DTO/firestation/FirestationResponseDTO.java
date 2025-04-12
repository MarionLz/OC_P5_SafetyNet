package com.openclassrooms.safetynet.DTO.firestation;

import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the response related to firestations.
 * This class holds information about persons associated with a fire station,
 * along with the number of adults and children covered by that station.
 */
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
