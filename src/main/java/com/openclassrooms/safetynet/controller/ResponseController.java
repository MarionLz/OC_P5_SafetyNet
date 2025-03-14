package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.DTO.PersonsByStationsDTO;
import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.service.*;

import java.io.IOException;
import java.util.ArrayList;
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

	/*@GetMapping("/persons")
	public List<Person> getAllPersons() {
		return service.getAllPersons();
	}*/
	
	/*
	@GetMapping("persons/dto")
    public List<PersonDTO> getAllPersonDTOs() {
        return service.getAllPersonDTOs();
    }*/
	
	@GetMapping("/firestation")
    public List<Object> getPersonsCoveredByStation(@RequestParam("stationNumber") String stationNumber) throws IOException {

		logger.info("Requête reçue pour /firestation avec stationNumber: {}", stationNumber);
		List<Object> response = service.getPersonsByStations(stationNumber);
		logger.info("Réponse réussie envoyée");
        return response;
    }
}

