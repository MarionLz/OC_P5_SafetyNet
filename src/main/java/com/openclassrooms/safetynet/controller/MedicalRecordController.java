package com.openclassrooms.safetynet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;

/**
 * Controller for handling medical record-related requests.
 * This class exposes endpoints for adding, updating, and deleting medical records.
 */
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Constructor for injecting the MedicalRecordService.
     *
     * @param medicalRecordService The service used to manage medical records.
     */
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Method to add a new medical record.
     * This method handles the POST request for the /medicalRecord endpoint with a new medical record.
     *
     * @param medicalRecord The medical record object to be added.
     */
    @PostMapping
    public void addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        logger.info("Request POST received for /medicalRecord with new MedicalRecord : {}", medicalRecord);
        medicalRecordService.addMedicalRecord(medicalRecord);
        logger.info("Request POST successful, a new medicalRecord has been added.");
    }

    /**
     * Method to update an existing medical record.
     * This method handles the PUT request for the /medicalRecord endpoint with an updated medical record object.
     *
     * @param medicalRecord The updated medical record object.
     */
    @PutMapping
    public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {

        logger.info("Request PUT received for /medicalRecord with updated MedicalRecord : {}", medicalRecord);
        medicalRecordService.updateMedicalRecord(medicalRecord);
        logger.info("Request PUT successful, the medicalRecord has been updated.");
    }

    /**
     * Method to delete a medical record.
     * This method handles the DELETE request for the /medicalRecord endpoint with the specified first and last names.
     *
     * @param firstName The first name of the individual whose medical record is to be deleted.
     * @param lastName The last name of the individual whose medical record is to be deleted.
     */
    @DeleteMapping
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {

        logger.info("Request DELETE received for /medicalRecord with firstName : {} and lastName : {}.", firstName, lastName);
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        logger.info("Request DELETE successful, the medicalRecord has been deleted.");
    }
}