package com.openclassrooms.safetynet.DTO.phoneAlert;

import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the response for phone alerts, containing 
 * a list of phone numbers to be alerted.
 */
@Data
public class PhoneAlertResponseDTO {
	
	private List<String> phoneNumber;

	public PhoneAlertResponseDTO(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

