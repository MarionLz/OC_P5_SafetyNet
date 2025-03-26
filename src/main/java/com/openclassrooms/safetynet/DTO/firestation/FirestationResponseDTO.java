package com.openclassrooms.safetynet.DTO.firestation;

import java.util.List;

import lombok.Data;

@Data
public class FirestationResponseDTO {

    private List<PersonsByStationsDTO> persons;
    private NbAdultAndChildrenDTO nbAdultAndChildren;
    
    public FirestationResponseDTO(List<PersonsByStationsDTO> persons, NbAdultAndChildrenDTO nbAdultAndChildren) {
        this.persons = persons;
        this.nbAdultAndChildren = nbAdultAndChildren;
    }
}
