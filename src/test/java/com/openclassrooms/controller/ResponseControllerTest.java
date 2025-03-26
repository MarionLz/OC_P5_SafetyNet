package com.openclassrooms.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAtAdressResponseDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.DTO.childAlert.OtherFamilyMembersDTO;
import com.openclassrooms.safetynet.DTO.firestation.NbAdultAndChildrenDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsByStationsDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsCoveredByStationResponseDTO;
import com.openclassrooms.safetynet.controller.ResponseController;
import com.openclassrooms.safetynet.service.PersonsCoveredByStationService;

@ExtendWith(MockitoExtension.class)
public class ResponseControllerTest {

	@Mock
	private PersonsCoveredByStationService personsCoveredByStationService;
	
    @InjectMocks
    private ResponseController responseController;
    
    private MockMvc mockMvc;
	
	@Test
	public void getPersonsCoveredByStationTest() throws Exception {
		
	    responseController = new ResponseController(personsCoveredByStationService);
	    mockMvc = MockMvcBuilders.standaloneSetup(responseController).build();
		
		List<PersonsByStationsDTO> persons = Arrays.asList(
				new PersonsByStationsDTO("John", "Boyd", "1509 Culver St", "841-874-6512"),
				new PersonsByStationsDTO("Tenley", "Boyd", "1509 Culver St", "841-874-6512")
		);
		
		NbAdultAndChildrenDTO nbAdultAndChildren = new NbAdultAndChildrenDTO(1, 1);
		
		PersonsCoveredByStationResponseDTO mockResponse = new PersonsCoveredByStationResponseDTO(persons, nbAdultAndChildren);
		
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
		
		when(personsCoveredByStationService.getPersonsByStations("1")).thenReturn(mockResponse);
		
		mockMvc.perform(get("/firestation")
				.param("stationNumber", "1"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
	
	@Test
	public void getChildAtAddress() throws Exception {
		
		responseController = new ResponseController(childAtAdressService);
		mockMvc = MockMvcBuilders.standaloneSetup(responseController).build();
		
		List<ChildDTO> children = Arrays.asList(
				new ChildDTO("Tenley", "Boyd", "02/18/2012"),
				new ChildDTO("Roger", "Boyd", "09/06/2017")
		);
		
		List<OtherFamilyMembersDTO> otherFamilyMembers = Arrays.asList(
				new OtherFamilyMembersDTO("John", "Boyd"),
				new OtherFamilyMembersDTO("Jacob", "Boyd"),
				new OtherFamilyMembersDTO("Felicia", "Boyd")
		);
		
		ChildAtAdressResponseDTO mockResponse = new ChildAtAdressResponseDTO(children, otherFamilyMembers);
		
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
		
		when(childAtAdressService.getChildAtAdressService("1509 Culver St")).thenReturn(mockResponse);
		
		mockMvc.perform(get("/childAlert")
				.param("address", "1509 Culver St"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
		
	}
}
