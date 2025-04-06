package com.openclassrooms.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.MedicalHistoryDTO;
import com.openclassrooms.safetynet.DTO.personInfo.PersonInfoPersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.personInfo.PersonInfoResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Person;

@Service
public class PersonInfoService {

	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger(PersonInfoService.class);
    
    
    @Autowired
	public PersonInfoService(DataModelService dataModelService) {
		
		this.dataModel = dataModelService.getDataModel();
	}
    
	private int getAge(String firstName, String lastName) {
		
        logger.debug("Calculating age for person: {} {}", firstName, lastName);

		String birthdate = dataModel.getMedicalrecords().stream()
				.filter(medicalRecord -> firstName.equals(medicalRecord.getFirstName())
						&& lastName.equals(medicalRecord.getLastName()))
				.map(medicalRecord -> medicalRecord.getBirthdate())
				.findFirst().orElse("");
		
		LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    LocalDate today = LocalDate.now();
	    int age = Period.between(birthdateLocalDate, today).getYears();
	    
        logger.debug("Age calculated for {} {}: {} years", firstName, lastName, age);

		return age;
	}
    
	private MedicalHistoryDTO getMedicalHistory(String firstName, String lastName) {
		
        logger.debug("Retrieving medicalHistory for person : {} {}", firstName, lastName);

		MedicalHistoryDTO medicalHistory = dataModel.getMedicalrecords().stream()
				.filter(medicalRecord -> firstName.equals(medicalRecord.getFirstName())
						&& lastName.equals(medicalRecord.getLastName()))
				.map(medicalRecord -> new MedicalHistoryDTO(medicalRecord.getMedications(), medicalRecord.getAllergies()))
				.findFirst().orElse(new MedicalHistoryDTO(new String[0], new String[0]));
		
        logger.debug("Medical history retrieved for {} {}", firstName, lastName);

		return medicalHistory;
	}
	
    public PersonInfoResponseDTO getPersonInfoWithLastName(String lastName) {
    	
    	List<Person> persons = dataModel.getPersons();
    	List<PersonInfoPersonIdentityDTO> personsWithSameLastName = new ArrayList<>();
    	
    	for (Person person : persons) {
    		if (person.getLastName().equals(lastName)) {
    			
    			int age = getAge(person.getFirstName(), lastName);
    			MedicalHistoryDTO medicalHistory = getMedicalHistory(person.getFirstName(), lastName);
    			personsWithSameLastName.add(
    					new PersonInfoPersonIdentityDTO(
    							lastName,
    							person.getAddress(),
    							String.valueOf(age),
    							person.getEmail(),
    							medicalHistory.getMedications(),
    							medicalHistory.getAllergies()
    					)
    			);
    		}
    	}
    	
    	PersonInfoResponseDTO response = new PersonInfoResponseDTO(personsWithSameLastName);
    	
    	return response;
    }
}
