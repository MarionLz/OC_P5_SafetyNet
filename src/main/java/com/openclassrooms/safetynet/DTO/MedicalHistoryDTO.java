package com.openclassrooms.safetynet.DTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the medical history of an individual.
 * It contains information about the person's medications and allergies.
 */
@Data
public class MedicalHistoryDTO {
	
	private String[] medications;
	private String[] allergies;
	
	public MedicalHistoryDTO(String[] medications, String[] allergies) {
		this.medications = medications;
		this.allergies = allergies;
	}
}
