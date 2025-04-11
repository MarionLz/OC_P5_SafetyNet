package com.openclassrooms.safetynet.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 102L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}