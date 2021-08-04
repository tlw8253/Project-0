package com.tlw8253.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Constants;

public class AccountAddDTO extends AddDTO implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AccountAddDTO.class);

	
	public AccountAddDTO() {
		super();
	}

	public void setAccountNumber(String sAccountNumber) {
		super.setDataElement(csAccountTblAccountNumber, sAccountNumber);		
	}
	public String getAccountNumber() {
		return super.getDataElement(csAccountTblAccountNumber);		
	}

	public void setAccountName(String sAccountName) {
		super.setDataElement(csAccountTblAccountName, sAccountName);		
	}
	public String getAccountName() {
		return super.getDataElement(csAccountTblAccountName);		
	}

	public void setAccountBalance(String sAccountBalance) {
		super.setDataElement(csAccountTblAccountBalance, sAccountBalance);		
	}
	public String getAccountBalance() {
		return super.getDataElement(csAccountTblAccountBalance);		
	}
	
	@Override
	public String toString() {
		String sMethod = "toString(): ";
		String sToString = "";
		
		sToString = super.toStringByKeys(csAccountTblAccountNumber, csAccountTblAccountName, csAccountTblAccountBalance, csClientTblClientId);
		objLogger.debug(sMethod + "sToString: [" + sToString + "]");
		
		return sToString;
		//return super.toString();
	}

}
