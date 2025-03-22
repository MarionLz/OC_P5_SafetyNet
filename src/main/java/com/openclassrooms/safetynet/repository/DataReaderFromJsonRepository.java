package com.openclassrooms.safetynet.repository;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.exceptions.JsonFileException;
import com.openclassrooms.safetynet.model.DataModel;

import jakarta.annotation.PostConstruct;

@Repository
public class DataReaderFromJsonRepository implements IDataReaderRepository {
	
	private DataModel dataModel;
    private static final Logger logger = LogManager.getLogger("DataReaderFromJsonRepository");
    
    @Value("${data.file}")
    private Resource jsonFile;
		
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
			}
		} catch (IOException e) {
			logger.error("Erreur lors du chargement des données JSON");
			throw new JsonFileException("Fichier JSON vide ou illisible.");
		}
	}
	
	public DataModel getDataModel() {
		return dataModel;
	}
}
