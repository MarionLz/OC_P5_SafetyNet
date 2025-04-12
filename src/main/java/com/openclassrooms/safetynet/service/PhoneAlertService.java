package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.phoneAlert.PhoneAlertResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;

/**
 * Service class for handling phone alert functionality.
 * This class retrieves phone numbers of persons covered by a specific fire station.
 */
@Service
public class PhoneAlertService {
    
    private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(PhoneAlertService.class);

    /**
     * Constructor for PhoneAlertService.
     * 
     * @param dataModelService the service that provides access to the data model
     */
    @Autowired
    public PhoneAlertService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }
    
    /**
     * Returns the current data model containing all persons and firestations.
     * 
     * @return the {@link DataModel}
     */
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }
    
    /**
     * Retrieves the phone numbers of all persons covered by a given fire station.
     * The phone numbers are retrieved for persons whose address matches one of the addresses covered by the fire station.
     * 
     * @param firestationNumber the number of the fire station to query
     * @return a {@link PhoneAlertResponseDTO} containing the list of phone numbers
     */
    public PhoneAlertResponseDTO getPhoneNumbersCoveredByStation(String firestationNumber) {
        
        logger.debug("Starting to retrieve phone numbers for station {}.", firestationNumber);
        
        DataModel dataModel = getDataModel();
        
        List<String> stationAddresses = ServiceUtils.getFirestationAdresses(firestationNumber, dataModel.getFirestations());
        
        List<String> response = dataModel.getPersons().stream()
            .filter(person -> stationAddresses.contains(person.getAddress()))
            .map(person -> person.getPhone())
            .collect(Collectors.toList());
        
        logger.debug("Retrieval successful: {} phone numbers have been retrieved. Data is ready to be sent.", response.size());

        return new PhoneAlertResponseDTO(response);
    }
}