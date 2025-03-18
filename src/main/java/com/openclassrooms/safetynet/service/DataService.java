package com.openclassrooms.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.repository.IDataReaderRepository;


@Service
public class DataService {
	
	private IDataReaderRepository dataReaderRepository;
	
	public DataService(IDataReaderRepository dataReaderRepository) {
		this.dataReaderRepository = dataReaderRepository;
	}
	
	public DataModel getDataModel() {
		return dataReaderRepository.getDataModel();
	}
}
