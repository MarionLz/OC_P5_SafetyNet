package com.openclassrooms.safetynet.exceptions;


public class ResourceAlreadyExistsException extends RuntimeException {
	
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}