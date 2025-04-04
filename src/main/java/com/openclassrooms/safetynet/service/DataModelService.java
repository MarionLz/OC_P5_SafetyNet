package com.openclassrooms.safetynet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.DataModel;

@Service
public class DataModelService {
	
    private static final Logger logger = LogManager.getLogger(DataModelService.class);	

	private DataModel dataModel;
	
    public DataModelService() {
        logger.info("DataModelService instance created: " + this);
    }
	
    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }
}