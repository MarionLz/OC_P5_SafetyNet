package com.openclassrooms.safetynet.DTO.childAlert;

import java.util.List;

import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the response of a child alert.
 * This class holds the information related to children and other family members
 * present at a specific address in response to a child alert request.
 */
@Data
public class ChildAlertResponseDTO {
    
    private List<ChildDTO> children;
    private List<PersonIdentityDTO> otherFamilyMembers;

    public ChildAlertResponseDTO() {}

    public ChildAlertResponseDTO(List<ChildDTO> children, List<PersonIdentityDTO> otherFamilyMembers) {
        this.children = children;
        this.otherFamilyMembers = otherFamilyMembers;
    }
}
