package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.personInfo.PersonInfoResponseDTO;
import com.openclassrooms.safetynet.service.PersonInfoService;

/**
 * Controller for handling person information requests.
 * This class exposes an endpoint to retrieve information about a person based on their last name.
 */
@RestController
public class PersonInfoController {

    private static final Logger logger = LogManager.getLogger(PersonInfoController.class);

    @Autowired
    private PersonInfoService personInfoService;

    /**
     * Constructor for injecting the PersonInfoService.
     *
     * @param personInfoService The service used to retrieve person information.
     */
    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    /**
     * Method to get person information based on the last name.
     * This method handles the GET request for the /personInfo endpoint.
     *
     * @param lastName The last name of the person whose information is to be retrieved.
     * @return A PersonInfoResponseDTO containing the person's information.
     */
    @GetMapping("/personInfo")
    public PersonInfoResponseDTO getPersonInfo(@RequestParam("lastName") String lastName) {

        logger.info("Request GET received for /personInfo with lastName : {}.", lastName);
        PersonInfoResponseDTO response = personInfoService.getPersonInfoWithLastName(lastName);
        logger.info("Request GET successful, response sent.");
        return response;
    }
}