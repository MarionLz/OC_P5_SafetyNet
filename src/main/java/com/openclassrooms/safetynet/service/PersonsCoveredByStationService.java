package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.DTO.NbAdultAndChildrenDTO;
import com.openclassrooms.safetynet.DTO.PersonsByStationsDTO;
import com.openclassrooms.safetynet.DTO.PersonsCoveredByStationResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonsCoveredByStationService {
	
	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger(PersonsCoveredByStationService.class);
    
	@Autowired
	public PersonsCoveredByStationService(DataReaderService dataService) {
		
		this.dataModel = dataService.getDataModel();
	}
	
	private List<String> getFirestationAdresses(String station) {
		
		logger.debug("Starting to retrieve firestation addresses for station {}.", station);
		List<String> stationAddresses = dataModel.getFirestations().stream()
            .filter(firestation -> firestation.getStation().equals(station))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} addresses found for station {}.", stationAddresses.size(), station);
		return stationAddresses;
	}
	
	private List<PersonsByStationsDTO> getPersonsList(List<String> stationAddresses) {
		
	    logger.debug("Starting to retrieve persons covered by firestations adresses: {}.", stationAddresses);
		List<PersonsByStationsDTO> personByStation = dataModel.getPersons().stream()
            .filter(person -> stationAddresses.contains(person.getAddress()))
            .map(person -> new PersonsByStationsDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} persons are covered by firestations matching adresses: {}.", personByStation.size(), stationAddresses);
		return  personByStation;
	}
	
	private boolean isChild(PersonsByStationsDTO personByStation) {
		
		String birthdate = dataModel.getMedicalrecords().stream()
				.filter(medicalRecord -> personByStation.getFirstName().equals(medicalRecord.getFirstName())
						&& personByStation.getLastName().equals(medicalRecord.getLastName()))
				.map(medicalRecord -> medicalRecord.getBirthdate())
				.findFirst().orElse("");
		
		LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		return birthdateLocalDate.plusYears(18).isAfter(LocalDate.now());
	}
		
	private NbAdultAndChildrenDTO countNbAdultAndChildren(List<PersonsByStationsDTO> personsByStation) {
		
		logger.debug("Starting calculating the number of adults and children. Number of persons received: {}.", personsByStation.size());
		int childCount = (int)personsByStation.stream().filter(this::isChild).count();
		int adultCount = (int)personsByStation.size() - childCount;
		logger.debug("Calculation successful: {} adults and {} children have been counted.", adultCount, childCount);
		return new NbAdultAndChildrenDTO(adultCount, childCount);
	}
	
	public PersonsCoveredByStationResponseDTO getPersonsByStations(String station) {
		
		logger.debug("Starting to retrieve data for station {}.", station);
		List<String> stationAddresses = getFirestationAdresses(station);
		List<PersonsByStationsDTO> personByStation = getPersonsList(stationAddresses);
		NbAdultAndChildrenDTO nbAdultAndChildren = countNbAdultAndChildren(personByStation);
		logger.debug("Retrieval successful: data is ready to be sent.");
		return new PersonsCoveredByStationResponseDTO(personByStation, nbAdultAndChildren);
    }
}
