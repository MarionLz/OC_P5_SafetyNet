package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.DataModel;

public interface IDataReaderRepository {

	public void loadData();
	DataModel getDataModel();
}
