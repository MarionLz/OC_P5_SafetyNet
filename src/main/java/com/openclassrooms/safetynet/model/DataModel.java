package com.openclassrooms.safetynet.model;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

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