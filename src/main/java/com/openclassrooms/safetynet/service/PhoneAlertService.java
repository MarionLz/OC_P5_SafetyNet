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
	
	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger(PhoneAlertService.class);

    @Autowired
	public PhoneAlertService(DataReaderService dataService) {
		
		this.dataModel = dataService.getDataModel();
	}
    
	private List<String> getFirestationAdresses(String firestationNumber) {
		
		logger.debug("Starting to retrieve firestation addresses for station {}.", firestationNumber);
		List<String> stationAddresses = dataModel.getFirestations().stream()
            .filter(firestation -> firestation.getStation().equals(firestationNumber))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} addresses found for station {}.", stationAddresses.size(), firestationNumber);
		return stationAddresses;
	}
	
	public PhoneAlertResponseDTO getPhoneNumbersCoveredByStation(String firestationNumber) {
		
		logger.debug("Starting to retrieve phone numbers for station {}.", firestationNumber);
		
		List<String> stationAddresses = getFirestationAdresses(firestationNumber);
		List<String> response = dataModel.getPersons().stream()
	            .filter(person -> stationAddresses.contains(person.getAddress()))
	            .map(person -> person.getPhone())
	            .collect(Collectors.toList());
		
		logger.debug("Retrieval successful: {} phone numbers have been. Data is ready to be sent.", response.size());

		return new PhoneAlertResponseDTO(response);
	}
}
