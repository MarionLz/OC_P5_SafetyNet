package com.openclassrooms.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;
import com.openclassrooms.safetynet.service.DataModelService;
import com.openclassrooms.safetynet.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@Mock
	private IDataWriterRepository writerRepository;
	
	@Mock
	private DataModelService dataModelService;
	
	private PersonService personService;
	private List<Person> persons;
	private List<Firestation> firestations;
	private List<MedicalRecord> medicalRecords;
	private DataModel dataModel;
	
	@BeforeEach
	void setUp() {
		personService = new PersonService(writerRepository, dataModelService);
		persons = new ArrayList<>(Arrays.asList(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com")));
		firestations = Arrays.asList(new Firestation("1509 Culver St", "3"));
		medicalRecords = Arrays.asList(new MedicalRecord("John", "Boyd", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}));
		dataModel = new DataModel(persons, firestations, medicalRecords);
		
        when(dataModelService.getDataModel()).thenReturn(dataModel);
	}
	
	@Test
	public void testAddPerson_Success() {
		
		Person newPerson = new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "tenz@email.com");
		
		personService.addPerson(newPerson);
		
		assertTrue(dataModel.getPersons().contains(newPerson));
		verify(writerRepository, times(1)).saveData();
	}
	
    @Test
    public void testAddPerson_WhenPersonAlreadyExists_ShouldThrowException() {

    	Person duplicate = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");

       ResourceAlreadyExistsException exception = assertThrows(
    		   ResourceAlreadyExistsException.class,
            () -> personService.addPerson(duplicate)
        );

        assertTrue(exception.getMessage().contains("John"));
        assertTrue(exception.getMessage().contains("Boyd"));

        verify(writerRepository, never()).saveData();
    }
//	
//	@Test
////	public void testUpdatePerson_Success() {
////		
////		
////	}
////	
//	@Test
////	public void testDeletePerson_Success() {
////		
////	}
	

}
