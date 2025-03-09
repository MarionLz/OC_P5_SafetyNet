package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class Firestation {

	private String address;
	private String station;
	
	public Firestation() {}
	
	public Firestation(String address, String station) {
		this.address = address;
		this.station = station;
	}
}