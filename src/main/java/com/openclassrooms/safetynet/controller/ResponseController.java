package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.service.*;

import DTO.PersonsByStationsDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseController {
	
	@Autowired
	private ResponseService service;

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
    public ArrayList getPersonsCoveredByStation(@RequestParam("stationNumber") String stationNumber) {
        return service.getPersonsByStations(stationNumber);
    }
}

