package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;
import com.openclassrooms.safetynet.exceptions.ResourceNotFoundException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;

/**
 * Service class for managing medical records.
 * Handles creation, update, and deletion of medical records within the DataModel.
 */
@Service
public class MedicalRecordService {

    private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);	

    private final IDataWriterRepository writerRepository;

    /**
     * Constructor for MedicalRecordService.
     *
     * @param writerRepository Repository used to persist data after changes.
     * @param dataModelService Service to access the current in-memory data model.
     */
    @Autowired
    public MedicalRecordService(IDataWriterRepository writerRepository, DataModelService dataModelService) {
        this.writerRepository = writerRepository;
        this.dataModelService = dataModelService;
    }

    /**
     * Returns the current data model containing all data.
     *
     * @return the {@link DataModel}
     */
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }

    /**
     * Adds a new medical record if it doesn't already exist.
     *
     * @param medicalRecord the new {@link MedicalRecord} to add
     * @throws ResourceAlreadyExistsException if a record with the same name already exists
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        logger.debug("Starting to add medicalRecord: {}", medicalRecord);
        List<MedicalRecord> medicalRecords = getDataModel().getMedicalrecords();

        boolean exists = medicalRecords.stream()
            .anyMatch(m -> m.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
                        && m.getLastName().equalsIgnoreCase(medicalRecord.getLastName()));

        if (exists) {
            logger.error("MedicalRecord already exists : {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
            throw new ResourceAlreadyExistsException("MedicalRecord already exists : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
        }

        medicalRecords.add(medicalRecord);
        writerRepository.saveData();
        logger.debug("New medicalRecord added: {}.", medicalRecord);
    }

    /**
     * Updates an existing medical record.
     *
     * @param updatedMedicalRecord the {@link MedicalRecord} with updated data
     * @throws ResourceNotFoundException if no matching record is found
     */
    public void updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
        logger.debug("Starting to update medicalRecord: {}", updatedMedicalRecord);
        List<MedicalRecord> medicalRecords = getDataModel().getMedicalrecords();

        boolean exists = false;
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord current = medicalRecords.get(i);
            if (current.getFirstName().equals(updatedMedicalRecord.getFirstName()) &&
                current.getLastName().equals(updatedMedicalRecord.getLastName())) {
                medicalRecords.set(i, updatedMedicalRecord);
                exists = true;
                break;
            }
        }

        if (!exists) {
            logger.error("MedicalRecord not found : {} {}", updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
            throw new ResourceNotFoundException("MedicalRecord not found : " + updatedMedicalRecord.getFirstName() + " " + updatedMedicalRecord.getLastName());
        }

        writerRepository.saveData();
        logger.debug("MedicalRecord updated: {}.", updatedMedicalRecord);
    }

    /**
     * Deletes a medical record by first and last name.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     * @throws ResourceNotFoundException if no record is found with the given names
     */
    public void deleteMedicalRecord(String firstName, String lastName) {
        logger.debug("Starting to delete medicalRecord for: {} {}.", firstName, lastName);
        List<MedicalRecord> medicalRecords = getDataModel().getMedicalrecords();

        boolean exists = medicalRecords.stream()
            .anyMatch(m -> m.getFirstName().equalsIgnoreCase(firstName)
                        && m.getLastName().equalsIgnoreCase(lastName));

        if (!exists) {
            logger.error("MedicalRecord not found : {} {}", firstName, lastName);
            throw new ResourceNotFoundException("MedicalRecord not found : " + firstName + " " + lastName);
        }

        List<MedicalRecord> updatedMedicalRecords = medicalRecords.stream()
            .filter(medicalRecord -> !(medicalRecord.getFirstName().equals(firstName)
                                    && medicalRecord.getLastName().equals(lastName)))
            .collect(Collectors.toList());

        getDataModel().setMedicalrecords(updatedMedicalRecords);
        writerRepository.saveData();
        logger.debug("MedicalRecord deleted for: {} {}.", firstName, lastName);
    }
}