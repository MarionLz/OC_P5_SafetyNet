package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class MedicalRecord {

	private String firstName;
	private String lastName;
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
