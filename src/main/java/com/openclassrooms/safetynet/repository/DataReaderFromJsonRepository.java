package com.openclassrooms.safetynet.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.exceptions.JsonFileException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.service.DataModelService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Repository class responsible for loading data from a JSON file, 
 * deserializing it into a DataModel object, and validating its content.
 * 
 * The class reads the file location from application properties and 
 * performs validation on the deserialized DataModel object before passing 
 * it to the DataModelService for further processing.
 */
@Repository
public class DataReaderFromJsonRepository implements IDataReaderRepository {
	
    private static final Logger logger = LogManager.getLogger("DataReaderFromJsonRepository");
    
    @Value("${data.file}")
    private Resource jsonFile;
    
	private final Validator validator;
	private final DataModelService dataModelService;

    /**
     * Constructor for DataReaderFromJsonRepository.
     * 
     * @param dataModelService The service to be used for further processing of the DataModel.
     */
	public DataReaderFromJsonRepository(DataModelService dataModelService) {
		this.dataModelService = dataModelService;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}
		
    /**
     * Method called after the bean is constructed to load data from the JSON file 
     * and validate it.
     */
	@PostConstruct
	private void init() {
		try {
	        loadData();
	    } catch (JsonFileException e) {
	        logger.error("Critical error: Unable to load JSON data. Application stopped.", e);
	        throw e;
	    }
	}
	
	 /**
     * Sets the JSON file resource to be loaded.
     * 
     * @param jsonFile The resource containing the JSON data file.
     */
    public void setJsonFile(Resource jsonFile) {
        this.jsonFile = jsonFile;
    }
    
    /**
     * Loads data from the JSON file, deserializes it into a DataModel object,
     * and validates its content.
     */
    public void loadData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = jsonFile.getInputStream();
                        
            if (inputStream == null) {
                throw new JsonFileException("JSON not found.");
            } else {
                DataModel dataModel = objectMapper.readValue(inputStream, DataModel.class);
                validateDataModel(dataModel);
                dataModelService.setDataModel(dataModel);
                logger.debug("Data successfully loaded in DataModel.");
            }
        } catch (IOException e) {
            logger.error("Error loading JSON data.");
            throw new JsonFileException("JSON empty or unreadable.");
        }
    }
    
    /**
     * Validates the deserialized DataModel object to ensure it conforms to the 
     * required constraints. If validation fails, a JsonFileException is thrown.
     * 
     * @param dataModel The DataModel object to be validated.
     */
    private void validateDataModel(DataModel dataModel) {
        Set<ConstraintViolation<DataModel>> violations = validator.validate(dataModel);
        
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("JSON validation error :\n");
            for (ConstraintViolation<DataModel> violation : violations) {
                errorMessage.append("- ").append(violation.getPropertyPath()).append(" : ")
                            .append(violation.getMessage()).append("\n");
            }
            logger.error(errorMessage.toString());
            throw new JsonFileException(errorMessage.toString());
        }
    }
}