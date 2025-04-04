package com.openclassrooms.safetynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.safetynet.repository.DataReaderFromJsonRepository;
import com.openclassrooms.safetynet.repository.IDataReaderRepository;
import com.openclassrooms.safetynet.service.DataModelService;

@SpringBootApplication
public class SafetynetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetApplication.class, args);
		
	}

}
