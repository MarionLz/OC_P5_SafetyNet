package com.openclassrooms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.IDataReaderRepository;

public class DataReaderServiceTest {
	
	@Mock
	private IDataReaderRepository dataReaderRepository;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void testThatGetDataModelReturnsDataModel() {
				
		List<Person> persons = Arrays.asList(
			new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
			new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com")
		);
				
		List<Firestation> firestations = Arrays.asList(
			new Firestation("1509 Culver St", "3"),
			new Firestation("29 15th St", "2")
		);
		
		List<MedicalRecord> medicalrecords = Arrays.asList(
			new MedicalRecord("John", "Boyd", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
			new MedicalRecord("Jacob", "Boyd", "03/06/1989", new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"}, new String[]{})
		);
				
		DataModel testDataModel = new DataModel(persons, firestations, medicalrecords);
		
		when(dataReaderRepository.getDataModel()).thenReturn(testDataModel);
		
		DataModel result = dataReaderRepository.getDataModel();
		
		assertEquals(testDataModel, result);
		verify(dataReaderRepository, Mockito.times(1)).getDataModel();
	}

}
