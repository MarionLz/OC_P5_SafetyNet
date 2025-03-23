package com.openclassrooms.safetynet.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Person {

	@NotBlank(message = "FisrtName may not be empty.")
	@Valid
    private String firstName;
	
	@NotBlank(message = "LastName may not be empty.")
	@Valid
    private String lastName;
	
	@NotBlank(message = "Address may not be empty.")
	@Valid
    private String address;
	
	@NotBlank(message = "City may not be empty.")
	@Valid
    private String city;
	
	@NotBlank(message = "Zip may not be empty.")
	@Valid
    private String zip;
	
	@NotBlank(message = "Phone may not be empty.")
    private String phone;
	
	@NotBlank(message = "Email may not be empty.")
	@Valid
    private String email;

    public Person() {}

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}
