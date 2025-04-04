package com.openclassrooms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


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

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

	@Mock
	private PersonService personService;
	
    @InjectMocks
    private PersonController personController;
    
    private MockMvc mockMvc;
	
	@Test
	public void testAddPerson_Success() throws Exception {
		
		personController = new PersonController(personService);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
		
		Person person = new Person("Molky", "Lechat", "1 rue des croquettes", "CatCity", "31000", "01010101", "eldorito@moustache.com");
								
		mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService, times(1)).addPerson(any(Person.class));
	}
	
	@Test
	public void testUpdatePerson_Success() throws Exception {
		
		personController = new PersonController(personService);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
		
		Person person = new Person("Molky", "Lechat", "1 rue de la pâtée", "CatCity", "31000", "01010101", "eldorito@moustache.com");
								
		mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService, times(1)).updatePerson(any(Person.class));
	}
	
	@Test
	public void testDeletePerson_Success() throws Exception {
		
		personController = new PersonController(personService);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
										
		mockMvc.perform(delete("/person")
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().isOk());

        verify(personService, times(1)).deletePerson("John", "Doe");
	}
}
