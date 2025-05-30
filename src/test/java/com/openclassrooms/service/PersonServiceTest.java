package com.openclassrooms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.openclassrooms.safetynet.exceptions.ResourceNotFoundException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;
import com.openclassrooms.safetynet.service.DataModelService;
import com.openclassrooms.safetynet.service.PersonService;

/**
 * Unit test class for {@link PersonService}.
 * This class tests the functionalities of adding, updating, and deleting persons in the system.
 */
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@Mock
	private IDataWriterRepository writerRepository;
	
	@Mock
	private DataModelService dataModelService;
	
	private PersonService personService;
	private List<Person> persons;
	private DataModel dataModel;
	
    /**
     * Sets up the test environment before each test method is executed.
     * Initializes the personService, mock data, and data model.
     */
	@BeforeEach
	void setUp() {
		personService = new PersonService(writerRepository, dataModelService);
		persons = new ArrayList<>(Arrays.asList(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
			    new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com")
		));
		dataModel = new DataModel();
		dataModel.setPersons(persons);
		
        when(dataModelService.getDataModel()).thenReturn(dataModel);
	}
	
    /**
     * Tests the successful addition of a person to the data model.
     * Verifies that the person is added and the data is saved.
     */
	@Test
	public void testAddPerson_Success() {
		
		Person newPerson = new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "tenz@email.com");
		
		personService.addPerson(newPerson);
		
		assertTrue(dataModel.getPersons().contains(newPerson));
		verify(writerRepository, times(1)).saveData();
	}
	
    /**
     * Tests the scenario where an attempt is made to add a person that already exists.
     * Verifies that a {@link ResourceAlreadyExistsException} is thrown.
     */
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
	
    /**
     * Tests the successful update of an existing person.
     * Verifies that the person's information is updated and the data is saved.
     */
	@Test
	public void testUpdatePerson_Success() {
		
    	Person updatedPerson = new Person("John", "Boyd", "1509 Culver St", "Toulouse", "97451", "841-874-6512", "john.boyd@email.com");
    	
    	personService.updatePerson(updatedPerson);
    	
        assertEquals("Toulouse", dataModel.getPersons().get(0).getCity());
        assertEquals("john.boyd@email.com", dataModel.getPersons().get(0).getEmail());
		verify(writerRepository, times(1)).saveData();
	}
	
    /**
     * Tests the scenario where an attempt is made to update a person that does not exist.
     * Verifies that a {@link ResourceNotFoundException} is thrown.
     */
	@Test
    public void testUpdatePerson_WhenPersonNotFound_ShouldThrowException() {

		Person nonExistentPerson = new Person("Unknown", "Anonyme", "1 rue des platanes", "Toulouse", "31000", "01010101", "nonexistent@email.com");

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
            () -> personService.updatePerson(nonExistentPerson)
        );

        assertTrue(exception.getMessage().contains("Unknown"));
        assertTrue(exception.getMessage().contains("Anonyme"));
        verify(writerRepository, never()).saveData();
    }
	
    /**
     * Tests the successful deletion of a person.
     * Verifies that the person is removed from the data model and the data is saved.
     */
	@Test
    public void testDeletePerson_Success() {

        personService.deletePerson("John", "Boyd");

        assertEquals(1, dataModel.getPersons().size());
        assertEquals("Tenley", dataModel.getPersons().get(0).getFirstName());
        verify(writerRepository).saveData();
    }
	
    /**
     * Tests the scenario where an attempt is made to delete a person that does not exist.
     * Verifies that a {@link ResourceNotFoundException} is thrown.
     */
	@Test
	public void testDeletePerson_WhenPersonNotFound_ShouldThrowException() {
		
		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
            () -> personService.deletePerson("Unknown", "Anonyme")
        );

        assertTrue(exception.getMessage().contains("Unknown"));
        assertTrue(exception.getMessage().contains("Anonyme"));
        verify(writerRepository, never()).saveData();
	}
}
