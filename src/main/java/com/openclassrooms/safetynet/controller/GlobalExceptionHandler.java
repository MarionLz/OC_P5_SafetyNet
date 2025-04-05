package com.openclassrooms.safetynet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<String> handlePersonAlreadyExists(ResourceAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
