package com.openclassrooms.safetynet.service;

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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing firestations data.
 * Provides operations to retrieve, add, update, and delete firestation entries.
 */
@Service
public class FirestationService {

    private final DataModelService dataModelService;
    private final IDataWriterRepository writerRepository;
    private static final Logger logger = LogManager.getLogger(FirestationService.class);

    /**
     * Constructor for FirestationService.
     *
     * @param writerRepository   repository responsible for persisting changes to the data file
     * @param dataModelService   service to access the in-memory data model
     */
    @Autowired
    public FirestationService(IDataWriterRepository writerRepository, DataModelService dataModelService) {
        this.writerRepository = writerRepository;
        this.dataModelService = dataModelService;
    }

    /**
     * Gets the current in-memory data model.
     *
     * @return the {@link DataModel}
     */
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }

    /**
     * Retrieves all persons covered by a specific fire station and counts adults and children.
     *
     * @param station the fire station number
     * @return a {@link FirestationResponseDTO} containing the list of persons and counts
     */
    public FirestationResponseDTO getPersonsByStations(String station) {
        logger.debug("Starting to retrieve data for station {}.", station);

        List<String> stationAddresses = ServiceUtils.getFirestationAdresses(station, getDataModel().getFirestations());

        List<PersonsByStationsDTO> personsByStation = getDataModel().getPersons().stream()
            .filter(person -> stationAddresses.contains(person.getAddress()))
            .map(person -> new PersonsByStationsDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
            .collect(Collectors.toList());

        int childCount = (int) personsByStation.stream()
            .filter(person -> ServiceUtils.isChild(person, getDataModel().getMedicalrecords()))
            .count();

        int adultCount = personsByStation.size() - childCount;

        logger.debug("Retrieval successful: data is ready to be sent.");
        return new FirestationResponseDTO(personsByStation, childCount, adultCount);
    }

    /**
     * Adds a new firestation mapping.
     *
     * @param firestation the {@link Firestation} to add
     * @throws ResourceAlreadyExistsException if the same address and station already exist
     */
    public void addFirestation(Firestation firestation) {
        logger.debug("Starting to add firestation: {}", firestation);
        List<Firestation> firestations = getDataModel().getFirestations();

        boolean exists = firestations.stream()
            .anyMatch(f -> f.getAddress().equalsIgnoreCase(firestation.getAddress())
                        && f.getStation().equalsIgnoreCase(firestation.getStation()));

        if (exists) {
            logger.error("Firestation already exists with address: {} & station: {}", firestation.getAddress(), firestation.getStation());
            throw new ResourceAlreadyExistsException("Firestation already exists with address: " + firestation.getAddress() + " & station: " + firestation.getStation());
        }

        firestations.add(firestation);
        writerRepository.saveData();
        logger.debug("New firestation added: {}.", firestation);
    }

    /**
     * Updates the station number for an existing firestation address.
     *
     * @param updatedFirestation the firestation with updated station value
     * @throws ResourceNotFoundException if no firestation exists with the given address
     */
    public void updateFirestation(Firestation updatedFirestation) {
        logger.debug("Starting to update firestation: {}", updatedFirestation);
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
            logger.error("Firestation not found: {}", updatedFirestation.getAddress());
            throw new ResourceNotFoundException("Firestation not found: " + updatedFirestation.getAddress());
        }

        writerRepository.saveData();
        logger.debug("Firestation updated: {}.", updatedFirestation);
    }

    /**
     * Deletes the firestation mapping by address.
     *
     * @param address the address of the firestation to delete
     * @throws ResourceNotFoundException if no firestation is mapped to the given address
     */
    public void deleteFirestation(String address) {
        logger.debug("Starting to delete firestation at address: {}", address);
        List<Firestation> firestations = getDataModel().getFirestations();

        boolean exists = firestations.stream()
            .anyMatch(f -> f.getAddress().equalsIgnoreCase(address));

        if (!exists) {
            logger.error("Firestation not found: {}", address);
            throw new ResourceNotFoundException("Firestation not found: " + address);
        }

        List<Firestation> updatedFirestations = firestations.stream()
            .filter(firestation -> !firestation.getAddress().equals(address))
            .collect(Collectors.toList());

        getDataModel().setFirestations(updatedFirestations);
        writerRepository.saveData();
        logger.debug("Firestation deleted at address: {}.", address);
    }
}
