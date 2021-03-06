package com.tlw8253.dto;

import java.util.Objects;

//DTO - Data Transfer Object
public class AddOrEditClientDTO {
	private String sFirstName = "";
	private String sLastName = "";
	private String sNickname = "";

	public AddOrEditClientDTO() {
		super();
	}
	
	public AddOrEditClientDTO(String sFirstName, String sLastName, String sNickname) {
		this.sFirstName = sFirstName;
		this.sLastName = sLastName;
		this.sNickname = sNickname;		
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

	public String getNickname() {
		return sNickname;
	}

	public void setNickname(String sNickname) {
		this.sNickname = sNickname;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(sFirstName, sLastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrEditClientDTO other = (AddOrEditClientDTO) obj;
		return Objects.equals(sFirstName, other.sFirstName) && Objects.equals(sLastName, other.sLastName);
	}
	@Override
	public String toString() {
		return "AddOrEditClientDTO [sFirstName=" + sFirstName + ", sLastName=" + sLastName + ", sNickname=" + sNickname
				+ "]";
	}

	
}
