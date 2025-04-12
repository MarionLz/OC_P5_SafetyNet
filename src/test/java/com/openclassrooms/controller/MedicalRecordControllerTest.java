package com.openclassrooms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.MedicalRecordController;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;


/**
 * Unit tests for the {@link MedicalRecordController} class.
 * This test class verifies the functionalities of the MedicalRecordController,
 * such as adding, updating, and deleting medical records.
 */
@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

	@Mock
	private MedicalRecordService medicalRecordService;
	
    @InjectMocks
    private MedicalRecordController medicalRecordController;
    
    private MockMvc mockMvc;
	
    /**
     * Sets up the test environment, initializing the controller and MockMvc.
     * This method is called before each test method is executed.
     */	
    @BeforeEach
	void setUp() {
    	
		medicalRecordController = new MedicalRecordController(medicalRecordService);
		mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordController).build();
    }
    
    /**
     * Tests the {@link MedicalRecordController#addMedicalRecord(MedicalRecord)} method.
     * This test simulates a POST request to the '/medicalRecord' endpoint to add
     * a new medical record and verifies the response and interactions with the service.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testAddMedicalRecord_Success() throws Exception {
		
		MedicalRecord medicalRecord = new MedicalRecord("Clive", "Ferguson", "03/06/1994", new String[]{}, new String[]{});
								
		mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).addMedicalRecord(any(MedicalRecord.class));
	}
	
    /**
     * Tests the {@link MedicalRecordController#updateMedicalRecord(MedicalRecord)} method.
     * This test simulates a PUT request to the '/medicalRecord' endpoint to update
     * an existing medical record and verifies the response and interactions with the service.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testUpdateMedicalRecord_Success() throws Exception {
		
		MedicalRecord medicalRecord = new MedicalRecord("Clive", "Ferguson", "03/06/1994", new String[]{}, new String[]{"peanut"});
								
		mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).updateMedicalRecord(any(MedicalRecord.class));
	}
	
	 /**
     * Tests the {@link MedicalRecordController#deleteMedicalRecord(String, String)} method.
     * This test simulates a DELETE request to the '/medicalRecord' endpoint to delete
     * an existing medical record and verifies the response and interactions with the service.
     *
     * @throws Exception if an error occurs while performing the request
     */
	@Test
	public void testDeleteMedicalRecord_Success() throws Exception {
										
		mockMvc.perform(delete("/medicalRecord")
                .param("firstName", "Clive")
                .param("lastName", "Ferguson"))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).deleteMedicalRecord("Clive", "Ferguson");
	}
}
