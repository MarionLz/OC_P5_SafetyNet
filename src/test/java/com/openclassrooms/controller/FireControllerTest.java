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
import com.openclassrooms.safetynet.DTO.fire.FirePersonsAtAddressDTO;
import com.openclassrooms.safetynet.DTO.fire.FireResponseDTO;
import com.openclassrooms.safetynet.controller.FireController;
import com.openclassrooms.safetynet.service.FireService;

@ExtendWith(MockitoExtension.class)
public class FireControllerTest {

	@Mock
	private FireService fireService;
	
    @InjectMocks
    private FireController fireController;
    
    private MockMvc mockMvc;
    
    @Test
    public void testGetPersonsAtAddress_Success() throws Exception {
    	
    	fireController = new FireController(fireService);
		mockMvc = MockMvcBuilders.standaloneSetup(fireController).build();
		
		List<FirePersonsAtAddressDTO> persons = Arrays.asList(
				new FirePersonsAtAddressDTO("Boyd", "841-874-6512", "42", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
				new FirePersonsAtAddressDTO("Boyd", "841-874-6513", "12", new String[] {}, new String[]{"peanut"})
		);
		
		FireResponseDTO mockResponse = new FireResponseDTO(persons, "3");
		
		ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
        
		when(fireService.getPersonsAtAddress("1509 Culver St")).thenReturn(mockResponse);
		
		mockMvc.perform(get("/fire")
				.param("address", "1509 Culver St"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
    }
}
