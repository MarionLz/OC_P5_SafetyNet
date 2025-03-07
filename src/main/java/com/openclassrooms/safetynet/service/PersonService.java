package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynet.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PersonService {

	private List<Person> persons;
	
	public PersonService() throws IOException {
		loadDataFromJson();
	}
	
	private void loadDataFromJson() throws IOException {
				
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data_test.json");
			
			if (inputStream != null)
				persons = objectMapper.readValue(inputStream, new TypeReference<List<Person>>(){});
			else
				System.err.println("Le fichier JSON n'a pas été trouvé !");
		} catch (JsonProcessingException e) {
		    System.err.println("Erreur de conversion JSON : " + e.getMessage());
		}
	}
	
	public List<Person> getAllPersons() {
		return persons;
	}
}
