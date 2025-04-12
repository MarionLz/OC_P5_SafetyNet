package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.service.CommunityEmailService;

/**
 * Controller for handling requests related to community emails.
 * This class exposes an endpoint to retrieve the list of community email addresses
 * based on a given city.
 */
@RestController
public class CommunityEmailController {

    private static final Logger logger = LogManager.getLogger(CommunityEmailController.class);

    @Autowired
    private CommunityEmailService communityEmailService;

    /**
     * Constructor for injecting the CommunityEmailService.
     *
     * @param communityEmailService The service used to retrieve community emails for a given city.
     */
    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    /**
     * Method to retrieve the list of community email addresses for a given city.
     * This method handles the GET request for the /communityEmail endpoint with the specified city.
     *
     * @param city The city to fetch community email addresses for.
     * @return A list of email addresses of the community members in the specified city.
     */
    @GetMapping("/communityEmail")
    public List<String> getCommunityEmails(@RequestParam("city") String city) {

        logger.info("Request GET received for /communityEmail with city: {}.", city);
        List<String> response = communityEmailService.getCommunityEmails(city);
        logger.info("Request GET successful, response sent.");
        return response;
    }
}