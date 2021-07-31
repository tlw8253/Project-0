package com.tlw8253.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Constants;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.model.Client;
import com.tlw8253.util.ConnectionUtility;

public class ClientDAOImpl implements Constants, ClientDAO {
	private Logger objLogger = LoggerFactory.getLogger(ClientDAOImpl.class);

	public ClientDAOImpl() {
		super();
	}
	
	public List<Client> getAllClients() throws SQLException {
		String sMethod = "getAllClients(): ";
		objLogger.trace(sMethod + "Entered");
		
		// Construct a List for Clients
		// This will store all Clients that exist in the database and pass it back to the user
		List<Client> lstClients = new ArrayList<>();
		
		try (Connection conConnection = ConnectionUtility.getConnection()) {
			
			// 1. get a Connection object			
			// 2. Obtain a Statement object (Statement, PreparedStatement, CallableStatement)			
			Statement objStatement = conConnection.createStatement();
			
			// 3. Execute the query
			String sSQL = "SELECT * FROM " + csClientTable;
			ResultSet objResultSet = objStatement.executeQuery(sSQL);
			
			// 4. Process the results
			while (objResultSet.next()) {//data exists in the results set
				
				int iClientId = objResultSet.getInt("client_id");
				String sFirstName = objResultSet.getString("client_first_name");
				String sLastName = objResultSet.getString("client_last_name");
				
				Client objClient = new Client(iClientId, sFirstName, sLastName);
				lstClients.add(objClient);
				objLogger.info(sMethod + "add client to list: [" + objClient.toString() + "]");
				
				
				//Ship ship = new Ship(id, name, age);				
				//ships.add(ship);
			}
		}
		
		return lstClients;
	}
	
	public Client getClientById(int iRecordId) throws SQLException{
		Client objClient = new Client();
		
		return(objClient);
		
	}

	public Client editClient(int iRecordId, AddOrEditClientDTO objClient) throws SQLException {
		Client objClient = new Client();
		
		return(objClient);
		
	}
}












