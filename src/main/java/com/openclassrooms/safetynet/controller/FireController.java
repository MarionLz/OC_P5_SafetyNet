package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.fire.FireResponseDTO;
import com.openclassrooms.safetynet.service.FireService;

/**
 * Controller for handling fire-related requests.
 * This class exposes an endpoint to retrieve information about the people
 * present at a specific address during a fire emergency.
 */
@RestController
public class FireController {

    private static final Logger logger = LogManager.getLogger(FireController.class);

    @Autowired
    private FireService fireService;

    /**
     * Constructor for injecting the FireService.
     *
     * @param fireService The service used to retrieve information about persons at a given address during a fire.
     */
    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    /**
     * Method to retrieve information about persons at a specific address during a fire emergency.
     * This method handles the GET request for the /fire endpoint with the specified address.
     *
     * @param address The address to fetch information about persons at the time of the fire.
     * @return A DTO response containing the details of the persons at the specified address during the fire.
     */
    @GetMapping("/fire")
    public FireResponseDTO getPersonsAtAddress(@RequestParam("address") String address) {

        logger.info("Request GET received for /fire with address: {}.", address);
        FireResponseDTO response = fireService.getPersonsAtAddress(address);
        logger.info("Request GET successful, response sent.");
        return response;
    }
}
