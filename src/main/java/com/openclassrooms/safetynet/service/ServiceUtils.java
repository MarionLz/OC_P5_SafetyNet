package com.openclassrooms.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openclassrooms.safetynet.DTO.MedicalHistoryDTO;
import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsByStationsDTO;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;

/**
 * Utility class providing common methods for working with persons, medical records,
 * firestations, and age calculations.
 */
public class ServiceUtils {

    private static final Logger logger = LogManager.getLogger(ServiceUtils.class);

    /**
     * Calculates the age of a person based on their birthdate found in their medical records.
     * 
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param medicalRecords the list of medical records
     * @return the calculated age of the person
     */
    public static int getAge(String firstName, String lastName, List<MedicalRecord> medicalRecords) {
        
        logger.debug("Calculating age for person: {} {}", firstName, lastName);

        // Retrieve the birthdate of the person from the medical records
        String birthdate = medicalRecords.stream()
                .filter(medicalRecord -> firstName.equals(medicalRecord.getFirstName())
                        && lastName.equals(medicalRecord.getLastName()))
                .map(medicalRecord -> medicalRecord.getBirthdate())
                .findFirst().orElse("");
        
        // Convert birthdate to LocalDate and calculate the age
        LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate today = LocalDate.now();
        int age = Period.between(birthdateLocalDate, today).getYears();
        
        logger.debug("Age calculated for {} {}: {} years", firstName, lastName, age);

        return age;
    }

    /**
     * Retrieves the medical history of a person from the medical records.
     * 
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param medicalRecords the list of medical records
     * @return a MedicalHistoryDTO containing the medications and allergies of the person
     */
    public static MedicalHistoryDTO getMedicalHistory(String firstName, String lastName, List<MedicalRecord> medicalRecords) {
        
        logger.debug("Retrieving medicalHistory for person : {}", firstName, lastName);

        MedicalHistoryDTO medicalHistory = medicalRecords.stream()
                .filter(medicalRecord -> firstName.equals(medicalRecord.getFirstName())
                        && lastName.equals(medicalRecord.getLastName()))
                .map(medicalRecord -> new MedicalHistoryDTO(medicalRecord.getMedications(), medicalRecord.getAllergies()))
                .findFirst().orElse(new MedicalHistoryDTO(new String[0], new String[0]));
        
        logger.debug("Medical history retrieved for {}", firstName, lastName);

        return medicalHistory;
    }

    /**
     * Retrieves a list of persons living at a specific address.
     * 
     * @param address the address to search for
     * @param persons the list of persons
     * @return a list of PersonIdentityDTO representing persons at the specified address
     */
    public static List<PersonIdentityDTO> getPersonsIdentity(String address, List<Person> persons) {
        
        logger.debug("Retrieving persons at address: {}", address);
        
        // Filter persons based on address and map them to PersonIdentityDTO
        List<PersonIdentityDTO> personsIdentity = persons.stream()
                .filter(person -> address.equals(person.getAddress()))
                .map(person -> new PersonIdentityDTO(person.getFirstName(), person.getLastName(), person.getPhone()))
                .collect(Collectors.toList());
        
        logger.debug("{} persons found at address: {}", personsIdentity.size(), address);
        
        return personsIdentity;
    }

    /**
     * Retrieves a list of addresses covered by a specific fire station.
     * 
     * @param stationNumber the fire station number
     * @param firestations the list of firestations
     * @return a list of addresses covered by the fire station
     */
    public static List<String> getFirestationAdresses(String stationNumber, List<Firestation> firestations) {
        
        logger.debug("Starting to retrieve firestation addresses for station {}.", stationNumber);
        
        List<String> stationAddresses = firestations.stream()
            .filter(firestation -> firestation.getStation().equals(stationNumber))
            .map(firestation -> firestation.getAddress())
            .collect(Collectors.toList());
        
        logger.debug("Retrieval successful: {} addresses found for station {}.", stationAddresses.size(), stationNumber);
        
        return stationAddresses;
    }

    /**
     * Determines if a person is considered a child (under 18) based on their birthdate.
     * 
     * @param personByStation the person to check
     * @param medicalRecords the list of medical records
     * @return true if the person is a child, false otherwise
     */
    public static boolean isChild(PersonsByStationsDTO personByStation, List<MedicalRecord> medicalRecords) {
        
        String birthdate = medicalRecords.stream()
                .filter(medicalRecord -> personByStation.getFirstName().equals(medicalRecord.getFirstName())
                        && personByStation.getLastName().equals(medicalRecord.getLastName()))
                .map(medicalRecord -> medicalRecord.getBirthdate())
                .findFirst().orElse("");
        
        LocalDate birthdateLocalDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return birthdateLocalDate.plusYears(18).isAfter(LocalDate.now());
    }
}