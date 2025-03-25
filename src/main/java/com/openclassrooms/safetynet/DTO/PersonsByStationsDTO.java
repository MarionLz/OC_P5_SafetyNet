package com.openclassrooms.safetynet.DTO;

import lombok.Data;

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
