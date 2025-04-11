package com.openclassrooms.safetynet.service;

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

	private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(FloodService.class);
    
    
    @Autowired
	public FloodService(DataModelService dataModelService) {
		
		this.dataModelService = dataModelService;
	}
    
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }
    
    private List<String> getFirestationAdresses(String station) {
		
		logger.debug("Starting to retrieve firestation addresses for station {}.", station);
		List<String> stationAddresses = getDataModel().getFirestations().stream()
            .filter(firestation -> firestation.getStation().equals(station))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} addresses found for station {}.", stationAddresses.size(), station);
		return stationAddresses;
	}
    
    public FloodResponseDTO getHouseholds(List<String> stationNumbers) {
    	
    	DataModel dataModel = getDataModel();
    	List<StationDTO> stations = new ArrayList<>();
    	
    	for(String stationNumber : stationNumbers) {
    		
    		List<HouseholdDTO> households = new ArrayList<>();
    		List<String> addresses = getFirestationAdresses(stationNumber);
    		
    		for(String address : addresses) {
    			
    			List<ResidentDTO> residents = new ArrayList<>();
    	    	List<PersonIdentityDTO> personsIdentity = ServiceUtils.getPersonsIdentity(address, dataModel.getPersons());
   
    	    	for (PersonIdentityDTO personIdentity : personsIdentity) {
    	    		
    	    		int age = ServiceUtils.getAge(personIdentity.getFirstName(), personIdentity.getLastName(), dataModel.getMedicalrecords());
    	    		MedicalHistoryDTO medicalHistory = ServiceUtils.getMedicalHistory(personIdentity.getFirstName(), personIdentity.getLastName(), dataModel.getMedicalrecords());
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
