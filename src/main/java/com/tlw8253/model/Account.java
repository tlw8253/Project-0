package com.tlw8253.model;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {
	private Logger objLogger = LoggerFactory.getLogger(Account.class);
	
	private String sAccountNumber = ""; //account number is the unique record id
	private String sAccountType = "";
	private double dAccountBalance = 0.0;
	private int iClientId = 0;
	
	public Account() {
		super();
	}

	public Account(String sAccountNumber, String sAccountType) {
		super();
		
		this.sAccountNumber = sAccountNumber;
		this.sAccountType = sAccountType;
	}

	public Account(String sAccountNumber, String sAccountType, double dAccountBalance) {
		super();
		
		this.sAccountNumber = sAccountNumber;
		this.sAccountType = sAccountType;
		this.dAccountBalance = dAccountBalance;
	}

	public Account(String sAccountNumber, String sAccountType, double dAccountBalance, int iClientId) {
		super();
		
		this.sAccountNumber = sAccountNumber;
		this.sAccountType = sAccountType;
		this.dAccountBalance = dAccountBalance;
		this.iClientId = iClientId;
	}

	public String getAccountType() {
		return sAccountType;
	}

	public void setAccountType(String sAccountName) {
		this.sAccountType = sAccountName;
	}

	public String getAccountNumber() {
		return sAccountNumber;
	}

	public void setAccountNumber(String sAccountNumber) {
		this.sAccountNumber = sAccountNumber;
	}

	public double getAccountBalance() {
		return dAccountBalance;
	}

	public void setAccountBalance(double dAccountBalance) {
		this.dAccountBalance = dAccountBalance;
	}
	
	public void setClientId(int iClientId) {
		this.iClientId = iClientId;
	}
	public int getClientId() {
		return iClientId;
	}


	
	
	@Override
	public int hashCode() {
		return Objects.hash(dAccountBalance, iClientId, sAccountType, sAccountNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Double.doubleToLongBits(dAccountBalance) == Double.doubleToLongBits(other.dAccountBalance)
				&& iClientId == other.iClientId && Objects.equals(sAccountType, other.sAccountType)
				&& Objects.equals(sAccountNumber, other.sAccountNumber);
	}

	@Override
	public String toString() {
		String sMethod = "toString(): ";
		
		String sToString = "Account [sAccountNumber= [" + sAccountNumber + "], sAccountType= [" 
							+ sAccountType + "], dAccountBalance= [" + dAccountBalance
							+ "] iClientId= [" + iClientId  + "]]";
		objLogger.debug(sMethod + "[" + sToString + "]");
		
		return(sToString);
	}

	
	
	
}
