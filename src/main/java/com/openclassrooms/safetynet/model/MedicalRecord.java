package com.openclassrooms.safetynet.model;

public class MedicalRecord {

	private String firstName;
	private String lastName;
	private String birthDate;
	private String[] medications;
	private String[] allergies;
	
	public MedicalRecord() {}
	
	public MedicalRecord(String firstName, String lastName, String birthDate, String[] medications, String[] allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.medications = medications;
		this.allergies = allergies;
	}
}
