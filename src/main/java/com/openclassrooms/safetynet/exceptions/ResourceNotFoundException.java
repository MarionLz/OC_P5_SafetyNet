package com.openclassrooms.safetynet.exceptions;

/**
 * Custom exception class for handling scenarios where a resource is not found.
 * This exception is thrown when an attempt is made to access a resource that does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 102L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}