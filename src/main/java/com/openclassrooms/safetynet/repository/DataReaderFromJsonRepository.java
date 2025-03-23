package com.openclassrooms.safetynet.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.openclassrooms.safetynet.exceptions.JsonFileException;
import com.openclassrooms.safetynet.model.DataModel;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Repository
public class DataReaderFromJsonRepository implements IDataReaderRepository {
	
	private DataModel dataModel;	
    private static final Logger logger = LogManager.getLogger("DataReaderFromJsonRepository");
    
    @Value("${data.file}")
    private Resource jsonFile;
    
	private final Validator validator;

	public DataReaderFromJsonRepository() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}
		
	@PostConstruct
	private void init() {
		try {
	        loadDataFromJson();
	    } catch (JsonFileException e) {
	        logger.error("Erreur critique : Impossible de charger les données JSON. Arrêt de l'application.", e);
	        throw e;
	    }
	}
	
	public void loadDataFromJson() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream inputStream = jsonFile.getInputStream();
			
			if (inputStream == null) {
				throw new JsonFileException("Fichier JSON non trouvé.");
			} else {
				dataModel = objectMapper.readValue(inputStream, DataModel.class);
				validateDataModel(dataModel);
			}
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des données JSON");
			throw new JsonFileException("Fichier JSON vide ou illisible.");
		}
	}
	
	private void validateDataModel(DataModel dataModel) {
		
		Set<ConstraintViolation<DataModel>> violations = validator.validate(dataModel);
		
		if(!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("JSON validation error :\n");
			for(ConstraintViolation<DataModel> violation : violations) {
				errorMessage.append("- ").append(violation.getPropertyPath()).append(" : ")
                .append(violation.getMessage()).append("\n");
			}
			logger.error(errorMessage.toString());
			throw new JsonFileException(errorMessage.toString());
		}
	}
	
	public DataModel getDataModel() {
		return dataModel;
	}
}
