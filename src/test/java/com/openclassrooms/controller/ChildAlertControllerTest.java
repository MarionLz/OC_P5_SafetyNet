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
import com.openclassrooms.safetynet.DTO.PersonIdentityDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildAlertResponseDTO;
import com.openclassrooms.safetynet.DTO.childAlert.ChildDTO;
import com.openclassrooms.safetynet.controller.ChildAlertController;
import com.openclassrooms.safetynet.service.ChildAlertService;


/**
 * Unit test for the {@link ChildAlertController} class.
 * This test verifies the behavior of the ChildAlertController's method
 * to retrieve children and other family members at a specific address.
 */
@ExtendWith(MockitoExtension.class)
public class ChildAlertControllerTest {
    
    @Mock
    private ChildAlertService childAlertService;
    
    @InjectMocks
    private ChildAlertController childAlertController;
    
    private MockMvc mockMvc;
    
    /**
     * Tests the {@link ChildAlertController#getChildAtAddress(String)} method.
     * This test simulates a GET request to the '/childAlert' endpoint and verifies
     * the response returned by the controller, including the HTTP status and content.
     * The test mocks the service layer's response and compares the expected response
     * with the actual output.
     *
     * @throws Exception if an error occurs while performing the request
     */
    @Test
    public void getChildAtAddress() throws Exception {
        
        childAlertController = new ChildAlertController(childAlertService);
        mockMvc = MockMvcBuilders.standaloneSetup(childAlertController).build();
        
        List<ChildDTO> children = Arrays.asList(
                new ChildDTO("Tenley", "Boyd", "02/18/2012"),
                new ChildDTO("Roger", "Boyd", "09/06/2017")
        );
        
        List<PersonIdentityDTO> otherFamilyMembers = Arrays.asList(
                new PersonIdentityDTO("John", "Boyd"),
                new PersonIdentityDTO("Jacob", "Boyd"),
                new PersonIdentityDTO("Felicia", "Boyd")
        );
        
        ChildAlertResponseDTO mockResponse = new ChildAlertResponseDTO(children, otherFamilyMembers);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(mockResponse);
        
        when(childAlertService.getChildrenAtAddress("1509 Culver St")).thenReturn(mockResponse);
        
        mockMvc.perform(get("/childAlert")
                .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}