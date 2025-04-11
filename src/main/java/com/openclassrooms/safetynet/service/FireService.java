package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.MedicalHistoryDTO;
import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.ResidentDTO;
import com.openclassrooms.safetynet.DTO.fire.FireResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;

@Service
public class FireService {

	private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(FireService.class);
    
    @Autowired
	public FireService(DataModelService dataModelService) {
		
		this.dataModelService = dataModelService;
	}
    
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }
    
    public FireResponseDTO getPersonsAtAddress(String address) {
    	
        logger.debug("Starting to retrieve persons who lived at this address : ", address);
    	List<ResidentDTO> personsResult = new ArrayList<>();
    	List<PersonIdentityDTO> personsIdentity = ServiceUtils.getPersonsIdentity(address, getDataModel().getPersons());
    	
    	for (PersonIdentityDTO personIdentity : personsIdentity) {
    		
    		int age = ServiceUtils.getAge(personIdentity.getFirstName(), personIdentity.getLastName(), getDataModel().getMedicalrecords());
    		MedicalHistoryDTO medicalHistory = ServiceUtils.getMedicalHistory(personIdentity.getFirstName(), personIdentity.getLastName(), getDataModel().getMedicalrecords());
    		personsResult.add(new ResidentDTO(personIdentity.getLastName(), personIdentity.getPhone(),
    						String.valueOf(age), medicalHistory.getMedications(), medicalHistory.getAllergies()));
    	}
    	
    	String stationNumber = getDataModel().getFirestations().stream()
    			.filter(firestation -> address.equals(firestation.getAddress()))
    			.map(firestation -> firestation.getStation())
    			.findFirst().orElse("");
    	
    	FireResponseDTO response = new FireResponseDTO(personsResult, stationNumber);
		
    	logger.debug("Retrieval successful: {} persons found at adress: {}.", personsResult.size(), address);
    	return response;
    }
}
