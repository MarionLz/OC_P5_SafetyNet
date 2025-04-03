package com.openclassrooms.safetynet.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynet.exceptions.JsonFileException;
import com.openclassrooms.safetynet.model.DataModel;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	
    private ObjectMapper objectMapper;
    private JsonNode rootNode;
	
	public DataWriterToJsonRepository() {
		objectMapper = new ObjectMapper();
	}
	
	@PostConstruct
	private void init() {
		loadDataFromJson();
	}

    private void loadDataFromJson() {
        try {
            if (jsonFile.exists()) {
                rootNode = objectMapper.readTree(jsonFile.getInputStream());
            } else {
                rootNode = objectMapper.createObjectNode();
            }
        } catch (IOException e) {
			logger.error("Error loading JSON data.");
			throw new JsonFileException("JSON empty or unreadable.");
        }
    }
	
    private void saveDataInJson() {
    	
        try (OutputStream os = Files.newOutputStream(jsonFile.getFile().toPath())) {
        	logger.info("OK SAVE DATA IN JSON");
            objectMapper.writeValue(os, rootNode);
        } catch (IOException e) {
            throw new JsonFileException("Error saving new data in JSON");
        }
    }
    
    public synchronized void add(String collectionName, ObjectNode newNode) {
    	
        ArrayNode arrayNode = (ArrayNode) rootNode.get(collectionName);
        if (arrayNode == null) {
            arrayNode = objectMapper.createArrayNode();
            ((ObjectNode) rootNode).set(collectionName, arrayNode);
        }
        arrayNode.add(newNode);
        saveDataInJson();
    }

//    // Écrire les données dans le fichier JSON
//    protected void writeToFile(List<T> data) {
//        try {
//            Files.write(Paths.get(resource.getURI()), objectMapper.writeValueAsBytes(data));
//        } catch (IOException e) {
//            throw new RuntimeException("Erreur d'écriture dans le fichier JSON: " + resource.getFilename(), e);
//        }
//    }
//
//    // Ajouter un élément à la liste et sauvegarder
//    public T save(T item) {
//        List<T> data = readFromFile();
//        data.add(item);
//        writeToFile(data);
//        return item;
//    }
//
//    // Supprimer un élément selon un critère
//    public boolean deleteIf(java.util.function.Predicate<T> predicate) {
//        List<T> data = readFromFile();
//        boolean removed = data.removeIf(predicate);
//        if (removed) writeToFile(data);
//        return removed;
//    }
//
//    // Mettre à jour un élément
//    public Optional<T> updateIf(java.util.function.Predicate<T> predicate, T newItem) {
//        List<T> data = readFromFile();
//        Optional<T> existingItem = data.stream().filter(predicate).findFirst();
//        if (existingItem.isPresent()) {
//            data.remove(existingItem.get());
//            data.add(newItem);
//            writeToFile(data);
//        }
//        return existingItem;
//    }
	
}
