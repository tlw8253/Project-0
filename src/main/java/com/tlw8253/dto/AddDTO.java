package com.tlw8253.dto;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.lang.Integer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//DTO - Data Transfer Object
public class AddDTO {
	private Logger objLogger = LoggerFactory.getLogger(AddDTO.class);
	
	//every data element should be in the hmStringDataElements for up front validation
	//then stored in its destination format after validation
	HashMap<String, String> hmStringDataElements; //expected format: <databaseTable.column>,<string value> 
	HashMap<String, Integer> hmIntegerDataElements; //expected format: <databaseTable.column>,<integer value>
	HashMap<String, Double> hmDoubleDataElements; //expected format: <databaseTable.column>,<double value>
	
	
	public AddDTO() {
		super();
		hmStringDataElements = new HashMap<String, String>();
		hmIntegerDataElements = new HashMap<String, Integer>();
		hmDoubleDataElements = new HashMap<String, Double>();
	}
	
	
	public AddDTO(HashMap<String, String> hmDataElements) {
		super();
		this.hmStringDataElements = hmDataElements;
	}

	
	
	
	public String getDataElement(String sElementName) {
		return (hmStringDataElements.get(sElementName));
	}
	public double getDoubleDataElement(String sElementName) {
		return (hmDoubleDataElements.get(sElementName).doubleValue());
	}
	public int getIntDataElement(String sElementName) {
		return (hmIntegerDataElements.get(sElementName).intValue());
	}

	
	
	
	public void setDataElement(String sElementName, String sElementValue) {
		hmStringDataElements.put(sElementName, sElementValue);
	}
	public void setDataElement(String sElementName, int iElementValue) {
		hmIntegerDataElements.put(sElementName, Integer.valueOf(iElementValue));
	}
	public void setDataElement(String sElementName, double dElementValue) {
		hmDoubleDataElements.put(sElementName, Double.valueOf(dElementValue));
	}

	
	
	//
	//### create return string in specific order of keys passed in
	//		Only use the String data elements, since all other types should 
	//		have a string counterpart.
	public String toStringByKeys(String... sKey) {//Varargs parameter list
		String sMethod = "toStringByKeys(): ";
		String sToString = "";
		
		for (int iCtr=0; iCtr<sKey.length; iCtr++) {
			String sThisKey = sKey[iCtr];
			objLogger.debug(sMethod + "sKey[" + sKey + "]: [" + sKey[iCtr] + "]" );
			
			sToString += "[" + sThisKey + "]: [" + hmStringDataElements.get(sThisKey) + "] ";			
		}
		objLogger.debug(sMethod + "sToString: [" + sToString + "]");		
		
		return sToString;
	}
	
	//Should move to utility class
	//###
	private TreeSet<String> getSortedKeys(){
		String sMethod = "getSortedKeys(): ";		

		Set<String> setKeys = hmStringDataElements.keySet();
		TreeSet<String> treeSetKeys = new TreeSet<String>(setKeys);
		
		objLogger.debug(sMethod + "treeSetKeys: [" + treeSetKeys.toString() + "]");
		
		return treeSetKeys;
	}

	
	
}
