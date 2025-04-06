package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.DTO.firestation.NbAdultAndChildrenDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsByStationsDTO;
import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;
import com.openclassrooms.safetynet.exceptions.ResourceNotFoundException;
import com.openclassrooms.safetynet.DTO.firestation.FirestationResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService {
	
	private final DataModelService dataModelService;
	private IDataWriterRepository writerRepository;
    private static final Logger logger = LogManager.getLogger(FirestationService.class);
    
	@Autowired
	public FirestationService(IDataWriterRepository writerRepository, DataModelService dataModelService) {
		
		this.writerRepository = writerRepository;
		this.dataModelService = dataModelService;
	}
	
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }
	
	private List<String> getFirestationAdresses(String station) {
		
		logger.debug("Starting to retrieve firestation addresses for station {}.", station);
		List<String> stationAddresses = getDataModel().getFirestations().stream()
            .filter(firestation -> firestation.getStation().equals(station))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} addresses found for station {}.", stationAddresses.size(), station);
		return stationAddresses;
	}
	
	private List<PersonsByStationsDTO> getPersonsList(List<String> stationAddresses) {
		
	    logger.debug("Starting to retrieve persons covered by firestations adresses: {}.", stationAddresses);
		List<PersonsByStationsDTO> personByStation = getDataModel().getPersons().stream()
            .filter(person -> stationAddresses.contains(person.getAddress()))
            .map(person -> new PersonsByStationsDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
            .collect(Collectors.toList());
		logger.debug("Retrieval successful: {} persons are covered by firestations matching adresses: {}.", personByStation.size(), stationAddresses);
		return  personByStation;
	}
	
	private boolean isChild(PersonsByStationsDTO personByStation) {
		
		String birthdate = getDataModel().getMedicalrecords().stream()
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
	
	public FirestationResponseDTO getPersonsByStations(String station) {
		
		logger.debug("Starting to retrieve data for station {}.", station);
		List<String> stationAddresses = getFirestationAdresses(station);
		List<PersonsByStationsDTO> personByStation = getPersonsList(stationAddresses);
		NbAdultAndChildrenDTO nbAdultAndChildren = countNbAdultAndChildren(personByStation);
		logger.debug("Retrieval successful: data is ready to be sent.");
		return new FirestationResponseDTO(personByStation, nbAdultAndChildren);
    }

    public void addFirestation(Firestation firestation) {
    	
    	List<Firestation> firestations = getDataModel().getFirestations();

        boolean exists = firestations.stream()
            .anyMatch(f -> f.getAddress().equalsIgnoreCase(firestation.getAddress())
                        && f.getStation().equalsIgnoreCase(firestation.getStation()));

        if (exists) {
        	logger.error("Firestation already exists with adress: " + firestation.getAddress() + " & station: " + firestation.getStation());
            throw new ResourceAlreadyExistsException("Firestation already exists with adress: " + firestation.getAddress() + " & station: " + firestation.getStation());
        }

        firestations.add(firestation);
        writerRepository.saveData();
    }
    
    public void updateFirestation(Firestation updatedFirestation) {
    	
        List<Firestation> firestations = getDataModel().getFirestations();

        boolean exists = false;
        for (int i = 0; i < firestations.size(); i++) {
            Firestation current = firestations.get(i);
            if (current.getAddress().equals(updatedFirestation.getAddress())) {
            	firestations.set(i, updatedFirestation);
                exists = true;
                break;
            }
        }
        
        if (!exists) {
        	logger.error("Firestation not found : " + updatedFirestation.getAddress());
        	throw new ResourceNotFoundException("Firestation not found : " + updatedFirestation.getAddress());
        }
    	writerRepository.saveData();
    }
    
    public void deleteFirestation(String address) {
    	
        List<Firestation> firestations = getDataModel().getFirestations();

        boolean exists = firestations.stream()
            .anyMatch(f -> f.getAddress().equalsIgnoreCase(address));
        
        if (!exists) {
        	logger.error("Firestation not found : " + address);
        	throw new ResourceNotFoundException("Firestation not found : " + address);
        }
        
    	List<Firestation> updatedFirestations = getDataModel().getFirestations().stream()
            .filter(firestation -> !(firestation.getAddress().equals(address)))
            .collect(Collectors.toList());

        getDataModel().setFirestations(updatedFirestations);
        writerRepository.saveData();
    }
}
