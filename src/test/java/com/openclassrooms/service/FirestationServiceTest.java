package com.openclassrooms.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.DTO.firestation.NbAdultAndChildrenDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsByStationsDTO;
import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;
import com.openclassrooms.safetynet.exceptions.ResourceNotFoundException;
import com.openclassrooms.safetynet.DTO.firestation.FirestationResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;
import com.openclassrooms.safetynet.service.DataModelService;
import com.openclassrooms.safetynet.service.FirestationService;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
		
	@Mock
	private DataModelService dataModelService;
	
	@Mock
	private IDataWriterRepository writerRepository;
	
	private DataModel dataModel;
	private FirestationService firestationService;
	
	@BeforeEach
	private void setUp() {
		
		firestationService = new FirestationService(writerRepository, dataModelService);
		dataModel = new DataModel();
		List<Firestation> firestations = new ArrayList<>(Arrays.asList(
				new Firestation("1509 Culver St", "3"),
				new Firestation("112 Steppes Pl", "4")
		));
		dataModel.setFirestations(firestations);
        when(dataModelService.getDataModel()).thenReturn(dataModel);
	}
	
	@Test
	public void testGetPersonsByStations_Success() {
		
		List<Person> persons = Arrays.asList(
			new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
		    new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com")
		);
		List<MedicalRecord> medicalrecords = Arrays.asList(
			new MedicalRecord("John", "Boyd", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
			new MedicalRecord("Tenley", "Boyd", "02/18/2012", new String[]{}, new String[]{"peanut"})
		);
		
		dataModel.setPersons(persons);
		dataModel.setMedicalrecords(medicalrecords);
		
		FirestationResponseDTO result = firestationService.getPersonsByStations("3");
		
		List<PersonsByStationsDTO> personByStation = result.getPersons();
		NbAdultAndChildrenDTO nbAdultAndChildren = result.getNbAdultAndChildren();

		assertEquals(personByStation.size(), 2);
		assertEquals(personByStation.get(0).getFirstName(), "John");
		assertEquals(personByStation.get(1).getFirstName(), "Tenley");
		assertEquals(nbAdultAndChildren.getNbAdult(), 1);
		assertEquals(nbAdultAndChildren.getNbChildren(), 1);
	}
	
	@Test
	public void testAddPerson_Success() {
        
		Firestation newFirestation = new Firestation("14 rue des Geeks", "2");
		
		firestationService.addFirestation(newFirestation);
		
		assertTrue(dataModel.getFirestations().contains(newFirestation));
		verify(writerRepository, times(1)).saveData();
	}
	
	@Test
    public void testAddFirestation_WhenFirestationAlreadyExists_ShouldThrowException() {
    	
		Firestation duplicate = new Firestation("1509 Culver St", "3");
    	
    	ResourceAlreadyExistsException exception = assertThrows(
    		   ResourceAlreadyExistsException.class,
            () -> firestationService.addFirestation(duplicate)
        );

        assertTrue(exception.getMessage().contains("Firestation already exists"));
        verify(writerRepository, never()).saveData();
    }
	
	@Test
	public void testUpdateFirestation_Success() {
		
		Firestation updatedFirestation = new Firestation("1509 Culver St", "6");
    	
		firestationService.updateFirestation(updatedFirestation);
    	
        assertEquals("6", dataModel.getFirestations().get(0).getStation());
		verify(writerRepository, times(1)).saveData();
	}
	
	@Test
    public void testUpdateFirestation_WhenFirestationNotFound_ShouldThrowException() {

		Firestation nonExistentFirestation = new Firestation("1 allee de l'inconnu", "6");

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
            () -> firestationService.updateFirestation(nonExistentFirestation)
        );

        assertTrue(exception.getMessage().contains("Firestation not found"));
        verify(writerRepository, never()).saveData();
    }
	
	@Test
    public void testDeleteFirestation_Success() {

        firestationService.deleteFirestation("1509 Culver St");

        assertEquals(1, dataModel.getFirestations().size());
        assertEquals("112 Steppes Pl", dataModel.getFirestations().get(0).getAddress());
        verify(writerRepository).saveData();
    }
	
	@Test
	public void testDeleteFirestation_WhenFirestationNotFound_ShouldThrowException() {
		
		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
            () -> firestationService.deleteFirestation("1 allee de l'inconnu")
        );

        assertTrue(exception.getMessage().contains("Firestation not found"));
        verify(writerRepository, never()).saveData();
	}
}
