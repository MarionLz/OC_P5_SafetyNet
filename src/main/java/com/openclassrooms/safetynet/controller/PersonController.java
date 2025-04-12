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
import com.openclassrooms.safetynet.service.PersonService;

/**
 * Controller for handling person-related requests.
 * This class exposes endpoints for adding, updating, and deleting person records.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    /**
     * Constructor for injecting the PersonService.
     *
     * @param personService The service used to manage person records.
     */
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Method to add a new person.
     * This method handles the POST request for the /person endpoint with a new person.
     *
     * @param person The person object to be added.
     */
    @PostMapping
    public void addPerson(@RequestBody Person person) {

        logger.info("Request POST received for /person with new Person : {}", person);
        personService.addPerson(person);
        logger.info("Request POST successful, a new person has been added.");
    }

    /**
     * Method to update an existing person.
     * This method handles the PUT request for the /person endpoint with an updated person object.
     *
     * @param person The updated person object.
     */
    @PutMapping
    public void updatePerson(@RequestBody Person person) {

        logger.info("Request PUT received for /person with updated Person : {}", person);
        personService.updatePerson(person);
        logger.info("Request PUT successful, the person has been updated.");
    }

    /**
     * Method to delete a person.
     * This method handles the DELETE request for the /person endpoint with the specified first and last names.
     *
     * @param firstName The first name of the person to be deleted.
     * @param lastName The last name of the person to be deleted.
     */
    @DeleteMapping
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {

        logger.info("Request DELETE received for /person with firstName : {} and lastName : {}.", firstName, lastName);   
        personService.deletePerson(firstName, lastName);
        logger.info("Request DELETE successful, the person has been deleted.");
    }
}