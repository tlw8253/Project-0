package com.tlw8253.model;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Model of what it is to be a Client
public class Client {
	private Logger objLogger = LoggerFactory.getLogger(Client.class);
	
	private int iRecordId = 0;
	private String sFirstName = "";
	private String sLastName = "";

//	private List<Phone> listPhones;
	
	
	public Client() {
		super();
	}

	public Client(int iRecordId, String sFirstName, String sLastName) {
		super();
		this.iRecordId = iRecordId;
		this.sFirstName = sFirstName;
		this.sLastName = sLastName;
	}

	//
	//### getters & setters
	public int getiRecordId() {
		return iRecordId;
	}

	public void setiRecordId(int iRecordId) {
		this.iRecordId = iRecordId;
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
		return Objects.hash(iRecordId, sFirstName, sLastName);
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
		return iRecordId == other.iRecordId && Objects.equals(sFirstName, other.sFirstName)
				&& Objects.equals(sLastName, other.sLastName);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	
	
}
