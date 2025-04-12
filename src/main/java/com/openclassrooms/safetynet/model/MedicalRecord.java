package com.openclassrooms.safetynet.model;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a medical record of a person, including their personal information
 * and medical history such as medications and allergies.
 */
@Data
public class MedicalRecord {

	@NotBlank(message = "FisrtName may not be empty.")
	@Valid
	private String firstName;
	
	@NotBlank(message = "LastName may not be empty.")
	@Valid
	private String lastName;
	
	@NotBlank(message = "Birthdate may not be empty.")
	@Valid
	private String birthdate;
	
	private String[] medications;
	
	private String[] allergies;
	
	public MedicalRecord() {}
	
	public MedicalRecord(String firstName, String lastName, String birthdate, String[] medications, String[] allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}
}
