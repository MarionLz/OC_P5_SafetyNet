package com.openclassrooms.safetynet.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JsonFileException extends RuntimeException {

    public JsonFileException(String message) {
        super(message);
    }

}
