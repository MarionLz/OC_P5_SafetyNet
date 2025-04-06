package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.DataModel;

@Service
public class CommunityEmailService {

	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger(CommunityEmailService.class);
    
	@Autowired
	public CommunityEmailService(DataModelService dataModelService) {
		
		this.dataModel = dataModelService.getDataModel();
	}
	
	public List<String> getCommunityEmails(String city) {
		
		List<String> result = dataModel.getPersons().stream()
		.filter(person -> city.equals(person.getCity()))
		.map(person -> new String(person.getEmail()))
		.collect(Collectors.toList());
		
		return result;
	}
}
