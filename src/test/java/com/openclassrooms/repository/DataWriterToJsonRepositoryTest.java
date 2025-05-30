package com.openclassrooms.repository;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.exceptions.JsonFileException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.DataWriterToJsonRepository;
import com.openclassrooms.safetynet.service.DataModelService;

/**
 * Unit test class for {@link DataWriterToJsonRepository}.
 * This class tests the functionality of saving data to a JSON file, including both successful 
 * and failure scenarios when there are issues with file access.
 */
@ExtendWith(MockitoExtension.class)
public class DataWriterToJsonRepositoryTest {

	@Mock
    private Resource jsonFile;
	
	@Mock
    private DataModelService dataModelService;

	@InjectMocks
	private DataWriterToJsonRepository repository;
	
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Tests the successful saving of data to a JSON file.
     * This test ensures that when the data is properly structured, the repository can save 
     * the data into a temporary file and that the file content matches the expected structure.
     * 
     * @throws IOException If there is an error creating or writing to the file.
     */
	@Test
	public void testSaveData_Success() throws IOException {
		
		List<Person> persons = Arrays.asList(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com")
		);
		List<Firestation> firestations = Arrays.asList(
				new Firestation("1509 Culver St", "3")
		);
		List<MedicalRecord> medicalrecords = Arrays.asList(
				new MedicalRecord("John", "Boyd", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"})
		);

		DataModel dataModel = new DataModel(persons, firestations, medicalrecords);
		
		File tempFile = File.createTempFile("test_data", ".json");
		tempFile.deleteOnExit();
		
        when(jsonFile.getFile()).thenReturn(tempFile);
        when(dataModelService.getDataModel()).thenReturn(dataModel);
        
        repository.setJsonFile(jsonFile);
        repository.saveData();
        
        DataModel result = objectMapper.readValue(tempFile, DataModel.class);
        
        assertNotNull(result);
        assertEquals(1, result.getPersons().size());
        assertEquals("John", result.getPersons().get(0).getFirstName());
        assertEquals(1, result.getFirestations().size());
        assertEquals("1509 Culver St", result.getFirestations().get(0).getAddress());
        assertEquals(1, result.getMedicalrecords().size());
        assertEquals("03/06/1984", result.getMedicalrecords().get(0).getBirthdate());
        verify(dataModelService).getDataModel();
	}
	
    /**
     * Tests that a {@link JsonFileException} is thrown when there is an error saving data to the JSON file.
     * This test simulates a failure in accessing the file, ensuring that the repository handles it properly.
     * 
     * @throws Exception If there is an unexpected error during the test execution.
     */
	@Test
    void testSaveData_ThrowsJsonFileException_OnIOException() throws Exception {

		when(jsonFile.getFile()).thenThrow(new IOException("Cannot access file"));

        repository.setJsonFile(jsonFile);
        JsonFileException exception = assertThrows(JsonFileException.class, () -> {
            repository.saveData();
        });

        assertEquals("Error saving data to JSON.", exception.getMessage());
        verify(jsonFile).getFile();
    }
}
