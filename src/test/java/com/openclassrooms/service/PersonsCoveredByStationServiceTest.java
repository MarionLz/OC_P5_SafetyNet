package com.openclassrooms.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.DTO.NbAdultAndChildrenDTO;
import com.openclassrooms.safetynet.DTO.PersonsByStationsDTO;
import com.openclassrooms.safetynet.DTO.PersonsCoveredByStationResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.DataReaderService;
import com.openclassrooms.safetynet.service.PersonsCoveredByStationService;

@ExtendWith(MockitoExtension.class)
public class PersonsCoveredByStationServiceTest {
		
	@Mock
	private DataReaderService dataReaderService;
	
	private DataModel dataModel;
	
	private PersonsCoveredByStationService personsCoveredByStation;
	
	@BeforeEach
	private void setUp() {
		
		dataModel = spy(new DataModel());
		when(dataReaderService.getDataModel()).thenReturn(dataModel);
        personsCoveredByStation = new PersonsCoveredByStationService(dataReaderService);
	}
	
	@Test
	public void getPersonsByStationsTest() {

		List<Firestation> firestations = Arrays.asList(
			new Firestation("1509 Culver St", "3")
		);
		List<Person> persons = Arrays.asList(
			new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
		    new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com")
		);
		List<MedicalRecord> medicalrecords = Arrays.asList(
			new MedicalRecord("John", "Boyd", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
			new MedicalRecord("Tenley", "Boyd", "02/18/2012", new String[]{}, new String[]{"peanut"})
		);
		
		doReturn(firestations).when(dataModel).getFirestations();
		doReturn(persons).when(dataModel).getPersons();
		doReturn(medicalrecords).when(dataModel).getMedicalrecords();
		
		PersonsCoveredByStationResponseDTO result = personsCoveredByStation.getPersonsByStations("3");
		
		List<PersonsByStationsDTO> personByStation = result.getPersons();
		NbAdultAndChildrenDTO nbAdultAndChildren = result.getNbAdultAndChildren();
		
		assertEquals(personByStation.size(), 2);
		assertEquals(personByStation.get(0).getFirstName(), "John");
		assertEquals(personByStation.get(1).getFirstName(), "Tenley");
		assertEquals(nbAdultAndChildren.getNbAdult(), 1);
		assertEquals(nbAdultAndChildren.getNbChildren(), 1);
	}
	
	/*@Test
	public void getPersonsByStationsTestWithUnknownStationTest() {
		
		List<Firestation> firestations = Arrays.asList(
			new Firestation("1509 Culver St", "3")
		);
		List<Person> persons = Arrays.asList(
			new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
		    new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com")
		);
		List<MedicalRecord> medicalrecords = Arrays.asList(
			new MedicalRecord("John", "Boyd", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
			new MedicalRecord("Tenley", "Boyd", "02/18/2012", new String[]{}, new String[]{"peanut"})
		);
		
		doReturn(firestations).when(dataModel).getFirestations();
		doReturn(persons).when(dataModel).getPersons();
		doReturn(medicalrecords).when(dataModel).getMedicalrecords();
	}*/
	
}
