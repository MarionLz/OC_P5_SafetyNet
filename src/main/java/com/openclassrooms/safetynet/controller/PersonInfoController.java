package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.personInfo.PersonInfoResponseDTO;
import com.openclassrooms.safetynet.service.PersonInfoService;

@RestController
public class PersonInfoController {

	private static final Logger logger = LogManager.getLogger(PersonInfoController.class);	

	@Autowired
	private PersonInfoService personInfoService;
	
	public PersonInfoController(PersonInfoService personInfoService) {
		this.personInfoService = personInfoService;
	}
	
	@GetMapping("/personInfo")
    public PersonInfoResponseDTO getPersonInfo(@RequestParam("lastName") String lastName) {

		logger.info("Request GET received for /personInfo with lastName : {}.", lastName);
		PersonInfoResponseDTO response = personInfoService.getPersonInfoWithLastName(lastName);
		logger.info("Request GET successful, response sent.");
        return response;
    }
}
