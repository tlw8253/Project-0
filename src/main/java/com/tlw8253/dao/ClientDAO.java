package com.tlw8253.dao;

import java.sql.SQLException;
import java.util.List;

import com.tlw8253.dto.AddOrEditClientDTO;
//import com.revature.dto.AddOrEditShipDTO;
import com.tlw8253.model.Client;

//DAO - Data Access Object
public interface ClientDAO {

	public abstract List<Client> getAllClients() throws SQLException;
	
	/**
	 * This method returns a Client from the database
	 * 
	 * @param iRecordId is an int that represents the primary key of the client in the database
	 * @return Client a representation of what a Client is, or null if none was found
	 */
	public abstract Client getClientById(int iRecordId) throws SQLException;
	
	// Here we are making use of a parameter known as ClientDTO, which is a Data Transfer Object
	// This being a DTO is used to pass the Client model data around in the program.

	public abstract Client addClient(AddOrEditClientDTO objClient) throws SQLException;
	
	public abstract Client editClient(int iClientId, AddOrEditClientDTO objClient) throws SQLException;
	
	public abstract void deleteClient(int iClientId) throws SQLException;

}
