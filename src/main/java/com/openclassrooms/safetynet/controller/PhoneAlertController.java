package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.DTO.phoneAlert.PhoneAlertResponseDTO;
import com.openclassrooms.safetynet.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneAlertController {

	 private static final Logger logger = LogManager.getLogger(PhoneAlertController.class);	

		@Autowired
		private PhoneAlertService phoneAlertService;
		
		public PhoneAlertController(PhoneAlertService phoneAlertService) {
			this.phoneAlertService = phoneAlertService;
		}
		
		@GetMapping("/phoneAlert")
	    public PhoneAlertResponseDTO getPhoneNumbersCoveredByStation(@RequestParam("firestationNumber") String firestationNumber) {

			logger.info("Request received for /phoneAlert with firestationNumber: {}.", firestationNumber);
			PhoneAlertResponseDTO response = phoneAlertService.getPhoneNumbersCoveredByStation(firestationNumber);
			logger.info("Request successful, response sent.");
	        return response;
	    }
}
