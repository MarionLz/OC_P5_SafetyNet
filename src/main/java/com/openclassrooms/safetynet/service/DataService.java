package com.openclassrooms.safetynet.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.DataModel;

@Service
public class DataService {
	
	private DataModel dataModel;
    private static final Logger logger = LogManager.getLogger("DataService");	
	
	public DataService () throws IOException {
		loadDataFromJson();
	}
	
	private void loadDataFromJson() throws IOException  {
//		try {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dta.json");
			
			if (inputStream != null) {
				dataModel = objectMapper.readValue(inputStream, DataModel.class);
			}
			else
				System.err.println("Le fichier JSON n'a pas été trouvé !");
//		} catch (JsonProcessingException e) {
		    //logger.error("Erreur de conversion JSON : " + e.getMessage());
		//}
	}
	
	public DataModel getDataModel() {
		return dataModel;
	}
}
