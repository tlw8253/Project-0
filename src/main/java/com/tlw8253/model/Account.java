package com.tlw8253.model;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {
	private Logger objLogger = LoggerFactory.getLogger(Account.class);
	
	private String sAccountNumber = ""; //account number is the unique record id
	private String sAccountName = "";
	private double dAccountBalance = 0.0;
	
	public Account() {
		super();
	}

	public Account(String sAccountName, String sAccountNumber, double dAccountBalance) {
		super();
		
		this.sAccountName = sAccountName;
		this.sAccountNumber = sAccountNumber;
		this.dAccountBalance = dAccountBalance;
	}

	public String getAccountName() {
		return sAccountName;
	}

	public void setAccountName(String sAccountName) {
		this.sAccountName = sAccountName;
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

	@Override
	public int hashCode() {
		return Objects.hash(dAccountBalance, sAccountName, sAccountNumber);
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
				&& Objects.equals(sAccountName, other.sAccountName)
				&& Objects.equals(sAccountNumber, other.sAccountNumber);
	}

	@Override
	public String toString() {
		String sMethod = "toString(): ";
		
		String sToString = "Account [sAccountNumber=" + sAccountNumber + ", sAccountName=" 
							+ sAccountName + ", dAccountBalance=" + dAccountBalance + "]";
		objLogger.debug(sMethod + "[" + sToString + "]");
		
		return(sToString);
	}

	
	
	
}
