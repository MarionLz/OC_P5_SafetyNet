package com.openclassrooms.safetynet.exceptions;

/**
 * Custom exception class for handling errors related to JSON file operations.
 * This exception is thrown when there are issues processing or interacting with JSON files.
 */
public class JsonFileException extends RuntimeException {

	private static final long serialVersionUID = 100L;

	public JsonFileException(String message) {
        super(message);
    }
}
