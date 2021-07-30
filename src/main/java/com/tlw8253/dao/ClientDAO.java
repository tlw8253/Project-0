package com.tlw8253.dao;

import java.sql.SQLException;
import java.util.List;

import com.tlw8253.dto.ClientDTO;
//import com.revature.dto.AddOrEditShipDTO;
import com.tlw8253.model.Client;

//DAO - Data Access Object
public interface ClientDAO {

	public abstract List<Client> getAllClients() throws SQLException;
	
	/**
	 * This method returns a Ship from the database
	 * 
	 * @param id is an int that represents the id
	 * @return Ship a representation of ship, or null if none was found
	 */
	public abstract Client getClientById(int iRecordId) throws SQLException;
	
	// Here we are making use of a parameter known as AddShipDTO, which is a Data Transfer Object
	// DTOs are classes that are used to pass data around that might not completely conform to the actual "Model" class
	// The "Model" class in this case is the Ship class, which will define ALL of the attributes associated with the data inside the
	// database
	public abstract Client addClient(ClientDTO objClient) throws SQLException;
	
	public abstract Client editClient(int iRecordId, ClientDTO objClient) throws SQLException;

}
