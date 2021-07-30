package com.tlw8253.dto;

//DTO - Data Transfer Object
public class ClientDTO {
	private String sFirstName = "";
	private String sLastName = "";

	public ClientDTO() {
		super();
	}

	public String getsFirstName() {
		return sFirstName;
	}

	public void setsFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}

	public String getsLastName() {
		return sLastName;
	}

	public void setsLastName(String sLastName) {
		this.sLastName = sLastName;
	}

	
}
