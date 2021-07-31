package com.tlw8253.dto;

//DTO - Data Transfer Object
public class AddOrEditClientDTO {
	private String sFirstName = "";
	private String sLastName = "";

	public AddOrEditClientDTO() {
		super();
	}

	public String getFirstName() {
		return sFirstName;
	}

	public void setFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}

	public String getLastName() {
		return sLastName;
	}

	public void setLastName(String sLastName) {
		this.sLastName = sLastName;
	}

	
}
