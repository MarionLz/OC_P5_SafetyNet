package com.openclassrooms.service;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.DTO.phoneAlert.PhoneAlertResponseDTO;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.DataModelService;
import com.openclassrooms.safetynet.service.PhoneAlertService;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceTest {

	@Mock
	private DataModelService dataModelService;
	
	private DataModel dataModel;
	
	PhoneAlertService phoneAlertService;
	
	@BeforeEach
	private void setUp() {
		
		dataModel = spy(new DataModel());
		when(dataModelService.getDataModel()).thenReturn(dataModel);
		phoneAlertService = new PhoneAlertService(dataModelService);
	}
	
	@Test
	public void testGetPhoneNumbersCoveredByStation_Success() {
		
		List<Firestation> firestations = Arrays.asList(
				new Firestation("947 E. Rose Dr", "1"),
				new Firestation("644 Gershwin Cir", "1")
		);
		List<Person> persons = Arrays.asList(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
			    new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"),
			    new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
			    new Person("Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com")
		);
		
		doReturn(firestations).when(dataModel).getFirestations();
		doReturn(persons).when(dataModel).getPersons();
		
		PhoneAlertResponseDTO result = phoneAlertService.getPhoneNumbersCoveredByStation("1");
		List<String> phoneNumbers = result.getPhoneNumber();
		
		assertEquals("841-874-6512", phoneNumbers.get(0));
		assertEquals("841-874-7784", phoneNumbers.get(1));
	}
}
