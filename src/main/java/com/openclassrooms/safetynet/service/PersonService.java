package com.openclassrooms.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.IDataReaderRepository;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;

@Service
public class PersonService {

	private IDataWriterRepository writerRepository;
	private IDataReaderRepository readerRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public PersonService(IDataWriterRepository writerRepository, IDataReaderRepository readerRepository) {
        this.writerRepository = writerRepository;
        this.readerRepository = readerRepository;
        this.objectMapper = new ObjectMapper();
    }

    public void addPerson(String collectionName, Person person) {
    	
        ObjectNode personNode = objectMapper.valueToTree(person);
        
        writerRepository.add(collectionName, personNode);
        readerRepository.loadData();
    }

//    public void updatePerson(Person person) {
//        return writerRepository.update();
//    }
//
//    public void deletePerson(String firstName, String lastName) {
//        return writerRepository.delete();
//    }
}
