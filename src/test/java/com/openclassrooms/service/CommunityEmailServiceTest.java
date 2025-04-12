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

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.CommunityEmailService;
import com.openclassrooms.safetynet.service.DataModelService;

/**
 * Unit test class for {@link CommunityEmailService}.
 * This class contains tests to validate the functionality of retrieving community emails
 * for persons living in a specified city.
 */
@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {
	
	@Mock
	private DataModelService dataModelService;
	
	private DataModel dataModel;
	
	CommunityEmailService communityEmailService;
	
    /**
     * Sets up the test environment by initializing the data model and CommunityEmailService
     * before each test method is executed.
     */
	@BeforeEach
	private void setUp() {
		
		dataModel = spy(new DataModel());
		when(dataModelService.getDataModel()).thenReturn(dataModel);
		communityEmailService = new CommunityEmailService(dataModelService);
	}
	
    /**
     * Tests the successful retrieval of community emails for all persons living in a specified city.
     * This test checks if the service correctly returns the list of email addresses of people
     * living in a given city.
     */
	@Test
	public void testGetCommunityEmails_Success() {
		
		List<Person> persons = Arrays.asList(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
			    new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"),
			    new Person("Tony", "Cooper", "112 Steppes Pl", "Culver", "97451", "841-874-6874", "tcoop@ymail.com"),
			    new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com")
		);
		
		doReturn(persons).when(dataModel).getPersons();
		
		List<String> result = communityEmailService.getCommunityEmails("Culver");
		
		assertEquals(4, result.size());
		assertEquals("jaboyd@email.com", result.get(0));
	}
}

