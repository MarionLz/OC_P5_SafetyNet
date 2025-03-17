package com.openclassrooms.safetynet.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.openclassrooms.safetynet.model.DataModel;

import com.openclassrooms.safetynet.exceptions.JsonFileException;

@Service
public class DataService {
	
	private DataModel dataModel;
    private static final Logger logger = LogManager.getLogger("DataService");	
	
	public DataService () {
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
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data_test_vide.json");
			
			if (inputStream == null) {
				throw new JsonFileException("JSON file not found.");
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
