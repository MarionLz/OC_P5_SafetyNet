package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;
import com.openclassrooms.safetynet.DTO.fire.FireResponseDTO;
import com.openclassrooms.safetynet.service.ChildAlertService;
import com.openclassrooms.safetynet.service.FireService;

@RestController
public class FireController {

	 private static final Logger logger = LogManager.getLogger(FireController.class);	

		@Autowired
		private FireService fireService;
		
		public FireController(FireService fireService) {
			this.fireService = fireService;
		}
		
		@GetMapping("/fire")
	    public FireResponseDTO getPersonsAtAddress(@RequestParam("address") String address) {

			logger.info("Request received for /fire with address: {}.", address);
			FireResponseDTO response = fireService.getPersonsAtAddress(address);
			logger.info("Request successful, response sent.");
	        return response;
	    }
}
