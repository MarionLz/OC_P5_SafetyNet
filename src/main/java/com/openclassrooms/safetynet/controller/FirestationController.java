package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.DTO.firestation.FirestationResponseDTO;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling firestation-related requests.
 * This class exposes endpoints for adding, updating, retrieving, and deleting firestations.
 */
@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private static final Logger logger = LogManager.getLogger("ResponseController");

    @Autowired
    private FirestationService firestationService;

    /**
     * Constructor for injecting the FirestationService.
     *
     * @param firestationService The service used to manage firestations.
     */
    public FirestationController(FirestationService personsCoveredByStationService) {
        this.firestationService = personsCoveredByStationService;
    }

    /**
     * Method to retrieve the list of persons covered by a specific firestation.
     * This method handles the GET request for the /firestation endpoint with the given station number.
     *
     * @param stationNumber The firestation number to fetch persons covered by the station.
     * @return A response DTO containing the list of persons covered by the specified firestation.
     */
    @GetMapping
    public FirestationResponseDTO getPersonsCoveredByStation(@RequestParam("stationNumber") String stationNumber) {

        logger.info("Request GET received for /firestation with stationNumber: {}.", stationNumber);
        FirestationResponseDTO response = firestationService.getPersonsByStations(stationNumber);
        logger.info("Request GET successful, response sent.");
        return response;
    }

    /**
     * Method to add a new firestation.
     * This method handles the POST request for the /firestation endpoint with a new firestation to be added.
     *
     * @param firestation The firestation object to be added.
     */
    @PostMapping
    public void addFirestation(@RequestBody Firestation firestation) {

        logger.info("Request POST received for /firestation with new Firestation : {}", firestation);
        firestationService.addFirestation(firestation);
        logger.info("Request POST successful, a new firestation has been added.");
    }

    /**
     * Method to update an existing firestation.
     * This method handles the PUT request for the /firestation endpoint with an updated firestation object.
     *
     * @param firestation The updated firestation object.
     */
    @PutMapping
    public void updateFirestation(@RequestBody Firestation firestation) {

        logger.info("Request PUT received for /firestation with updated Firestation : {}", firestation);
        firestationService.updateFirestation(firestation);
        logger.info("Request PUT successful, the firestation has been updated.");
    }

    /**
     * Method to delete a firestation.
     * This method handles the DELETE request for the /firestation endpoint with the specified address.
     *
     * @param address The address of the firestation to be deleted.
     */
    @DeleteMapping
    public void deleteFirestation(@RequestParam String address) {

        logger.info("Request DELETE received for /firestation at address : {}.", address);
        firestationService.deleteFirestation(address);
        logger.info("Request DELETE successful, the firestation has been deleted.");
    }
}