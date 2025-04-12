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
import com.openclassrooms.safetynet.DTO.firestation.FirestationResponseDTO;
import com.openclassrooms.safetynet.DTO.firestation.PersonsByStationsDTO;
import com.openclassrooms.safetynet.controller.FirestationController;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.FirestationService;

/**
 * Unit test for the {@link FirestationController} class.
 * This test verifies the functionality of the FirestationController's methods
 * for handling requests related to fire stations, including adding, updating, 
 * deleting, and retrieving data about fire stations and the persons they cover.
 */
@ExtendWith(MockitoExtension.class)
public class FirestationControllerTest {

	@Mock
	private FirestationService firestationService;
	
    @InjectMocks
    private FirestationController responseController;
    
    private MockMvc mockMvc;
	
    /**
     * Sets up the test environment before each test case.
     * Initializes the FirestationController and MockMvc.
     */
    @BeforeEach
	void setUp() {
    	
	    responseController = new FirestationController(firestationService);
	    mockMvc = MockMvcBuilders.standaloneSetup(responseController).build();
    }
    
    /**
     * Tests the {@link FirestationController#getPersonsByStations(String)} method.
     * This test simulates a GET request to the '/firestation' endpoint to retrieve
     * the persons covered by a specific fire station and verifies the response content
     * and HTTP status.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testGetPersonsCoveredByStation_Success() throws Exception {
		
		List<PersonsByStationsDTO> persons = Arrays.asList(
				new PersonsByStationsDTO("John", "Boyd", "1509 Culver St", "841-874-6512"),
				new PersonsByStationsDTO("Tenley", "Boyd", "1509 Culver St", "841-874-6512")
		);
				
		FirestationResponseDTO mockResponse = new FirestationResponseDTO(persons, 1, 1);
		
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
		
		when(firestationService.getPersonsByStations("1")).thenReturn(mockResponse);
		
		mockMvc.perform(get("/firestation")
				.param("stationNumber", "1"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
	
    /**
     * Tests the {@link FirestationController#addFirestation(Firestation)} method.
     * This test simulates a POST request to the '/firestation' endpoint to add a new
     * fire station and verifies that the service's add method is called once.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testAddFirestation_Success() throws Exception {
		
		Firestation firestation = new Firestation("14 rue des Geeks", "2");
		
		mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isOk());

        verify(firestationService, times(1)).addFirestation(any(Firestation.class));
	}
	
    /**
     * Tests the {@link FirestationController#updateFirestation(Firestation)} method.
     * This test simulates a PUT request to the '/firestation' endpoint to update an existing
     * fire station and verifies that the service's update method is called once.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testUpdateFirestation_Success() throws Exception {
				
		Firestation firestation = new Firestation("14 rue des Geeks", "2");
								
		mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isOk());

        verify(firestationService, times(1)).updateFirestation(any(Firestation.class));
	}
	
    /**
     * Tests the {@link FirestationController#deleteFirestation(String)} method.
     * This test simulates a DELETE request to the '/firestation' endpoint to delete an
     * existing fire station and verifies that the service's delete method is called once.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testDeleteFirestation_Success() throws Exception {
						
		mockMvc.perform(delete("/firestation")
                .param("address", "14 rue des Geeks"))
                .andExpect(status().isOk());

        verify(firestationService, times(1)).deleteFirestation("14 rue des Geeks");
	}
}
