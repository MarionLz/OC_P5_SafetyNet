package com.openclassrooms.safetynet.model;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Represents the data model containing lists of persons, firestations, and medical records.
 * This class is used to encapsulate the data for various entities in the system and ensure 
 * that the required fields are properly validated.
 */
@Data
public class DataModel {

	@NotEmpty(message = "Persons may not be empty.")
	@Valid
	private List<Person> persons;
	
	@NotEmpty(message = "Firestations may not be empty")
	@Valid
	private List<Firestation> firestations;
	
	@NotEmpty(message = "Medicalrecords may not be empty.")
	@Valid
	private List<MedicalRecord> medicalrecords;
	
	public DataModel() {}
		
	public DataModel(List<Person> persons, List<Firestation> firestations, List<MedicalRecord> medicalrecords) {
		
		this.persons = persons;
		this.firestations = firestations;
		this.medicalrecords = medicalrecords;
	}
}