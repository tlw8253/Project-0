package com.tlw8253.util;

public final class Utility {

	private Utility() {
		super();
	}
	
	public static int getRandomIntBetween(int iMin, int iMax) {
		return (int) ((Math.random() * (iMax - iMin)) + iMin);
	}

	public static String padIntLeadingZero(int iInt, int iStringLen) {
		String sRetString = Integer.toString(iInt);
		int iPadLen = iStringLen - sRetString.length();
		
		
		for(int iCtr=0; iCtr<iPadLen; iCtr++) {
			sRetString = "0" + sRetString;
		}		
		return sRetString;
	}
}
