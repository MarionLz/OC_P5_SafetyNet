package com.openclassrooms.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.MedicalHistoryDTO;
import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.ResidentDTO;
import com.openclassrooms.safetynet.DTO.flood.FloodResponseDTO;
import com.openclassrooms.safetynet.DTO.flood.HouseholdDTO;
import com.openclassrooms.safetynet.DTO.flood.StationDTO;
import com.openclassrooms.safetynet.model.DataModel;

@Service
public class FloodService {

	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger(FloodService.class);
    
    
    @Autowired
	public FloodService(DataModelService dataModelService) {
		
		this.dataModel = dataModelService.getDataModel();
	}
    
    private List<String> getFirestationAdresses(String station) {
		
		logger.debug("Starting to retrieve firestation addresses for station {}.", station);
		List<String> stationAddresses = dataModel.getFirestations().stream()
            .filter(firestation -> firestation.getStation().equals(station))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} addresses found for station {}.", stationAddresses.size(), station);
		return stationAddresses;
	}
    
    private List<PersonIdentityDTO> getPersonsIdentity(String address) {
    	
        logger.debug("Retrieving persons at address: {}", address);
        
		List<PersonIdentityDTO> personsIdentity = dataModel.getPersons().stream()
				.filter(person -> address.equals(person.getAddress()))
				.map(person -> new PersonIdentityDTO(person.getFirstName(), person.getLastName(), person.getPhone()))
				.collect(Collectors.toList());
		
        logger.debug("{} persons found at address: {}", personsIdentity.size(), address);
        
		return personsIdentity;
	}
    
	private int getAge(PersonIdentityDTO person) {
			
        logger.debug("Calculating age for person: {} {}", person.getFirstName(), person.getLastName());

		String birthdate = dataModel.getMedicalrecords().stream()
				.filter(medicalRecord -> person.getFirstName().equals(medicalRecord.getFirstName())
						&& person.getLastName().equals(medicalRecord.getLastName()))
				.map(medicalRecord -> medicalRecord.getBirthdate())
				.findFirst().orElse("");
		
		LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    LocalDate today = LocalDate.now();
	    int age = Period.between(birthdateLocalDate, today).getYears();
	    
        logger.debug("Age calculated for {} {}: {} years", person.getFirstName(), person.getLastName(), age);

		return age;
	}
	
	private MedicalHistoryDTO getMedicalHistory(PersonIdentityDTO person) {
		
        logger.debug("Retrieving medicalHistory for person : {}", person);

		MedicalHistoryDTO medicalHistory = dataModel.getMedicalrecords().stream()
				.filter(medicalRecord -> person.getFirstName().equals(medicalRecord.getFirstName())
						&& person.getLastName().equals(medicalRecord.getLastName()))
				.map(medicalRecord -> new MedicalHistoryDTO(medicalRecord.getMedications(), medicalRecord.getAllergies()))
				.findFirst().orElse(new MedicalHistoryDTO(new String[0], new String[0]));
		
        logger.debug("Medical history retrieved for {}", person);

		return medicalHistory;
	}
    
    public FloodResponseDTO getHouseholds(List<String> stationNumbers) {
    	
    	List<StationDTO> stations = new ArrayList<>();
    	
    	for(String stationNumber : stationNumbers) {
    		
    		List<HouseholdDTO> households = new ArrayList<>();
    		List<String> addresses = getFirestationAdresses(stationNumber);
    		
    		for(String address : addresses) {
    			
    			List<ResidentDTO> residents = new ArrayList<>();
    	    	List<PersonIdentityDTO> personsIdentity = getPersonsIdentity(address);
   
    	    	for (PersonIdentityDTO personIdentity : personsIdentity) {
    	    		
    	    		int age = getAge(personIdentity);
    	    		MedicalHistoryDTO medicalHistory = getMedicalHistory(personIdentity);
    	    		residents.add(new ResidentDTO(personIdentity.getLastName(), personIdentity.getPhone(),
    	    						String.valueOf(age), medicalHistory.getMedications(), medicalHistory.getAllergies()));
    	    	}
    	    	
    	    	households.add(new HouseholdDTO(address, residents));
    		}
    		
    		stations.add(new StationDTO(stationNumber, households));
    	}
    	
    	FloodResponseDTO response = new FloodResponseDTO(stations);
    	
    	return response;
    }
}
