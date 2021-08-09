package com.tlw8253.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Constants;

public class AccountEditDTO extends AddDTO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AccountEditDTO.class);

	
	public AccountEditDTO() {
		super();
	}
	

	//currently only account balance can be updated
	public AccountEditDTO(String sAccountNumber, String sAccountBalance, String sClientId) {
		super();
		setAccountNumber(sAccountNumber);
		setAccountBalance(sAccountBalance);
		setClientId(sClientId);
	}


	//Account number is stored as a string in the database.
	//	Only provide a String setter
	public void setAccountNumber(String sAccountNumber) {
		super.setDataElement(csAccountTblAccountNumber, sAccountNumber);		
	}
	public String getAccountNumber() {
		return super.getDataElement(csAccountTblAccountNumber);		
	}

	//Account type is stored as a string in the database.
	//	Only provide a String setter
	public void setAccountType(String sAccountType) {
		sAccountType = sAccountType.toUpperCase();
		super.setDataElement(csAccountTblAccountType, sAccountType);		
	}
	public String getAccountType() {
		return super.getDataElement(csAccountTblAccountType);		
	}

	//Account balance is a double and treated as such.
	//	Provide a double setter and getter
	public void setAccountBalance(String sAccountBalance) {
		super.setDataElement(csAccountTblAccountBalance, sAccountBalance);		
	}
	public void setAccountBalance(double dAccountBalance) {
		super.setDataElement(csAccountTblAccountBalance, dAccountBalance);		
	}

	public String getAccountBalance() {
		return super.getDataElement(csAccountTblAccountBalance);		
	}
	public double getAccountBalanceAsDouble() {
		return super.getDoubleDataElement(csAccountTblAccountBalance);		
	}
	
	//Client Id is an int and treated as such.
	//	Provide an int setter and getter
	public void setClientId(String sClientId) {
		super.setDataElement(csClientTblClientId, sClientId);
	}
	public void setClientId(int iClientId) {
		super.setDataElement(csClientTblClientId, iClientId);
	}

	
	public String getClientId() {
		return super.getDataElement(csClientTblClientId);
	}
	public int getClientIdAsInt() {
		return super.getIntDataElement(csClientTblClientId);
	}

	
	@Override
	public String toString() {
		String sMethod = "toString(): ";
		String sToString = "";
		
		sToString = super.toStringByKeys(csAccountTblAccountNumber, csAccountTblAccountType, csAccountTblAccountBalance, csClientTblClientId);
		objLogger.debug(sMethod + "sToString: [" + sToString + "]");
		
		return sToString;
		//return super.toString();
	}

}
