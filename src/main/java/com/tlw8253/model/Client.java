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

//	private List<Phone> listPhones;		TBD
//	private List<Account> listAccounts;	to be implemented
	
	
	public Client() {
		super();
	}

	public Client(int iRecordId, String sFirstName, String sLastName) {
		super();
		this.iClientId = iRecordId;
		this.sFirstName = sFirstName;
		this.sLastName = sLastName;
	}

	//
	//### getters & setters
	public int getiRecordId() {
		return iClientId;
	}

	public void setiRecordId(int iRecordId) {
		this.iClientId = iRecordId;
	}

	public String getsFirstName() {
		return sFirstName;
	}

	public void setsFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}

	public String getsLastName() {
		return sLastName;
	}

	public void setsLastName(String sLastName) {
		this.sLastName = sLastName;
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
		String sToString = "iClientId: [" + iClientId +"] sFirstName: [" + sFirstName + "] sLastName; [" + sLastName + "]";
		objLogger.debug(sMethod + sToString);
		return (sToString);
	}

	
	
}
