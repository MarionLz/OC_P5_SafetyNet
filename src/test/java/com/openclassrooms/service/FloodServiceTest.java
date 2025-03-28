package com.openclassrooms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.DTO.ResidentDTO;
import com.openclassrooms.safetynet.DTO.flood.FloodResponseDTO;
import com.openclassrooms.safetynet.DTO.flood.HouseholdDTO;
import com.openclassrooms.safetynet.DTO.flood.StationDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.DataReaderService;
import com.openclassrooms.safetynet.service.FireService;

@ExtendWith(MockitoExtension.class)
public class FloodServiceTest {

	@Mock
	private DataReaderService dataReaderService;
	
	private DataModel dataModel;
	
	FloodService floodService;
	
	@BeforeEach
	private void setUp() {
		
		dataModel = spy(new DataModel());
		when(dataReaderService.getDataModel()).thenReturn(dataModel);
		floodService = new FloodService(dataReaderService);
	}
	
	@Test
	public void testGetHouseholds_Success() {
	
		List<Person> persons = Arrays.asList(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
			    new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"),
			    new Person("Tony", "Cooper", "112 Steppes Pl", "Culver", "97451", "841-874-6874", "tcoop@ymail.com"),
			    new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com")
		);
		List<MedicalRecord> medicalrecords = Arrays.asList(
				new MedicalRecord("John", "Boyd", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
				new MedicalRecord("Tenley", "Boyd", "02/18/2012", new String[]{}, new String[]{"peanut"}),
		        new MedicalRecord("Tony","Cooper", "03/06/1994", new String []{"hydrapermazol:300mg", "dodoxadin:30mg"}, new String[]{"shellfish"}),
		        new MedicalRecord("Clive", "Ferguson", "03/06/1994", new String[]{}, new String[]{"allergies"})
		);
		List<Firestation> firestations = Arrays.asList(
				new Firestation("1509 Culver St", "3"),
				new Firestation("112 Steppes Pl", "3")
		);
		
		doReturn(persons).when(dataModel).getPersons();
		doReturn(medicalrecords).when(dataModel).getMedicalrecords();
		doReturn(firestations).when(dataModel).getFirestations();
		
		FloodResponseDTO result = floodService.getHouseholds_Success("3");
		List<StationDTO> stationsSearched = result.getStationsSearched();
		String stationNumber = stationsSearched.get(0).getStationNumber();
		List<HouseholdDTO> households = stationsSearched.get(0).getHouseholds();
		List<ResidentDTO> residents = households.get(0).getResidents();
		

		assertEquals(1, stationsSearched.size());
		assertEquals("3", stationNumber);
		assertEquals(2, households.size());
		assertEquals("1509 Culver St", households.get(0).getAddress());
		assertEquals("112 Steppes Pl", households.get(1).getAddress());
		assertEquals(2, residents.size());
		assertEquals("Boyd", residents.get(1).getLastName());
	}
}
