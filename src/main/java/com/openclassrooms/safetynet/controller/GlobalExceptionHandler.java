package com.openclassrooms.safetynet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;
import com.openclassrooms.safetynet.exceptions.ResourceNotFoundException;

/**
 * Global exception handler for the application.
 * This class handles exceptions globally and provides appropriate HTTP responses for specific exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the ResourceAlreadyExistsException.
     * This method returns a 409 Conflict status with the exception message when a resource already exists.
     *
     * @param ex The exception that was thrown when the resource already exists.
     * @return A ResponseEntity with the status 409 (Conflict) and the exception message.
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<String> handlePersonAlreadyExists(ResourceAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Handles the ResourceNotFoundException.
     * This method returns a 404 Not Found status with the exception message when a resource is not found.
     *
     * @param ex The exception that was thrown when the resource was not found.
     * @return A ResponseEntity with the status 404 (Not Found) and the exception message.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
