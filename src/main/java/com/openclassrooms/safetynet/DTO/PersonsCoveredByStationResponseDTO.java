package com.openclassrooms.safetynet.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PersonsCoveredByStationResponseDTO {

    private List<PersonsByStationsDTO> persons;
    private NbAdultAndChildrenDTO nbAdultAndChildren;
    
    public PersonsCoveredByStationResponseDTO(List<PersonsByStationsDTO> persons, NbAdultAndChildrenDTO nbAdultAndChildren) {
        this.persons = persons;
        this.nbAdultAndChildren = nbAdultAndChildren;
    }
}
