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

/**
 * Repository responsible for writing the current application data (DataModel) 
 * back to the original JSON file.
 * 
 * This class retrieves the current data from the DataModelService and uses 
 * Jackson to serialize and write it to disk. It is useful for persisting 
 * any modifications made during the application runtime.
 */
@Repository
public class DataWriterToJsonRepository implements IDataWriterRepository {
	
    private static final Logger logger = LogManager.getLogger(DataWriterToJsonRepository.class);

    @Value("${data.file}")
    private Resource jsonFile;

    private final DataModelService dataModelService;

    /**
     * Constructor for DataWriterToJsonRepository.
     * 
     * @param dataModelService Service providing access to the current DataModel.
     */
    public DataWriterToJsonRepository(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    /**
     * Allows manually setting the JSON file resource, useful for testing or dynamic configs.
     * 
     * @param jsonFile the JSON file resource to be written to.
     */
    public void setJsonFile(Resource jsonFile) {
        this.jsonFile = jsonFile;
    }

    /**
     * Saves the current DataModel to the configured JSON file.
     * Uses Jackson's pretty printer for human-readable formatting.
     * 
     * Throws a JsonFileException if any I/O error occurs.
     */
    public void saveData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            File file = jsonFile.getFile();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, dataModelService.getDataModel());

            logger.debug("Data successfully saved to JSON file: " + file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error saving data to JSON.", e);
            throw new JsonFileException("Error saving data to JSON.");
        }
    }
}