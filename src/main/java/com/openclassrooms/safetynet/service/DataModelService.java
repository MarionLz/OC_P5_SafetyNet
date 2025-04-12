package com.openclassrooms.safetynet.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.DataModel;

/**
 * Service responsible for storing and providing access to the application's in-memory data model.
 * This class acts as a central access point for other services to interact with the loaded data.
 * It stores a single instance of DataModel, typically loaded once during application startup.
 */
@Service
public class DataModelService {

    private DataModel dataModel;

    /**
     * Default constructor.
     */
    public DataModelService() {}

    /**
     * Retrieves the current data model.
     *
     * @return the current {@link DataModel} instance
     */
    public DataModel getDataModel() {
        return dataModel;
    }

    /**
     * Sets the application's data model. This method is typically called once during initialization.
     *
     * @param dataModel the {@link DataModel} instance to be stored
     */
    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }
}