package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.phoneAlert.PhoneAlertResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;

@Service
public class PhoneAlertService {
	
	private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(PhoneAlertService.class);

    @Autowired
	public PhoneAlertService(DataModelService dataModelService) {
		
		this.dataModelService = dataModelService;
	}
    
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }
	
	public PhoneAlertResponseDTO getPhoneNumbersCoveredByStation(String firestationNumber) {
		
		logger.debug("Starting to retrieve phone numbers for station {}.", firestationNumber);
		
		DataModel dataModel = getDataModel();
		List<String> stationAddresses = ServiceUtils.getFirestationAdresses(firestationNumber, dataModel.getFirestations());
		List<String> response = dataModel.getPersons().stream()
	            .filter(person -> stationAddresses.contains(person.getAddress()))
	            .map(person -> person.getPhone())
	            .collect(Collectors.toList());
		
		logger.debug("Retrieval successful: {} phone numbers have been. Data is ready to be sent.", response.size());

		return new PhoneAlertResponseDTO(response);
	}
}
