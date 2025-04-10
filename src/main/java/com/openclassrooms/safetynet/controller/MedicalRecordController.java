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

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

	private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);	

    @Autowired
	private MedicalRecordService medicalRecordService;
	
	public MedicalRecordController(MedicalRecordService medicalRecordService) {
		
		this.medicalRecordService = medicalRecordService;
	}
	
	@PostMapping
    public void addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    	
		logger.info("Request POST received for /medicalRecord with new MedicalRecord : {}", medicalRecord);
		medicalRecordService.addMedicalRecord(medicalRecord);
		logger.info("Request POST successful, a new medicalRecord has been added.");
    }
    
    @PutMapping
    public void updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    	
		logger.info("Request PUT received for /medicalRecord with updated MedicalRecord : {}", medicalRecord);
		medicalRecordService.updateMedicalRecord(medicalRecord);
		logger.info("Request PUT successful, the medicalRecord has been updated.");
    }
    
    @DeleteMapping
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
    	
		logger.info("Request DELETE received for /medicalRecord with firstName : {} and lastName : {}.", firstName, lastName);
		medicalRecordService.deleteMedicalRecord(firstName, lastName);
		logger.info("Request DELETE successful, the medicalRecord has been deleted.");
    }
}
