package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    	
    	getDataModel().getPersons().add(person);
    	writerRepository.saveData();
    }
    
    public void updatePerson(Person updatedPerson) {
    	
        List<Person> persons = getDataModel().getPersons();

        for (int i = 0; i < persons.size(); i++) {
            Person current = persons.get(i);
            if (current.getFirstName().equals(updatedPerson.getFirstName()) && current.getLastName().equals(updatedPerson.getLastName())) {
                persons.set(i, updatedPerson);
                break;
            }
        }
    	writerRepository.saveData();
    }
    
    public void deletePerson(String firstName, String lastName) {
    	
        List<Person> updatedPersons =  getDataModel().getPersons().stream()
            .filter(person -> !(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)))
            .collect(Collectors.toList());

        getDataModel().setPersons(updatedPersons);
        writerRepository.saveData();    }
}
