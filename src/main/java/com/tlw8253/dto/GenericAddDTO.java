package com.tlw8253.dto;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//DTO - Data Transfer Object
public class GenericAddDTO {
	private Logger objLogger = LoggerFactory.getLogger(GenericAddDTO.class);
	
	HashMap<String, String> hmDataElements; //expected format: <databaseTable.column>,<value> 
	
	/*
	private String sFirstName = "";
	private String sLastName = "";
	private String sNickname = "";
	*/

	public GenericAddDTO() {
		super();
		hmDataElements = new HashMap<String, String>();
	}
	
	public GenericAddDTO(HashMap<String, String> hmDataElements) {
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
		GenericAddDTO other = (GenericAddDTO) obj;
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
	
	//Should move to utility class
	//###
	private TreeSet<String> getSortedKeys(){
		String sMethod = "getSortedKeys(): ";		

		Set<String> setKeys = hmDataElements.keySet();
		TreeSet<String> treeSetKeys = new TreeSet<String>(setKeys);
		
		objLogger.debug(sMethod + "treeSetKeys: [" + treeSetKeys.toString() + "]");
		
		return treeSetKeys;
	}

	
	/*	
	//
	//###
	public TreeSet<String> getSortedNames(){
		Set<String> objKeys = objBarrierMap.keySet();
		TreeSet<String> objSortedKeys = new TreeSet<String>(objKeys);
		return(objSortedKeys);
	}
	
	//
	//###
	public void printBarriers() {
		TreeSet<String> objSortedNames = getSortedNames();
		for(String sName : objSortedNames)
			System.out.println(sName + ": [" + objBarrierMap.get(sName).toString() + "]");		
	}
	
*/
	
	
}
