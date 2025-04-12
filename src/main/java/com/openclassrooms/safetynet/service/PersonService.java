package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;
import com.openclassrooms.safetynet.exceptions.ResourceNotFoundException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;

/**
 * Service class for managing persons in the system.
 * This class allows adding, updating, and deleting person records in the data model.
 */
@Service
public class PersonService {

    private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(PersonService.class);    

    private IDataWriterRepository writerRepository;

    /**
     * Constructor for PersonService.
     * 
     * @param writerRepository the repository responsible for saving the data
     * @param dataModelService the service that provides access to the data model
     */
    @Autowired
    public PersonService(IDataWriterRepository writerRepository, DataModelService dataModelService) {
        this.writerRepository = writerRepository;
        this.dataModelService = dataModelService;
    }

    /**
     * Returns the current data model containing all persons.
     * 
     * @return the {@link DataModel}
     */
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }

    /**
     * Adds a new person to the data model.
     * If a person with the same first and last name already exists, an exception is thrown.
     * 
     * @param person the person to be added
     * @throws ResourceAlreadyExistsException if the person already exists in the data model
     */
    public void addPerson(Person person) {
        logger.debug("Starting to add person: {}", person);
        List<Person> persons = getDataModel().getPersons();

        boolean exists = persons.stream()
            .anyMatch(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
                        && p.getLastName().equalsIgnoreCase(person.getLastName()));

        if (exists) {
            logger.error("Person already exists: " + person.getFirstName() + " " + person.getLastName());
            throw new ResourceAlreadyExistsException("Person already exists: " + person.getFirstName() + " " + person.getLastName());
        }

        persons.add(person);
        writerRepository.saveData();
        logger.debug("New person added: {}.", person);
    }

    /**
     * Updates the details of an existing person in the data model.
     * If the person does not exist, an exception is thrown.
     * 
     * @param updatedPerson the person with updated details
     * @throws ResourceNotFoundException if the person is not found in the data model
     */
    public void updatePerson(Person updatedPerson) {
        logger.debug("Starting to update person: {}", updatedPerson);
        List<Person> persons = getDataModel().getPersons();

        boolean exists = false;
        for (int i = 0; i < persons.size(); i++) {
            Person current = persons.get(i);
            if (current.getFirstName().equals(updatedPerson.getFirstName()) && current.getLastName().equals(updatedPerson.getLastName())) {
                persons.set(i, updatedPerson);
                exists = true;
                break;
            }
        }

        if (!exists) {
            logger.error("Person not found: " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName());
            throw new ResourceNotFoundException("Person not found: " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName());
        }
        writerRepository.saveData();
        logger.debug("Person updated: {}.", updatedPerson);
    }

    /**
     * Deletes a person from the data model by their first and last name.
     * If the person does not exist, an exception is thrown.
     * 
     * @param firstName the first name of the person to be deleted
     * @param lastName the last name of the person to be deleted
     * @throws ResourceNotFoundException if the person is not found in the data model
     */
    public void deletePerson(String firstName, String lastName) {
        logger.debug("Starting to delete person: {} {}.", firstName, lastName);
        List<Person> persons = getDataModel().getPersons();

        boolean exists = persons.stream()
            .anyMatch(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName));

        if (!exists) {
            logger.error("Person not found: " + firstName + " " + lastName);
            throw new ResourceNotFoundException("Person not found: " + firstName + " " + lastName);
        }

        List<Person> updatedPersons = getDataModel().getPersons().stream()
            .filter(person -> !(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)))
            .collect(Collectors.toList());

        getDataModel().setPersons(updatedPersons);
        writerRepository.saveData();
        logger.debug("Person deleted: {} {}.", firstName, lastName);
    }
}