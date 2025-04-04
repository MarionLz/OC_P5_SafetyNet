package com.openclassrooms.safetynet.service;

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
}
