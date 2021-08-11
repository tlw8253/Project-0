package com.tlw8253.dto;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Constants;

public class AccountAddDTO extends AddDTO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AccountAddDTO.class);

	public AccountAddDTO() {
		super();
	}

	public AccountAddDTO(String sAccountNumber, String sAccountType, String sAccountBalance, String sClientId) {
		super();
		setAccountNumber(sAccountNumber);
		setAccountType(sAccountType);
		setAccountBalance(sAccountBalance);
		setClientId(sClientId);
	}

	public AccountAddDTO(String sAccountType, String sAccountBalance, String sClientId) {
		super();
		setAccountType(sAccountType);
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
	//I would prefer to overload the set method however the objCtx.bodyAsClass(AccountAddDTO.class);
	//  is using the set using double parameter instead of string like I thought it would
	public void setAccountBalanceAsDouble(double dAccountBalance) {
		super.setDataElement(csAccountTblAccountBalance, dAccountBalance);		
		
		System.out.println("setAccountBalance(double): [" + dAccountBalance + "]");
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
	
	//I would prefer to overload the set method however the objCtx.bodyAsClass(AccountAddDTO.class);
	//  is using the set using int parameter instead of string like I thought it would
	public void setClientIdAsInt(int iClientId) {
		super.setDataElement(csClientTblClientId, iClientId);
	}

	
	public String getClientId() {
		return super.getDataElement(csClientTblClientId);
	}
	public int getClientIdAsInt() {
		return super.getIntDataElement(csClientTblClientId);
	}

	
	
	//
	public String toString() {
		String sMethod = "toString(): ";
		String sToString = "";
		
		sToString = super.toStringByKeys(csAccountTblAccountNumber, csAccountTblAccountType, csAccountTblAccountBalance, csClientTblClientId);
		objLogger.debug(sMethod + "sToString: [" + sToString + "]");
		
		return sToString;
		//return super.toString();
	}


	
	
}
