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

@ExtendWith(MockitoExtension.class)
public class CommunityEmailControllerTest {
	
	@Mock
	private CommunityEmailService communityEmailService;
	
    @InjectMocks
    private CommunityEmailController communityEmailController;
    
    private MockMvc mockMvc;
	
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
