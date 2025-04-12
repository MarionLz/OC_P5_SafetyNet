package com.openclassrooms.safetynet.repository;

/**
 * Interface for writing the application's data model to an external data source.
 * 
 * Implementations are responsible for persisting data, typically to a JSON file
 * or any other storage mechanism used by the application.
 */
public interface IDataWriterRepository {

    /**
     * Persists the current state of the application's data model.
     */
	public void saveData();
}
