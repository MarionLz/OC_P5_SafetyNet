package com.openclassrooms.safetynet.model;

import java.util.List;

import lombok.Data;

@Data
public class DataModel {

	private List<Person> persons;
	private List<Firestation> firestations;
	private List<MedicalRecord> medicalrecords;
	
	public DataModel() {}
	
}