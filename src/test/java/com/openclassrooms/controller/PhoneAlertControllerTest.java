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
import com.openclassrooms.safetynet.DTO.phoneAlert.PhoneAlertResponseDTO;
import com.openclassrooms.safetynet.controller.PhoneAlertController;
import com.openclassrooms.safetynet.service.PhoneAlertService;

/**
 * Unit test class for the {@link PhoneAlertController}.
 * This class tests the behavior of the controller's methods, specifically retrieving
 * phone numbers of children covered by a specified fire station.
 */
@ExtendWith(MockitoExtension.class)
public class PhoneAlertControllerTest {

	@Mock
	private PhoneAlertService phoneAlertService;
	
    @InjectMocks
    private PhoneAlertController phoneAlertController;
    
    private MockMvc mockMvc;
	
    /**
     * Tests the {@link PhoneAlertController#getPhoneNumbersCoveredByStation(String)} method to verify
     * the retrieval of phone numbers for children covered by a specified fire station.
     * This method simulates a GET HTTP request and checks that the response matches the expected JSON 
     * format returned by the service.
     *
     * @throws Exception If an error occurs while executing the request.
     */
	@Test
	public void testGetPhoneNumbersCoveredByStation_Success() throws Exception {
		
		phoneAlertController = new PhoneAlertController(phoneAlertService);
		mockMvc = MockMvcBuilders.standaloneSetup(phoneAlertController).build();
		
		List<String> children = Arrays.asList(
				new String("841-874-6512"),
				new String("841-874-8547")
		);
		
		PhoneAlertResponseDTO mockResponse = new PhoneAlertResponseDTO(children);
		
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
		
		when(phoneAlertService.getPhoneNumbersCoveredByStation("1")).thenReturn(mockResponse);
		
		mockMvc.perform(get("/phoneAlert")
				.param("firestation", "1"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
}
