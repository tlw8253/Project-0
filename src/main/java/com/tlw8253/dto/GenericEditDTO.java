package com.tlw8253.dto;

import java.util.HashMap;
import java.util.Objects;

//DTO - Data Transfer Object
public class GenericEditDTO {
	
	HashMap<String, String> hmDataElements; //expected format: <databaseTable.column>,<value> 
	
	/*
	private String sFirstName = "";
	private String sLastName = "";
	private String sNickname = "";
	*/

	public GenericEditDTO(HashMap<String, String> hmDataElements) {
		super();
		this.hmDataElements = hmDataElements;
	}

	public String getDataElement(String sElementName) {
		return (hmDataElements.get(sElementName));
	}

	public void setDataElement(String sElementName, String sElementValue) {
		hmDataElements.put(sElementName, sElementValue);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(hmDataElements);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericEditDTO other = (GenericEditDTO) obj;
		return Objects.equals(hmDataElements, other.hmDataElements);
	}

	
}
