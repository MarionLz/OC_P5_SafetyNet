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

@Service
public class MedicalRecordService {

	private final DataModelService dataModelService;
    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);	
	
	private IDataWriterRepository writerRepository;
    
    @Autowired
	public MedicalRecordService(IDataWriterRepository writerRepository, DataModelService dataModelService) {
		
    	this.writerRepository = writerRepository;
		this.dataModelService = dataModelService;
	}
    
    private DataModel getDataModel() {
        return dataModelService.getDataModel();
    }
    
    public void addMedicalRecord(MedicalRecord medicalRecord) {
    	
    	List<MedicalRecord> medicalRecords = getDataModel().getMedicalrecords();

        boolean exists = medicalRecords.stream()
            .anyMatch(m -> m.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
                        && m.getLastName().equalsIgnoreCase(medicalRecord.getLastName()));

        if (exists) {
        	logger.error("MedicalRecord already exists : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
            throw new ResourceAlreadyExistsException("MedicalRecord already exists : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
        }

        medicalRecords.add(medicalRecord);
        writerRepository.saveData();
    }
    
    public void updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
    	
    	List<MedicalRecord> medicalRecords = getDataModel().getMedicalrecords();

        boolean exists = false;
        for (int i = 0; i < medicalRecords.size(); i++) {
        	MedicalRecord current = medicalRecords.get(i);
            if (current.getFirstName().equals(updatedMedicalRecord.getFirstName()) && current.getLastName().equals(updatedMedicalRecord.getLastName())) {
            	medicalRecords.set(i, updatedMedicalRecord);
                exists = true;
                break;
            }
        }
        
        if (!exists) {
        	logger.error("MedicalRecord not found : " + updatedMedicalRecord.getFirstName() + " " + updatedMedicalRecord.getLastName());
        	throw new ResourceNotFoundException("MedicalRecord not found : " + updatedMedicalRecord.getFirstName() + " " + updatedMedicalRecord.getLastName());
        }
    	writerRepository.saveData();
    }
    
    public void deleteMedicalRecord(String firstName, String lastName) {
    	
    	List<MedicalRecord> medicalRecords = getDataModel().getMedicalrecords();

        boolean exists = medicalRecords.stream()
            .anyMatch(m -> m.getFirstName().equalsIgnoreCase(firstName)
                        && m.getLastName().equalsIgnoreCase(lastName));
        
        if (!exists) {
        	logger.error("MedicalRecord not found : " + firstName + " " + lastName);
        	throw new ResourceNotFoundException("MedicalRecord not found : " + firstName + " " + lastName);
        }
        
        List<MedicalRecord> updatedMedicalRecords=  getDataModel().getMedicalrecords().stream()
            .filter(medicalRecord -> !(medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)))
            .collect(Collectors.toList());

        getDataModel().setMedicalrecords(updatedMedicalRecords);
        writerRepository.saveData();
    }
}
