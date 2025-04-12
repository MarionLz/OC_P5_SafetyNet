package com.openclassrooms.safetynet.exceptions;

/**
 * Custom exception class for handling scenarios where a resource already exists.
 * This exception is thrown when an attempt is made to create or add a resource that already exists.
 */
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}