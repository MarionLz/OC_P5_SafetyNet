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

/**
 * Service class responsible for retrieving information about residents at a specific address
 * and the associated fire station.
 * This service is used primarily by the `/fire` endpoint to respond with a list of individuals
 * living at a given address, their age, contact, and medical information, along with the station number.
 */
@Service
public class FireService {

    private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(FireService.class);

    /**
     * Constructor for FireService.
     *
     * @param dataModelService the service used to access the in-memory data model
     */
    @Autowired
    public FireService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    /**
     * Retrieves the current DataModel instance.
     *
     * @return the application's in-memory {@link DataModel}
     */
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }

    /**
     * Retrieves all persons living at the specified address, their personal and medical information,
     * and the fire station number responsible for that address.
     *
     * @param address the address to look up
     * @return a {@link FireResponseDTO} containing the list of residents and the station number
     */
    public FireResponseDTO getPersonsAtAddress(String address) {

        logger.debug("Starting to retrieve persons who lived at this address: {}", address);

        List<ResidentDTO> personsResult = new ArrayList<>();
        List<PersonIdentityDTO> personsIdentity = ServiceUtils.getPersonsIdentity(address, getDataModel().getPersons());

        for (PersonIdentityDTO personIdentity : personsIdentity) {
            int age = ServiceUtils.getAge(personIdentity.getFirstName(), personIdentity.getLastName(), getDataModel().getMedicalrecords());
            MedicalHistoryDTO medicalHistory = ServiceUtils.getMedicalHistory(personIdentity.getFirstName(), personIdentity.getLastName(), getDataModel().getMedicalrecords());

            personsResult.add(new ResidentDTO(
                    personIdentity.getLastName(),
                    personIdentity.getPhone(),
                    String.valueOf(age),
                    medicalHistory.getMedications(),
                    medicalHistory.getAllergies()
            ));
        }

        String stationNumber = getDataModel().getFirestations().stream()
                .filter(firestation -> address.equals(firestation.getAddress()))
                .map(firestation -> firestation.getStation())
                .findFirst()
                .orElse("");

        logger.debug("Retrieval successful: {} persons found at address: {}.", personsResult.size(), address);

        return new FireResponseDTO(personsResult, stationNumber);
    }
}