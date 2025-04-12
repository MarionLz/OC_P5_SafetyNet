package com.openclassrooms.safetynet.DTO.firestation;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing a person associated with a fire station.
 * This class holds information about the person's first name, last name, address, and phone number.
 */
@Data
public class PersonsByStationsDTO {
	
	private String firstName;
	private String lastName;
	private String address;
    private String phone;

    public PersonsByStationsDTO() {}

    public PersonsByStationsDTO(String firstName, String lastName, String address, String phone ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
}
