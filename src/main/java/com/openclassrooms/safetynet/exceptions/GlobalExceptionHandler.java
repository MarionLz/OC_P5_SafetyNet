package com.openclassrooms.safetynet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger("GlobalExceptionHandler");
	
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException ex) {
        logger.error("Erreur d'entrée/sortie: {}", ex.getMessage());
        return new ResponseEntity<>("Erreur de lecture du fichier JSON: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
