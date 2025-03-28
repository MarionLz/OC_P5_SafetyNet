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
import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.ResidentDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.DTO.flood.FloodResponseDTO;
import com.openclassrooms.safetynet.DTO.flood.HouseholdDTO;
import com.openclassrooms.safetynet.DTO.flood.StationDTO;
import com.openclassrooms.safetynet.controller.ChildAlertController;
import com.openclassrooms.safetynet.service.ChildAlertService;

@ExtendWith(MockitoExtension.class)
public class FloodControllerTest {
	
	@Mock
	private FloodService floodService;
	
    @InjectMocks
    private FloodController floodController;
    
    private MockMvc mockMvc;
	
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
		
		when(floodService.getHouseholdsServedByStation("3")).thenReturn(mockResponse);
		
		mockMvc.perform(get("/flood/stations")
				.param("stations", "3"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
}
