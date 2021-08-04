package com.tlw8253.dto;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//DTO - Data Transfer Object
public class AddDTO {
	private Logger objLogger = LoggerFactory.getLogger(AddDTO.class);
	
	HashMap<String, String> hmDataElements; //expected format: <databaseTable.column>,<value> 
	
	public AddDTO() {
		super();
		hmDataElements = new HashMap<String, String>();
	}
	
	public AddDTO(HashMap<String, String> hmDataElements) {
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
		AddDTO other = (AddDTO) obj;
		return Objects.equals(hmDataElements, other.hmDataElements);
	}

	@Override
	public String toString() {
		String sMethod = "toString(): ";
		TreeSet<String> treeSetSortedKeys = getSortedKeys();
		String sToString = "";
		
		for (String sKey : treeSetSortedKeys) {
			sToString += "[" + sKey + "]: [" + hmDataElements.get(sKey) + "]";
		}
		objLogger.debug(sMethod + "sToString: [" + sToString + "]");
		
		return sToString;
	}
	
	//
	//### create retrun string in specific order of keys passed in
	public String toStringByKeys(String... sKey) {//Varargs parameter list
		String sMethod = "toStringByKeys(): ";
		String sToString = "";
		
		for (int iCtr=0; iCtr<sKey.length; iCtr++) {
			String sThisKey = sKey[iCtr];
			objLogger.debug(sMethod + "sKey[" + sKey + "]: [" + sKey[iCtr] + "]" );
			
			sToString += "[" + sThisKey + "]: [" + hmDataElements.get(sThisKey) + "] ";			
		}
		objLogger.debug(sMethod + "sToString: [" + sToString + "]");		
		
		return sToString;
	}
	
	//Should move to utility class
	//###
	private TreeSet<String> getSortedKeys(){
		String sMethod = "getSortedKeys(): ";		

		Set<String> setKeys = hmDataElements.keySet();
		TreeSet<String> treeSetKeys = new TreeSet<String>(setKeys);
		
		objLogger.debug(sMethod + "treeSetKeys: [" + treeSetKeys.toString() + "]");
		
		return treeSetKeys;
	}

	
	
}
