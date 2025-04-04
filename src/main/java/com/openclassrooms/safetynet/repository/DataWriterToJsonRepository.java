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
	
	
	//private DataModel dataModel;	
    private static final Logger logger = LogManager.getLogger(DataWriterToJsonRepository.class);
    
	@Value("${data.file}")
	private Resource jsonFile;
	
    private final DataModelService dataModelService;
	
	public DataWriterToJsonRepository(DataModelService dataModelService) {
		
		this.dataModelService = dataModelService;
	}
	
//	@PostConstruct
//	private void init() {
//		loadDataFromJson();
//	}

//    private void loadDataFromJson() {
//        try {
//            if (jsonFile.exists()) {
//                rootNode = objectMapper.readTree(jsonFile.getInputStream());
//            } else {
//                rootNode = objectMapper.createObjectNode();
//            }
//        } catch (IOException e) {
//			logger.error("Error loading JSON data.");
//			throw new JsonFileException("JSON empty or unreadable.");
//        }
//    }
    
//    public void saveData() {
//    	
//        ObjectMapper objectMapper = new ObjectMapper();
//        File file = jsonFile.getFile();
//        
//        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, dataModel);
//    }
    
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
