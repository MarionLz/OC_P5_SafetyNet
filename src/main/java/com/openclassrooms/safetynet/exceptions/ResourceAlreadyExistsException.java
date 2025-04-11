package com.openclassrooms.safetynet.exceptions;


public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 101L;

	public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}