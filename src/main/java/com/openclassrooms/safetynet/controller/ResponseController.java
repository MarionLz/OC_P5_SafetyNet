package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.service.*;

import java.util.List;

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
	private PersonsCoveredByStationService service;
	
	@GetMapping("/firestation")
    public List<Object> getPersonsCoveredByStation(@RequestParam("stationNumber") String stationNumber) {

		logger.info("Requête reçue pour /firestation avec stationNumber: {}", stationNumber);
		List<Object> response = service.getPersonsByStations(stationNumber);
		logger.info("Requête réussie, réponse envoyée");
        return response;
    }
}

