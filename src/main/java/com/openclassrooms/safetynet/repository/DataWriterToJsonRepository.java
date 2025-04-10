package com.openclassrooms.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.exceptions.JsonFileException;
import com.openclassrooms.safetynet.service.DataModelService;

import java.io.File;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

@Repository
public class DataWriterToJsonRepository implements IDataWriterRepository {
	
    private static final Logger logger = LogManager.getLogger(DataWriterToJsonRepository.class);
    
	@Value("${data.file}")
	private Resource jsonFile;
	
    private final DataModelService dataModelService;
	
	public DataWriterToJsonRepository(DataModelService dataModelService) {
		
		this.dataModelService = dataModelService;
	}
	
	public void setJsonFile(Resource jsonFile) {
	    this.jsonFile = jsonFile;
	}
    
    public void saveData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            File file = jsonFile.getFile();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, dataModelService.getDataModel());

            logger.info("Data successfully saved to JSON file: " + file.getAbsolutePath());
        } catch (IOException e) {
        	
            logger.error("Error saving data to JSON.", e);
            throw new JsonFileException("Error saving data to JSON.");
        }
    }
}
