package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.service.*;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling child alert requests.
 * This class exposes an endpoint to retrieve a list of children at a given address.
 */
@RestController
public class ChildAlertController {
    
    private static final Logger logger = LogManager.getLogger("ChildAlertController");

    @Autowired
    private ChildAlertService childAlertService;

    /**
     * Constructor for injecting the child alert service.
     * 
     * @param childAlertService The service used to retrieve children at a specific address.
     */
    public ChildAlertController(ChildAlertService childAlertService) {
        this.childAlertService = childAlertService;
    }

    /**
     * Method to retrieve information about children at a given address.
     * This method handles the GET request for the /childAlert endpoint with the specified address.
     * 
     * @param address The address to fetch children information for.
     * @return A DTO response containing the list of children at the specified address.
     */
    @GetMapping("/childAlert")
    public ChildAlertResponseDTO getChildAtAddress(@RequestParam("address") String address) {
        
        logger.info("Request GET received for /childAlert with address: {}.", address);
        ChildAlertResponseDTO response = childAlertService.getChildrenAtAddress(address);
        logger.info("Request GET successful, response sent.");
        return response;
    }
}