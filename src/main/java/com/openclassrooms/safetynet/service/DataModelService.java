package com.openclassrooms.safetynet.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.DataModel;

@Service
public class DataModelService {

	private DataModel dataModel;
	
    public DataModelService() {}
	
    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }
}