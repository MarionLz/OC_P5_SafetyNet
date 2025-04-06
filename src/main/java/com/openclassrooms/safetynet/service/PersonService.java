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

@Service
public class PersonService {

	private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(PersonService.class);	
	
	private IDataWriterRepository writerRepository;
    
    @Autowired
	public PersonService(IDataWriterRepository writerRepository, DataModelService dataModelService) {
		
    	this.writerRepository = writerRepository;
		this.dataModelService = dataModelService;
	}
    
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }

    public void addPerson(Person person) {
    	
    	List<Person> persons = getDataModel().getPersons();

        boolean exists = persons.stream()
            .anyMatch(p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
                        && p.getLastName().equalsIgnoreCase(person.getLastName()));

        if (exists) {
        	logger.error("Person already exists : " + person.getFirstName() + " " + person.getLastName());
            throw new ResourceAlreadyExistsException("Person already exists : " + person.getFirstName() + " " + person.getLastName());
        }

        persons.add(person);
        writerRepository.saveData();
    }
    
    public void updatePerson(Person updatedPerson) {
    	
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
        	logger.error("Person not found : " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName());
        	throw new ResourceNotFoundException("Person not found : " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName());
        }
    	writerRepository.saveData();
    }
    
    public void deletePerson(String firstName, String lastName) {
    	
    	List<Person> persons = getDataModel().getPersons();

        boolean exists = persons.stream()
            .anyMatch(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName));
        
        if (!exists) {
        	logger.error("Person not found : " + firstName + " " + lastName);
        	throw new ResourceNotFoundException("Person not found : " + firstName + " " + lastName);
        }
        
    	List<Person> updatedPersons =  getDataModel().getPersons().stream()
            .filter(person -> !(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)))
            .collect(Collectors.toList());

        getDataModel().setPersons(updatedPersons);
        writerRepository.saveData();
    }
}
