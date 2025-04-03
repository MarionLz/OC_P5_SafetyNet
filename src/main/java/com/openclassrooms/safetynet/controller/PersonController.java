package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FireService;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    //private static final Logger logger = LogManager.getLogger(PersonController.class);	

    @Autowired
	private PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
    @PostMapping
    public void addPerson(@RequestBody Person person) {
    	
    	personService.addPerson("persons", person);
    }
    
//    @PutMapping
//    public void updatePerson(@RequestBody Person person) {
//    	
//    	personService.updatePerson();
//    	
//    }
//    
//    @DeleteMapping
//    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
//    	
//    	personService.deletePerson();
//    }
}
