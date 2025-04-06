package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.DTO.firestation.FirestationResponseDTO;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firestation")
public class FirestationController {
	
    private static final Logger logger = LogManager.getLogger("ResponseController");	

	@Autowired
	private FirestationService firestationService;
	
	public FirestationController(FirestationService personsCoveredByStationService) {
		this.firestationService = personsCoveredByStationService;
	}
	
	@GetMapping
    public FirestationResponseDTO getPersonsCoveredByStation(@RequestParam("stationNumber") String stationNumber) {

		logger.info("Request received for /firestation with stationNumber: {}.", stationNumber);
		FirestationResponseDTO response = firestationService.getPersonsByStations(stationNumber);
		logger.info("Request successful, response sent.");
        return response;
    }
	
    @PostMapping
    public void addFirestation(@RequestBody Firestation firestation) {
    	
		logger.info("Request POST received for /firestation with new Firestation : {}", firestation);
		firestationService.addFirestation(firestation);
		logger.info("Request POST successful, a new firestation has been added.");
    }
    
    @PutMapping
    public void updateFirestation(@RequestBody Firestation firestation) {
    	
		logger.info("Request PUT received for /firestation with updated Firestation : {}", firestation);
		firestationService.updateFirestation(firestation);
		logger.info("Request PUT successful, the firestation has been updated.");
    }
    
    @DeleteMapping
    public void deleteFirestation(@RequestParam String address) {
    	
		logger.info("Request DELETE received for /firestation at address : {}.", address);
		firestationService.deleteFirestation(address);
		logger.info("Request DELETE successful, the firestation has been deleted.");
    }
}

