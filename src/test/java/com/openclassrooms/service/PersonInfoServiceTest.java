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

import com.openclassrooms.safetynet.DTO.personInfo.PersonInfoPersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.personInfo.PersonInfoResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.DataReaderService;
import com.openclassrooms.safetynet.service.PersonInfoService;

@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {

	@Mock
	private DataReaderService dataReaderService;
	
	private DataModel dataModel;
	
	private PersonInfoService personInfoService;
	
	@BeforeEach
	private void setUp() {
		
		dataModel = spy(new DataModel());
		when(dataReaderService.getDataModel()).thenReturn(dataModel);
		personInfoService = new PersonInfoService(dataReaderService);
	}
	
	@Test
	public void testGetPersonInfoByLastName_Success() {
		
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
		
		doReturn(persons).when(dataModel).getPersons();
		doReturn(medicalrecords).when(dataModel).getMedicalrecords();
		
		PersonInfoResponseDTO result = personInfoService.getPersonInfoWithLastName("Boyd");
		List<PersonInfoPersonIdentityDTO> personsWithSameLastName = result.getPersonsWithSameLastName();
		
		assertEquals(2, personsWithSameLastName.size());
		assertEquals("Boyd", personsWithSameLastName.get(0).getLastName());
		assertEquals("Boyd", personsWithSameLastName.get(1).getLastName());
		assertEquals("jaboyd@email.com", personsWithSameLastName.get(0).getEmail());
		assertEquals("tenz@email.com", personsWithSameLastName.get(1).getEmail());
	}
}
