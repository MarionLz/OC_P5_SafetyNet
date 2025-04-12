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
import com.openclassrooms.safetynet.controller.CommunityEmailController;
import com.openclassrooms.safetynet.service.CommunityEmailService;

/**
 * Unit test for the {@link CommunityEmailController} class.
 * This test verifies the behavior of the CommunityEmailController's method
 * to retrieve community email addresses for a specific city.
 */
@ExtendWith(MockitoExtension.class)
public class CommunityEmailControllerTest {
	
	@Mock
	private CommunityEmailService communityEmailService;
	
    @InjectMocks
    private CommunityEmailController communityEmailController;
    
    private MockMvc mockMvc;

    /**
     * Tests the {@link CommunityEmailController#getCommunityEmails(String)} method.
     * This test simulates a GET request to the '/communityEmail' endpoint and verifies
     * the response returned by the controller, including the HTTP status and content.
     * The test mocks the service layer's response and compares the expected response
     * with the actual output.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void getChildAtAddress() throws Exception {
		
		communityEmailController = new CommunityEmailController(communityEmailService);
		mockMvc = MockMvcBuilders.standaloneSetup(communityEmailController).build();
		
		List<String> emails = Arrays.asList(
				new String("01@email.com"),
				new String("02@email.com"),
				new String("03@email.com")
		);
		
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(emails);
		
		when(communityEmailService.getCommunityEmails("Culver")).thenReturn(emails);
		
		mockMvc.perform(get("/communityEmail")
				.param("city", "Culver"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
}
