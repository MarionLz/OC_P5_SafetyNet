package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.DTO.flood.FloodResponseDTO;
import com.openclassrooms.safetynet.service.FloodService;

@RestController
public class FloodController {

	private static final Logger logger = LogManager.getLogger(FloodController.class);	
	
	@Autowired
	private FloodService floodService;
	
	public FloodController(FloodService floodService) {
		this.floodService = floodService;
	}
	
	@GetMapping("/flood/stations")
    public FloodResponseDTO getHouseholdsServedByStation(@RequestParam("stations") List<String> stationNumbers) {

		logger.info("Request GET received for /flood/station with stations : {}.", stationNumbers);
		FloodResponseDTO response = floodService.getHouseholds(stationNumbers);
		logger.info("Request GET successful, response sent.");
        return response;
    }
}
