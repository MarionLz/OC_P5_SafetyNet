package com.openclassrooms.controller;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.DTO.ResidentDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.DTO.personInfo.PersonInfoResponseDTO;
import com.openclassrooms.safetynet.controller.ChildAlertController;
import com.openclassrooms.safetynet.service.ChildAlertService;

@ExtendWith(MockitoExtension.class)
public class PersonInfoControllerTest {

	@Mock
	private PersonInfoService personInfoService;
	
    @InjectMocks
    private PersonInfoController personInfoController;
    
    private MockMvc mockMvc;
    
    @Test
    public void testGetPersonInfo_Success() {
    	
    	personInfoController = new PersonInfoController(personInfoService);
		mockMvc = MockMvcBuilders.standaloneSetup(personInfoController).build();

		List<ResidentDTO> residents = Arrays.asList(
				new ResidentDTO("Boyd", "1509 Culver St", "42", "jaboyd@email.com", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}),
				new ResidentDTO("Boyd", "1509 Culver St", "12", "tenz@email.com", new String[] {}, new String[]{"peanut"})
		);
		
		PersonInfoResponseDTO mockResponse = new PersonInfoResponseDTO(residents);
		
		ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
		
        
		when(personInfoService.getPersonInfo(Arrays.asList("Boyd"))).thenReturn(mockResponse);
		
		mockMvc.perform(get("/personInfo")
				.param("lastName", "Boyd"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
    }
}
