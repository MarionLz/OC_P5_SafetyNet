package com.openclassrooms.safetynet.exceptions;

public class JsonFileException extends RuntimeException {

	private static final long serialVersionUID = 100L;

	public JsonFileException(String message) {
        super(message);
    }
}
