package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.flood.FloodResponseDTO;
import com.openclassrooms.safetynet.service.FloodService;

/**
 * Controller for handling flood-related requests.
 * This class exposes an endpoint to retrieve information about the households served by specific fire stations during a flood event.
 */
@RestController
public class FloodController {

    private static final Logger logger = LogManager.getLogger(FloodController.class);

    @Autowired
    private FloodService floodService;

    /**
     * Constructor for injecting the FloodService.
     *
     * @param floodService The service used to retrieve information about households served by flood stations.
     */
    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    /**
     * Method to retrieve the list of households served by the specified fire stations during a flood event.
     * This method handles the GET request for the /flood/stations endpoint with the specified station numbers.
     *
     * @param stationNumbers A list of fire station numbers to fetch households served during the flood.
     * @return A DTO response containing the households served by the specified stations during the flood.
     */
    @GetMapping("/flood/stations")
    public FloodResponseDTO getHouseholdsServedByStation(@RequestParam("stations") List<String> stationNumbers) {

        logger.info("Request GET received for /flood/station with stations : {}.", stationNumbers);
        FloodResponseDTO response = floodService.getHouseholds(stationNumbers);
        logger.info("Request GET successful, response sent.");
        return response;
    }
}