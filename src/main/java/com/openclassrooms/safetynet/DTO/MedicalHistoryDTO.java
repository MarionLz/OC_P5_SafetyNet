package com.openclassrooms.safetynet.DTO;

import lombok.Data;

@Data
public class MedicalHistoryDTO {
	
	private String[] medications;
	private String[] allergies;
	
	public MedicalHistoryDTO(String[] medications, String[] allergies) {
		this.medications = medications;
		this.allergies = allergies;
	}
}
