package com.tlw8253.model;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Model of what it is to be a Client
public class Client {
	private Logger objLogger = LoggerFactory.getLogger(Client.class);
	
	private int iClientId = 0;
	private String sFirstName = "";
	private String sLastName = "";
	private String sNickname = "";

//	private List<Phone> listPhones;		TBD
	private List<Account> lstAccounts;
	
	
	public Client() {
		super();
	}

	public Client(int iRecordId, String sFirstName, String sLastName, String sNickname) {
		super();
		this.iClientId = iRecordId;
		this.sFirstName = sFirstName;
		this.sLastName = sLastName;
		this.sNickname = sNickname;		
	}

	public Client(String sFirstName, String sLastName, String sNickname) {
		super();
		this.sFirstName = sFirstName;
		this.sLastName = sLastName;
		this.sNickname = sNickname;		
	}

	//
	//### getters & setters
	public int getRecordId() {
		return iClientId;
	}

	public void setRecordId(int iRecordId) {
		this.iClientId = iRecordId;
	}

	public String getFirstName() {
		return sFirstName;
	}
	
	public void setFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}

	public String getLastName() {
		return sLastName;
	}

	public void setLastName(String sLastName) {
		this.sLastName = sLastName;
	}
	
	public String getNickname() {
		return sNickname;
	}

	public void setNickname(String sNickname) {
		this.sNickname = sNickname;
	}

	public List<Account> getAccounts(){
		return this.lstAccounts;
	}
	public void setAccounts(List<Account> lstAccounts) {
		this.lstAccounts =lstAccounts;
	}
	
		
	@Override
	public int hashCode() {
		return Objects.hash(iClientId, sFirstName, sLastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return iClientId == other.iClientId && Objects.equals(sFirstName, other.sFirstName)
				&& Objects.equals(sLastName, other.sLastName);
	}

	@Override
	public String toString() {
		String sMethod = "toString(): ";
		String sToString = "Client Id: [" + iClientId +"] First Name: [" 
							+ sFirstName + "] Last Name; [" + sLastName + "]"
							+ " Nickname: [" + sNickname + "]";
		objLogger.debug(sMethod + sToString);
		return (sToString);
	}

	
	
}
