package com.openclassrooms.safetynet.model;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a firestation entity with an associated address and station number.
 * This class is used to store and validate the details of a firestation.
 */
@Data
public class Firestation {

	@NotBlank(message = "Address may not be empty.")
	@Valid
	private String address;
	
	@NotBlank(message = "Station may not be empty.")
	@Valid
	private String station;
	
	public Firestation() {}
	
	public Firestation(String address, String station) {
		this.address = address;
		this.station = station;
	}
}