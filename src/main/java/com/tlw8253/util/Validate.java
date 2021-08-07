package com.tlw8253.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Validate {
	private static Logger objLogger = LoggerFactory.getLogger(Validate.class);

	private Validate() {
		super();
	}

	//
	//### Utility method to check if string is an primitive int
	public static boolean isInt(String sValue) {
		String sMethod = "isInt";
		boolean bIsValid = false;
		try {
			objLogger.debug(sMethod + "Checking if string value of: [" + sValue + "] is an integer.");
			Integer.parseInt(sValue);
			bIsValid = true;

		} catch (NumberFormatException objE) {
			objLogger.debug(sMethod + "String value of: [" + sValue + "] is NOT an integer.");		
			}
		return bIsValid;
	}
	
	//
	//### Utility method to check if string is an primitive int
	public static boolean isDouble(String sValue) {
		String sMethod = "isDouble";
		boolean bIsValid = false;
		try {
			objLogger.debug(sMethod + "Checking if string value of: [" + sValue + "] is a double.");
			Double.parseDouble(sValue);
			bIsValid = true;

		} catch (NumberFormatException objE) {
			objLogger.debug(sMethod + "String value of: [" + sValue + "] is NOT a double.");		
			}
		return bIsValid;
	}
}
