package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.DTO.NbAdultAndChildrenDTO;
import com.openclassrooms.safetynet.DTO.PersonsByStationsDTO;
import com.openclassrooms.safetynet.model.DataModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonsCoveredByStationService {
	
	private final DataModel dataModel;
    private static final Logger logger = LogManager.getLogger("PersonsCoveredByStationService");
    
	@Autowired
	public PersonsCoveredByStationService(DataReaderService dataService) {
		
		this.dataModel = dataService.getDataModel();
	}
	
	private List<String> getFirestationAdresses(String station) {
		
		logger.debug("Début de la récupération des adresses des firestations pour la station {}.", station);
		List<String> stationAddresses = dataModel.getFirestations().stream()
            .filter(firestation -> firestation.getStation().equals(station))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		logger.debug("Récupération réussie : {} adresses trouvées pour la station {}.", stationAddresses.size(), station);
		return stationAddresses;
	}
	
	private List<PersonsByStationsDTO> getPersonsList(List<String> stationAddresses) {
		
	    logger.debug("Début de la récupération des personnes couvertes par les adresses des stations : {}", stationAddresses);
		List<PersonsByStationsDTO> personByStation = dataModel.getPersons().stream()
            .filter(person -> stationAddresses.contains(person.getAddress()))
            .map(person -> new PersonsByStationsDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
            .collect(Collectors.toList());
		logger.debug("Récupération réussi : {} personnes trouvées pour les stations correspondant aux adresses {}.", personByStation.size(), stationAddresses);
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
		
	private List<Object> addNbAdultAndChildrenToResponse(List<PersonsByStationsDTO> personsByStation) {
		
		logger.debug("Début de l'ajout du nombre d'adultes et d'enfants. Nombre de personnes reçu : {}", personsByStation.size());
		List<Object> response = new ArrayList<>();
		
		response.add(personsByStation);
		int childCount = (int)personsByStation.stream().filter(this::isChild).count();
		int adultCount = (int)personsByStation.size() - childCount;
		response.add(new NbAdultAndChildrenDTO(adultCount, childCount));
		logger.debug("Ajout réussi : {} adultes et {} enfants ont été comptabilisés.", adultCount, childCount);
		return response;
	}
	
	public List<Object> getPersonsByStations(String station) {
		
		logger.debug("Début de la récupération des données pour la station {}", station);
		List<String> stationAddresses = getFirestationAdresses(station);
		List<PersonsByStationsDTO> personByStation = getPersonsList(stationAddresses);
		logger.debug("Récupération réussie, les données sont prêtes à être envoyées.");
		return addNbAdultAndChildrenToResponse(personByStation);
    }
}
