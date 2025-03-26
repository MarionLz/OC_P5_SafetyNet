package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.service.*;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildAlertController {
	
    private static final Logger logger = LogManager.getLogger("ChildAlertController");	

	@Autowired
	private ChildAlertService childAlertService;
	
	public ChildAlertController(ChildAlertService childAlertService) {
		this.childAlertService = childAlertService;
	}
	
	@GetMapping("/childAlert")
    public ChildAlertResponseDTO getChildAtAddress(@RequestParam("address") String address) {

		logger.info("Request received for /childAlert with address: {}.", address);
		ChildAlertResponseDTO response = childAlertService.getChildAtAddress(address);
		logger.info("Request successful, response sent.");
        return response;
    }
}

