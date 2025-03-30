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

import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.ChildAlertService;
import com.openclassrooms.safetynet.service.DataReaderService;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {
	
	@Mock
	private DataReaderService dataReaderService;
	
	private DataModel dataModel;
	
	CommunityEmailService communityEmailService;
	
	@BeforeEach
	private void setUp() {
		
		dataModel = spy(new DataModel());
		when(dataReaderService.getDataModel()).thenReturn(dataModel);
		communityEmailService = new CommunityEmailService(dataReaderService);
	}
	
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

