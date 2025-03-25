package com.openclassrooms.safetynet.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.repository.IDataReaderRepository;


@Service
public class DataReaderService {
	
	private IDataReaderRepository dataReaderRepository;
	
	public DataReaderService(IDataReaderRepository dataReaderRepository) {
		this.dataReaderRepository = dataReaderRepository;
	}
	
	public DataModel getDataModel() {
		return dataReaderRepository.getDataModel();
	}
}
