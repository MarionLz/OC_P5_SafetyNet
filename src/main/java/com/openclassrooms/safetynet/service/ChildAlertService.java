package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.model.DataModel;

@Service
public class ChildAlertService {

	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger(ChildAlertService.class);
    
	@Autowired
	public ChildAlertService(DataModelService dataModelService) {
		
		this.dataModel = dataModelService.getDataModel();
	}
	
	public List<PersonIdentityDTO> getPersonsAtAddress(String address) {
		
        logger.debug("Retrieving persons at address: {}", address);
        
		List<PersonIdentityDTO> personsAtAddress = dataModel.getPersons().stream()
				.filter(person -> address.equals(person.getAddress()))
				.map(person -> new PersonIdentityDTO(person.getFirstName(), person.getLastName()))
				.collect(Collectors.toList());
		
        logger.debug("{} persons found at address: {}", personsAtAddress.size(), address);
        
		return personsAtAddress;
	}
	
	public ChildAlertResponseDTO getChildrenAtAddress(String address) {
		
        logger.debug("Retrieving children and family members at address: {}", address);

		List<PersonIdentityDTO> personsAtAddress = getPersonsAtAddress(address);
		List<ChildDTO> children = new ArrayList<>();
		List<PersonIdentityDTO> otherFamilyMembers = new ArrayList<>();
		
		for (PersonIdentityDTO person : personsAtAddress) {
			int age = ServiceUtils.getAge(person.getFirstName(), person.getLastName(), dataModel.getMedicalrecords());
			if (age < 18) {
				children.add(new ChildDTO(person.getFirstName(), person.getLastName(), String.valueOf(age)));
			}
			else {
				otherFamilyMembers.add(new PersonIdentityDTO(person.getFirstName(), person.getLastName()));
			}
		}
		
	    if (children.isEmpty()) {
	        return new ChildAlertResponseDTO(new ArrayList<>(), new ArrayList<>());
	    }
	    
        logger.debug("Child retrieval completed: {} children and {} other family members found.", children.size(), otherFamilyMembers.size());
        
		return new ChildAlertResponseDTO(children, otherFamilyMembers);
	}
}
