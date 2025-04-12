package com.openclassrooms.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.exceptions.ResourceAlreadyExistsException;
import com.openclassrooms.safetynet.exceptions.ResourceNotFoundException;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.IDataWriterRepository;
import com.openclassrooms.safetynet.service.DataModelService;
import com.openclassrooms.safetynet.service.MedicalRecordService;

/**
 * Unit test class for {@link MedicalRecordService}.
 * This class contains tests for the medical record-related functionalities, such as
 * adding, updating, and deleting medical records, as well as handling duplicate and missing records.
 */
@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

	@Mock
	private IDataWriterRepository writerRepository;
	
	@Mock
	private DataModelService dataModelService;
	
	private MedicalRecordService medicalRecordService;
	private List<MedicalRecord> medicalRecords;
	private DataModel dataModel;
	
    /**
     * Sets up the test environment by initializing the service and mock data
     * before each test method is executed.
     */
	@BeforeEach
	void setUp() {
		medicalRecordService = new MedicalRecordService(writerRepository, dataModelService);
		medicalRecords = new ArrayList<>(Arrays.asList(
				new MedicalRecord("Clive", "Ferguson", "03/06/1994", new String[]{}, new String[]{}),
			    new MedicalRecord("Tony", "Cooper", "03/06/1994", new String[] {"hydrapermazol:300mg", "dodoxadin:30mg"}, new String[]{"shellfish"})
		));
		dataModel = new DataModel();
		dataModel.setMedicalrecords(medicalRecords);
		
        when(dataModelService.getDataModel()).thenReturn(dataModel);
	}
	
    /**
     * Tests the successful addition of a new medical record.
     * Verifies that the medical record is added to the data model and saved via the writer repository.
     */
	@Test
	public void testAddMedicalRecord_Success() {
		
		MedicalRecord newMedicalRecord = new MedicalRecord("Reginold", "Walker", "08/30/1979", new String[]{"thradox:700mg"}, new String[]{"illisoxian"});

		medicalRecordService.addMedicalRecord(newMedicalRecord);
		
		assertTrue(dataModel.getMedicalrecords().contains(newMedicalRecord));
		verify(writerRepository, times(1)).saveData();
	}
	
    /**
     * Tests adding a medical record that already exists in the system.
     * Verifies that the appropriate exception is thrown when trying to add a duplicate medical record.
     */
    @Test
    public void testAddMedicalRecord_WhenMedicalRecordAlreadyExists_ShouldThrowException() {
    	
    	MedicalRecord duplicate = new MedicalRecord("Clive", "Ferguson", "03/06/1994", new String[]{}, new String[]{});

    	ResourceAlreadyExistsException exception = assertThrows(
    		   ResourceAlreadyExistsException.class,
            () -> medicalRecordService.addMedicalRecord(duplicate)
        );

        assertTrue(exception.getMessage().contains("MedicalRecord already exists"));
        verify(writerRepository, never()).saveData();
    }
	
    /**
     * Tests the successful update of an existing medical record.
     * Verifies that the record is updated in the data model and saved.
     */
	@Test
	public void testUpdateMedicalRecord_Success() {
		
		MedicalRecord updatedMedicalRecord = new MedicalRecord("Clive", "Ferguson", "03/06/1994", new String[]{}, new String[]{"peanuts"});
    	
		medicalRecordService.updateMedicalRecord(updatedMedicalRecord);
    	
        assertArrayEquals(new String[]{"peanuts"}, dataModel.getMedicalrecords().get(0).getAllergies());
		verify(writerRepository, times(1)).saveData();
	}
	
    /**
     * Tests the update of a non-existent medical record.
     * Verifies that the appropriate exception is thrown when trying to update a record that does not exist.
     */
	@Test
    public void testUpdateMedicalRecord_WhenMedicalRecordNotFound_ShouldThrowException() {

		MedicalRecord nonExistentMedicalRecord = new MedicalRecord("Unknown", "Anonyme", "07/04/1995", new String[]{}, new String[]{});

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
            () -> medicalRecordService.updateMedicalRecord(nonExistentMedicalRecord)
        );

        assertTrue(exception.getMessage().contains("MedicalRecord not found"));
        verify(writerRepository, never()).saveData();
    }
	
    /**
     * Tests the successful deletion of a medical record.
     * Verifies that the record is removed from the data model and saved.
     */
	@Test
    public void testDeleteMedicalRecord_Success() {

		medicalRecordService.deleteMedicalRecord("Clive", "Ferguson");

        assertEquals(1, dataModel.getMedicalrecords().size());
        assertEquals("Tony", dataModel.getMedicalrecords().get(0).getFirstName());
        verify(writerRepository).saveData();
    }
	
    /**
     * Tests the deletion of a non-existent medical record.
     * Verifies that the appropriate exception is thrown when trying to delete a record that does not exist.
     */
	@Test
	public void testDeleteMedicalRecord_WhenMedicalRecordNotFound_ShouldThrowException() {
		
		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
            () -> medicalRecordService.deleteMedicalRecord("Unknown", "Anonyme")
        );

        assertTrue(exception.getMessage().contains("MedicalRecord not found"));
        verify(writerRepository, never()).saveData();
	}
}
