package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.service.CommunityEmailService;

@RestController
public class CommunityEmailController {

	private static final Logger logger = LogManager.getLogger(CommunityEmailController.class);	

	@Autowired
	private CommunityEmailService communityEmailService;
		
	public CommunityEmailController(CommunityEmailService communityEmailService) {
		
		this.communityEmailService = communityEmailService;
	}
	
	@GetMapping("/communityEmail")
    public List<String> getCommunityEmails(@RequestParam("city") String city) {

		logger.info("Request received for /communityEmail with city: {}.", city);
		List<String> response = communityEmailService.getCommunityEmails(city);
		logger.info("Request successful, response sent.");
        return response;
    }
}
