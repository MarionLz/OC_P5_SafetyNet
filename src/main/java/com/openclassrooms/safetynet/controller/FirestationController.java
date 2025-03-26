package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.DTO.firestation.FirestationResponseDTO;
import com.openclassrooms.safetynet.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationController {
	
    private static final Logger logger = LogManager.getLogger("ResponseController");	

	@Autowired
	private FirestationService personsCoveredByStationService;
	
	public FirestationController(FirestationService personsCoveredByStationService) {
		this.personsCoveredByStationService = personsCoveredByStationService;
	}
	
	@GetMapping("/firestation")
    public FirestationResponseDTO getPersonsCoveredByStation(@RequestParam("stationNumber") String stationNumber) {

		logger.info("Request received for /firestation with stationNumber: {}.", stationNumber);
		FirestationResponseDTO response = personsCoveredByStationService.getPersonsByStations(stationNumber);
		logger.info("Request successful, response sent.");
        return response;
    }
}

