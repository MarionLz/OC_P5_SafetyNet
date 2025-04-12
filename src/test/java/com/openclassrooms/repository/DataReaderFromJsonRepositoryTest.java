package com.openclassrooms.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.openclassrooms.safetynet.exceptions.JsonFileException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.repository.DataReaderFromJsonRepository;
import com.openclassrooms.safetynet.service.DataModelService;

/**
 * Unit test class for {@link DataReaderFromJsonRepository}.
 * This class tests the methods of the repository that load data from a JSON file,
 * ensuring that the data is parsed correctly or exceptions are thrown when there are issues.
 */
@ExtendWith(MockitoExtension.class)
public class DataReaderFromJsonRepositoryTest {

	@Mock
    private Resource jsonFile;
	
	@Mock
	private DataModelService dataModelService;
	
	@InjectMocks
	private DataReaderFromJsonRepository repository;
	
    /**
     * Tests the successful loading of data from a JSON file.
     * This test ensures that when valid JSON is provided, the data is correctly parsed
     * and the resulting data model is set in the service.
     *
     * @throws IOException If there is an error reading the JSON file.
     */
	@Test
	public void testLoadDataFromJson_Success() throws IOException {
		
		String jsonData = """
				{
				    "persons": [
				        { "firstName": "John", "lastName": "Boyd", "address": "1509 Culver St", "city": "Culver", "zip": "97451", "phone": "841-874-6512", "email": "jaboyd@email.com" },
				        { "firstName": "Jacob", "lastName": "Boyd", "address": "1509 Culver St", "city": "Culver", "zip": "97451", "phone": "841-874-6513", "email": "drk@email.com" }
				    ],
				    "firestations": [
				        { "address": "1509 Culver St", "station": "3" },
				        { "address": "29 15th St", "station": "2" }
				    ],
				    "medicalrecords": [
				        { "firstName": "John", "lastName": "Boyd", "birthdate": "03/06/1984", "medications": ["aznol:350mg"], "allergies": ["nillacilan"] },
				        { "firstName": "Jacob", "lastName": "Boyd", "birthdate": "03/06/1989", "medications": ["pharmacol:5000mg"], "allergies": [] }
				    ]
				}
				""";
		
        InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());
        
        when(jsonFile.getInputStream()).thenReturn(inputStream);
        
        repository.setJsonFile(jsonFile);
        repository.loadData();
        
        verify(dataModelService, times(1)).setDataModel(Mockito.any(DataModel.class));
    }
	
    /**
     * Tests that a {@link JsonFileException} is thrown when the JSON file cannot be found.
     * This test ensures that the repository handles missing files gracefully.
     *
     * @throws IOException If an error occurs while reading the JSON file.
     */
	@Test
	public void testLoadDataFromJson_FileNotFound_ShouldThrowException() throws IOException {
		
        when(jsonFile.getInputStream()).thenReturn(null);
        
        repository.setJsonFile(jsonFile);
        JsonFileException exception = assertThrows(JsonFileException.class, repository::loadData);
        assertEquals("JSON not found.", exception.getMessage());
	}
	
    /**
     * Tests that a {@link JsonFileException} is thrown when the JSON file has an incorrect structure
     * and cannot be parsed into the expected objects.
     * In this case, the JSON contains an unexpected object ("animal").
     *
     * @throws IOException If an error occurs while reading the JSON file.
     */
	@Test
	public void testLoadDataFromJson_InvalidJsonWithWrongObject_ShouldThrowException() throws IOException {
		
		String jsonData = """
				{
			    "persons": [
			        { "firstName": "John", "lastName": "Boyd", "address": "1509 Culver St", "city": "Culver", "zip": "97451", "phone": "841-874-6512", "email": "jaboyd@email.com" },
			        { "firstName": "Jacob", "lastName": "Boyd", "address": "1509 Culver St", "city": "Culver", "zip": "97451", "phone": "841-874-6513", "email": "drk@email.com" }
			    ],
			    "animal": [
			        { "name": "Molky", "age": "6" },
			    ],
			    "medicalrecords": [
			        { "firstName": "John", "lastName": "Boyd", "birthdate": "03/06/1984", "medications": ["aznol:350mg"], "allergies": ["nillacilan"] },
			        { "firstName": "Jacob", "lastName": "Boyd", "birthdate": "03/06/1989", "medications": ["pharmacol:5000mg"], "allergies": [] }
			    ]
			}
			""";
		
        InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());
        
        when(jsonFile.getInputStream()).thenReturn(inputStream);
        
        repository.setJsonFile(jsonFile);
        JsonFileException exception = assertThrows(JsonFileException.class, repository::loadData);
        assertEquals("JSON empty or unreadable.", exception.getMessage());
	}
	
    /**
     * Tests that a {@link JsonFileException} is thrown when the JSON file contains an empty field
     * (e.g., an empty "firstName" for a person). This ensures proper validation of the JSON content.
     *
     * @throws IOException If an error occurs while reading the JSON file.
     */
	@Test
    public void testLoadDataFromJson_InvalidJsonWithEmptyField_ShouldThrowException() throws IOException {
		
		String jsonData = """
				{
				    "persons": [
				        { "firstName": "", "lastName": "Boyd", "address": "1509 Culver St", "city": "Culver", "zip": "97451", "phone": "841-874-6512", "email": "jaboyd@email.com" },
				        { "firstName": "Jacob", "lastName": "Boyd", "address": "1509 Culver St", "city": "Culver", "zip": "97451", "phone": "841-874-6513", "email": "drk@email.com" }
				    ],
				    "firestations": [
				        { "address": "1509 Culver St", "station": "3" },
				        { "address": "29 15th St", "station": "2" }
				    ],
				    "medicalrecords": [
				        { "firstName": "John", "lastName": "Boyd", "birthdate": "03/06/1984", "medications": ["aznol:350mg"], "allergies": ["nillacilan"] },
				        { "firstName": "Jacob", "lastName": "Boyd", "birthdate": "03/06/1989", "medications": ["pharmacol:5000mg"], "allergies": [] }
				    ]
				}
				""";
		
		InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());
		        
		when(jsonFile.getInputStream()).thenReturn(inputStream);
		
        repository.setJsonFile(jsonFile);
        JsonFileException exception = assertThrows(JsonFileException.class, repository::loadData);
        assertEquals("JSON validation error :\n- persons[0].firstName : FisrtName may not be empty.\n", exception.getMessage());
	}
}
