package com.tlw8253.dto;

import com.tlw8253.application.Constants;

public class AccountAddDTO implements Constants {
	GenericAddDTO objAddDTO;

	public AccountAddDTO() {
		super();
		objAddDTO = new GenericAddDTO();
		
	}
	
	public void setAccountNumber(String sAccountNumber) {
		objAddDTO.setDataElement(csAccountTblAccountNumber, sAccountNumber);		
	}
	public String getAccountNumber() {
		return objAddDTO.getDataElement(csAccountTblAccountNumber);		
	}

	public void setAccountName(String sAccountName) {
		objAddDTO.setDataElement(csAccountTblAccountName, sAccountName);		
	}
	public String getAccountName() {
		return objAddDTO.getDataElement(csAccountTblAccountName);		
	}

	public void setAccountBalance(String sAccountBalance) {
		objAddDTO.setDataElement(csAccountTblAccountBalance, sAccountBalance);		
	}
	public String getAccountBalance() {
		return objAddDTO.getDataElement(csAccountTblAccountBalance);		
	}
	
	@Override
	public String toString() {
		return objAddDTO.toString();
	}
	
}
