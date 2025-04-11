package com.openclassrooms.safetynet.service;

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

	private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(PersonInfoService.class);
    
    @Autowired
	public PersonInfoService(DataModelService dataModelService) {
		
		this.dataModelService = dataModelService;
	}
    
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }
	
    public PersonInfoResponseDTO getPersonInfoWithLastName(String lastName) {
    	
        logger.debug("Retrieving persons with lastName: {}", lastName);
    	DataModel dataModel = getDataModel();
    	List<Person> persons = dataModel.getPersons();
    	List<PersonInfoPersonIdentityDTO> personsWithSameLastName = new ArrayList<>();
    	
    	for (Person person : persons) {
    		if (person.getLastName().equals(lastName)) {
    			
    			int age = ServiceUtils.getAge(person.getFirstName(), lastName, dataModel.getMedicalrecords());
    			MedicalHistoryDTO medicalHistory = ServiceUtils.getMedicalHistory(person.getFirstName(), lastName, dataModel.getMedicalrecords());
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
    	logger.debug("Retrieval successful: {} persons found with lastName: {}.", personsWithSameLastName.size(), lastName);
    	return response;
    }
}
