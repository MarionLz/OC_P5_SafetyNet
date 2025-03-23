package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.DTO.PersonsCoveredByStationResponseDTO;
import com.openclassrooms.safetynet.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseController {
	
    private static final Logger logger = LogManager.getLogger("ResponseController");	

	@Autowired
	private PersonsCoveredByStationService personsCoveredByStationService;
	
	public ResponseController(PersonsCoveredByStationService personsCoveredByStationService) {
		this.personsCoveredByStationService = personsCoveredByStationService;
	}
	
	@GetMapping("/firestation")
    public PersonsCoveredByStationResponseDTO getPersonsCoveredByStation(@RequestParam("stationNumber") String stationNumber) {

		logger.info("Request received for /firestation with stationNumber: {}.", stationNumber);
		PersonsCoveredByStationResponseDTO response = personsCoveredByStationService.getPersonsByStations(stationNumber);
		logger.info("Request successful, response sent.");
        return response;
    }
}

