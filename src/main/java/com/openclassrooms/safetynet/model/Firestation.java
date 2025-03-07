package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class Firestation {

	private String adress;
	private String station;
	
	public Firestation() {}
	
	public Firestation(String adress, String station) {
		this.adress = adress;
		this.station = station;
	}
}