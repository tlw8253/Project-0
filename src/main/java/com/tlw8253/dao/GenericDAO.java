package com.tlw8253.dao;

import java.sql.SQLException;
import java.util.List;

import com.tlw8253.dto.AddDTO;
import com.tlw8253.dto.EditDTO;

//import com.tlw8253.model.Client;

/**
 * This is a concept of a Generic interface as allowed in Java 7.
 * A Java tutorial provided insight on how to implement such an interface:
 * 		https://docs.oracle.com/javase/tutorial/java/generics/types.html
 * Implementors of this interface will define their own type <T>.
 * They will need to implement all abstract methods returning type <T>
 * 
 * @author tlw87
 *
 * @param <T>
 */


//DAO - Data Access Object
public interface GenericDAO<T> {

	public abstract List<T> getAllRecords() throws SQLException;
	
	/**
	 * This method returns a Client from the database
	 * 
	 * @param iRecordId is an int that represents the primary key of the client in the database
	 * @return Client a representation of what a Client is, or null if none was found
	 */
	//public abstract T getByRecordId(int iRecordId) throws SQLException;
	//		using string so called method can convert to type needed
	public abstract T getByRecordIdentifer(String sRecordIdentifier) throws SQLException; 
	
	// Here we are making use of a parameter known as ClientDTO, which is a Data Transfer Object
	// This being a DTO is used to pass the Client model data around in the program.

	public abstract T addRecord(AddDTO objGenericAddDTO) throws SQLException;
	
	public abstract T editRecord(String sRecordIdentifier, EditDTO objGenericEditDTO) throws SQLException;
	
	public abstract void deleteRecord(String sRecordIdentifier) throws SQLException;

}
