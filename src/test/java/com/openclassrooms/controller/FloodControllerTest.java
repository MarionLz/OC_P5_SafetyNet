package com.openclassrooms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.DTO.ResidentDTO;
import com.openclassrooms.safetynet.DTO.flood.FloodResponseDTO;
import com.openclassrooms.safetynet.DTO.flood.HouseholdDTO;
import com.openclassrooms.safetynet.DTO.flood.StationDTO;
import com.openclassrooms.safetynet.controller.FloodController;
import com.openclassrooms.safetynet.service.FloodService;

/**
 * Unit test for the {@link FloodController} class.
 * This test verifies the functionality of the FloodController's methods
 * for handling requests related to flood stations, including retrieving 
 * the households served by specific stations during a flood event.
 */
@ExtendWith(MockitoExtension.class)
public class FloodControllerTest {
	
	@Mock
	private FloodService floodService;
	
    @InjectMocks
    private FloodController floodController;
    
    private MockMvc mockMvc;
	
    /**
     * Tests the {@link FloodController#getHouseholdsByStations(List)} method.
     * This test simulates a GET request to the '/flood/stations' endpoint to retrieve
     * the households served by specific flood stations and verifies the response content
     * and HTTP status.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testGetHouseholdsServedByStation_Success() throws Exception {
		
		floodController = new FloodController(floodService);
		mockMvc = MockMvcBuilders.standaloneSetup(floodController).build();
		
		List<ResidentDTO> residents = Arrays.asList(
				new ResidentDTO("Boyd", "841-874-6512", "42", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
				new ResidentDTO("Boyd", "841-874-6513", "12", new String[] {}, new String[]{"peanut"})
		);
		
		List<HouseholdDTO> households = Arrays.asList(
				new HouseholdDTO("1509 Culver St", residents)
		);
		
		List<StationDTO> stations = Arrays.asList(
				new StationDTO("3", households)
		);
						
		FloodResponseDTO mockResponse = new FloodResponseDTO(stations);
		
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
		
        
		when(floodService.getHouseholds(Arrays.asList("3"))).thenReturn(mockResponse);
		
		mockMvc.perform(get("/flood/stations")
				.param("stations", "3"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
}
