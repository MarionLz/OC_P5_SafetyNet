package com.openclassrooms.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openclassrooms.safetynet.DTO.MedicalHistoryDTO;
import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsByStationsDTO;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;

public class ServiceUtils {

    private static final Logger logger = LogManager.getLogger(ServiceUtils.class);

    public static int getAge(String firstName, String lastName, List<MedicalRecord> medicalRecords) {
		
        logger.info("Calculating age for person: {} {}", firstName, lastName);

		String birthdate = medicalRecords.stream()
				.filter(medicalRecord -> firstName.equals(medicalRecord.getFirstName())
						&& lastName.equals(medicalRecord.getLastName()))
				.map(medicalRecord -> medicalRecord.getBirthdate())
				.findFirst().orElse("");
		
		LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    LocalDate today = LocalDate.now();
	    int age = Period.between(birthdateLocalDate, today).getYears();
	    
        logger.info("Age calculated for {} {}: {} years", firstName, lastName, age);

		return age;
	}
    
	public static MedicalHistoryDTO getMedicalHistory(String firstName, String lastName, List<MedicalRecord> medicalRecords) {
		
        logger.debug("Retrieving medicalHistory for person : {}", firstName, lastName);

		MedicalHistoryDTO medicalHistory = medicalRecords.stream()
				.filter(medicalRecord -> firstName.equals(medicalRecord.getFirstName())
						&& lastName.equals(medicalRecord.getLastName()))
				.map(medicalRecord -> new MedicalHistoryDTO(medicalRecord.getMedications(), medicalRecord.getAllergies()))
				.findFirst().orElse(new MedicalHistoryDTO(new String[0], new String[0]));
		
        logger.debug("Medical history retrieved for {}", firstName, lastName);

		return medicalHistory;
	}
	
    public static List<PersonIdentityDTO> getPersonsIdentity(String address, List<Person> persons) {
    	
        logger.debug("Retrieving persons at address: {}", address);
        
		List<PersonIdentityDTO> personsIdentity = persons.stream()
				.filter(person -> address.equals(person.getAddress()))
				.map(person -> new PersonIdentityDTO(person.getFirstName(), person.getLastName(), person.getPhone()))
				.collect(Collectors.toList());
		
        logger.debug("{} persons found at address: {}", personsIdentity.size(), address);
        
		return personsIdentity;
	}
    
	public static List<String> getFirestationAdresses(String stationNumber, List<Firestation> firestations) {
		
		logger.debug("Starting to retrieve firestation addresses for station {}.", stationNumber);
		List<String> stationAddresses = firestations.stream()
            .filter(firestation -> firestation.getStation().equals(stationNumber))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} addresses found for station {}.", stationAddresses.size(), stationNumber);
		return stationAddresses;
	}
	
	public static boolean isChild(PersonsByStationsDTO personByStation, List<MedicalRecord> medicalRecords) {
		
		String birthdate = medicalRecords.stream()
				.filter(medicalRecord -> personByStation.getFirstName().equals(medicalRecord.getFirstName())
						&& personByStation.getLastName().equals(medicalRecord.getLastName()))
				.map(medicalRecord -> medicalRecord.getBirthdate())
				.findFirst().orElse("");
		
		LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		return birthdateLocalDate.plusYears(18).isAfter(LocalDate.now());
	}
}
