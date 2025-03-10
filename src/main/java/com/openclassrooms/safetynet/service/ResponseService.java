package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.DataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import DTO.PersonsByStationsDTO;
import DTO.NbAdultAndChildrenDTO;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {

	private final DataModel dataModel;
	
	@Autowired
	public ResponseService(DataService dataService) {
		this.dataModel = dataService.getDataModel();
	}
	
	private List<String> getFirestationAdresses(String station) {
		List<String> stationAddresses = dataModel.getFirestations().stream()
            .filter(firestation -> firestation.getStation().equals(station))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		if (stationAddresses.isEmpty()) {
	        System.err.println("Aucune station trouvée pour le numéro " + station);
	        return Collections.emptyList();
	    }
		return stationAddresses;
	}
	
	private List<PersonsByStationsDTO> getPersonsList(List<String> stationAddresses) {
		
		List<PersonsByStationsDTO> personByStation = dataModel.getPersons().stream()
            .filter(person -> stationAddresses.contains(person.getAddress()))
            .map(person -> new PersonsByStationsDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
            .collect(Collectors.toList()); 
		return  personByStation;
	}
	
	private boolean isChild(PersonsByStationsDTO personByStation) {
		String birthdate = dataModel.getMedicalrecords().stream()
				.filter(medicalRecord -> personByStation.getFirstName().equals(medicalRecord.getFirstName())
						&& personByStation.getLastName().equals(medicalRecord.getLastName()))
				.map(medicalRecord -> medicalRecord.getBirthdate())
				.findFirst().orElse("");
		if (birthdate.isEmpty()) {
	        System.err.println("Aucune date de naissance trouvée pour la personne " + personByStation.getFirstName() + personByStation.getLastName());
	        return false;
		}
		
		LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		return birthdateLocalDate.plusYears(18).isAfter(LocalDate.now());
	}
		
	private List<Object> addNbAdultAndChildrenToResponse(List<PersonsByStationsDTO> personsByStation) {
		
		List<Object> response = new ArrayList<>();
		
		response.add(personsByStation);
		int childCount = (int)personsByStation.stream().filter(this::isChild).count();
		int adultCount = (int)personsByStation.size() - childCount;
		response.add(new NbAdultAndChildrenDTO(adultCount, childCount));
		return response;
	}
	
	public List<Object> getPersonsByStations(String station) {
			
		List<String> stationAddresses = getFirestationAdresses(station);
		List<PersonsByStationsDTO> personByStation = getPersonsList(stationAddresses);
		
		return addNbAdultAndChildrenToResponse(personByStation);
    }
	
}
