package com.openclassrooms.safetynet.repository;

/**
 * Interface for reading data into the application.
 * 
 * Implementations of this interface are responsible for loading data 
 * (typically from an external source like a JSON file) into the application's data model.
 */
public interface IDataReaderRepository {

    /**
     * Loads and initializes data into the application.
     */
    void loadData();
}