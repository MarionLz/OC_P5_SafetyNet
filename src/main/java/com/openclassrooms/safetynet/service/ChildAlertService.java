package com.openclassrooms.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.DTO.childAlert.PersonsAtAddressDTO;
import com.openclassrooms.safetynet.model.DataModel;

@Service
public class ChildAlertService {

	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger(ChildAlertService.class);
    
	@Autowired
	public ChildAlertService(DataReaderService dataService) {
		
		this.dataModel = dataService.getDataModel();
	}	
	
	private int getAge(PersonsAtAddressDTO person) {
		
        logger.debug("Calculating age for person: {} {}", person.getFirstName(), person.getLastName());

		String birthdate = dataModel.getMedicalrecords().stream()
				.filter(medicalRecord -> person.getFirstName().equals(medicalRecord.getFirstName())
						&& person.getLastName().equals(medicalRecord.getLastName()))
				.map(medicalRecord -> medicalRecord.getBirthdate())
				.findFirst().orElse("");
		
		LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    LocalDate today = LocalDate.now();
	    int age = Period.between(birthdateLocalDate, today).getYears();
	    
        logger.debug("Age calculated for {} {}: {} years", person.getFirstName(), person.getLastName(), age);

		return age;
	}
	
	public List<PersonsAtAddressDTO> getPersonsAtAddress(String address) {
		
        logger.debug("Retrieving persons at address: {}", address);
        
		List<PersonsAtAddressDTO> personsAtAddress = dataModel.getPersons().stream()
				.filter(person -> address.equals(person.getAddress()))
				.map(person -> new PersonsAtAddressDTO(person.getFirstName(), person.getLastName()))
				.collect(Collectors.toList());
		
        logger.debug("{} persons found at address: {}", personsAtAddress.size(), address);
        
		return personsAtAddress;
	}
	
	public ChildAlertResponseDTO getChildrenAtAddress(String address) {
		
        logger.debug("Retrieving children and family members at address: {}", address);

		List<PersonsAtAddressDTO> personsAtAddress = getPersonsAtAddress(address);
		List<ChildDTO> children = new ArrayList<>();
		List<PersonsAtAddressDTO> otherFamilyMembers = new ArrayList<>();
		
		for (PersonsAtAddressDTO person : personsAtAddress) {
			int age = getAge(person);
			if (age < 18) {
				children.add(new ChildDTO(person.getFirstName(), person.getLastName(), String.valueOf(age)));
			}
			else {
				otherFamilyMembers.add(new PersonsAtAddressDTO(person.getFirstName(), person.getLastName()));
			}
		}
		
	    if (children.isEmpty()) {
	        return new ChildAlertResponseDTO(new ArrayList<>(), new ArrayList<>());
	    }
	    
        logger.debug("Child retrieval completed: {} children and {} other family members found.", children.size(), otherFamilyMembers.size());
        
		return new ChildAlertResponseDTO(children, otherFamilyMembers);
	}
}
