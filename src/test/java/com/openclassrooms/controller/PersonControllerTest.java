package com.openclassrooms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

/**
 * Unit tests for the {@link PersonController} class.
 * This test class verifies the functionality of the PersonController,
 * including methods to add, update, and delete person records.
 */
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

	@Mock
	private PersonService personService;
	
    @InjectMocks
    private PersonController personController;
    
    private MockMvc mockMvc;
	
    /**
     * Sets up the test environment by creating the controller and MockMvc instance 
     * before each test. This method is called before each test method.
     */
	@BeforeEach
	public void setUp() {
		personController = new PersonController(personService);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	}
	
    /**
     * Tests the {@link PersonController#addPerson(Person)} method to verify adding a person.
     * This method simulates a POST HTTP request to add a person and checks that the method
     * {@link PersonService#addPerson(Person)} is called once with the correct parameters.
     *
     * @throws Exception If an error occurs while executing the request.
     */
	@Test
	public void testAddPerson_Success() throws Exception {
		
		Person person = new Person("Molky", "Lechat", "1 rue des croquettes", "CatCity", "31000", "01010101", "eldorito@moustache.com");
								
		mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService, times(1)).addPerson(any(Person.class));
	}
	
    /**
     * Tests the {@link PersonController#updatePerson(Person)} method to verify updating a person.
     * This method simulates a PUT HTTP request to update a person and checks that the method
     * {@link PersonService#updatePerson(Person)} is called once with the correct parameters.
     *
     * @throws Exception If an error occurs while executing the request.
     */
	@Test
	public void testUpdatePerson_Success() throws Exception {
		
		Person person = new Person("Molky", "Lechat", "1 rue de la pâtée", "CatCity", "31000", "01010101", "eldorito@moustache.com");
								
		mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService, times(1)).updatePerson(any(Person.class));
	}
	
    /**
     * Tests the {@link PersonController#deletePerson(String, String)} method to verify deleting a person.
     * This method simulates a DELETE HTTP request to delete a person by their first name and last name,
     * and checks that the method {@link PersonService#deletePerson(String, String)} is called once with the correct parameters.
     *
     * @throws Exception If an error occurs while executing the request.
     */
	@Test
	public void testDeletePerson_Success() throws Exception {
										
		mockMvc.perform(delete("/person")
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().isOk());

        verify(personService, times(1)).deletePerson("John", "Doe");
	}
}
