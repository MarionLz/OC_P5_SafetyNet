package com.openclassrooms.safetynet.DTO.firestation;

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
