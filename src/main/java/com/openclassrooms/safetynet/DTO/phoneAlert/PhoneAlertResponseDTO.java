package com.openclassrooms.safetynet.DTO.phoneAlert;

import java.util.List;

import lombok.Data;

@Data
public class PhoneAlertResponseDTO {
	
	private List<String> phoneNumber;

	public PhoneAlertResponseDTO() {}
	
	public PhoneAlertResponseDTO(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

