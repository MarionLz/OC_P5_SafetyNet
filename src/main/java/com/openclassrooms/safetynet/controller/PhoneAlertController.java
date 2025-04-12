package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.DTO.phoneAlert.PhoneAlertResponseDTO;
import com.openclassrooms.safetynet.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling phone alert requests.
 * This class exposes an endpoint to retrieve phone numbers covered by a specified fire station.
 */
@RestController
public class PhoneAlertController {

    private static final Logger logger = LogManager.getLogger(PhoneAlertController.class);

    @Autowired
    private PhoneAlertService phoneAlertService;

    /**
     * Constructor for injecting the PhoneAlertService.
     *
     * @param phoneAlertService The service used to get phone numbers covered by a fire station.
     */
    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService = phoneAlertService;
    }

    /**
     * Method to retrieve phone numbers covered by a specific fire station.
     * This method handles the GET request for the /phoneAlert endpoint.
     *
     * @param firestationNumber The fire station number to get the covered phone numbers.
     * @return A PhoneAlertResponseDTO containing the phone numbers covered by the fire station.
     */
    @GetMapping("/phoneAlert")
    public PhoneAlertResponseDTO getPhoneNumbersCoveredByStation(@RequestParam("firestation") String firestationNumber) {

        logger.info("Request GET received for /phoneAlert with firestationNumber: {}.", firestationNumber);
        PhoneAlertResponseDTO response = phoneAlertService.getPhoneNumbersCoveredByStation(firestationNumber);
        logger.info("Request GET successful, response sent.");
        return response;
    }
}