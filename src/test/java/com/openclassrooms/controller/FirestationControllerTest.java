package com.openclassrooms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.DTO.firestation.NbAdultAndChildrenDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsByStationsDTO;
import com.openclassrooms.safetynet.DTO.firestation.FirestationResponseDTO;
import com.openclassrooms.safetynet.controller.FirestationController;
import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FirestationService;

@ExtendWith(MockitoExtension.class)
public class FirestationControllerTest {

	@Mock
	private FirestationService firestationService;
	
    @InjectMocks
    private FirestationController responseController;
    
    private MockMvc mockMvc;
	
    @BeforeEach
	void setUp() {
    	
	    responseController = new FirestationController(firestationService);
	    mockMvc = MockMvcBuilders.standaloneSetup(responseController).build();
    }
    
	@Test
	public void testGetPersonsCoveredByStation_Success() throws Exception {
		
		List<PersonsByStationsDTO> persons = Arrays.asList(
				new PersonsByStationsDTO("John", "Boyd", "1509 Culver St", "841-874-6512"),
				new PersonsByStationsDTO("Tenley", "Boyd", "1509 Culver St", "841-874-6512")
		);
		
		NbAdultAndChildrenDTO nbAdultAndChildren = new NbAdultAndChildrenDTO(1, 1);
		
		FirestationResponseDTO mockResponse = new FirestationResponseDTO(persons, nbAdultAndChildren);
		
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
		
		when(firestationService.getPersonsByStations("1")).thenReturn(mockResponse);
		
		mockMvc.perform(get("/firestation")
				.param("stationNumber", "1"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
	
	@Test
	public void testAddFirestation_Success() {
		
		Firestation firestation = new Firestation("14 rue des Geeks", "2");
		
		mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isOk());

        verify(firestationService, times(1)).addFirestation(any(Person.class));
	}
	
	@Test
	public void testUpdateFirestation_Success() throws Exception {
				
		Firestation firestation = new Firestation("14 rue des Geeks", "2");
								
		mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isOk());

        verify(firestationService, times(1)).updateFirestation(any(Person.class));
	}
	
	@Test
	public void testDeleteFirestation_Success() throws Exception {
						
		Firestation firestation = new Firestation("14 rue des Geeks", "2");

		mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isOk());

        verify(firestationService, times(1)).deletePerson(any(Person.class));
	}
}
