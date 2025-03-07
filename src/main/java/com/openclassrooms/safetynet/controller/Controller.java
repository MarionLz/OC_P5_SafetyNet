package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired
	private PersonService service;

	@GetMapping("/persons")
	public List<Person> getAllPersons() {
		return service.getAllPersons();
	}
	
}
