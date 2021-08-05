package com.tlw8253.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

//GENERAL comment for all hard coded SQL statements in the program.
//It is a better practice to called a database stored procedure instead of
//  hard coding SQL statements in the Java code.  Future revision is to create
//  stored procedures in the database, call from Java code and process the 
//  result set.
//

/**
 * 
 * @author tlw8253
 *
 */

public class ClientDAOImpl implements Constants, ClientDAO {
	private Logger objLogger = LoggerFactory.getLogger(ClientDAOImpl.class);

	public ClientDAOImpl() {
		super();
	}

	//
	// ###
	@Override
	public List<Client> getAllClients() throws SQLException {
		String sMethod = "getAllClients(): ";
		objLogger.trace(sMethod + "Entered");

		// Construct a List for Clients
		// This will store all Clients that exist in the database and pass it back to
		// the user
		List<Client> lstClients = new ArrayList<>();

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			// 1. get a Connection object
			// 2. Obtain a Statement object (Statement, PreparedStatement,
			// CallableStatement)
			Statement objStatement = conConnection.createStatement();

			// 3. Execute the query
			//String sSQL  = "SELECT * FROM project0.client";
			String sSQL = "SELECT * FROM " + csClientTable;
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");
			
			ResultSet objResultSet = objStatement.executeQuery(sSQL);

			// 4. Process the results
			while (objResultSet.next()) {// data exists in the results set
				

				int iClientId = objResultSet.getInt(csClientTblClientId);
				String sFirstName = objResultSet.getString(csClientTblFirstName);
				String sLastName = objResultSet.getString(csClientTblLastName);
				String sNickname = objResultSet.getString(csClientTblNickname);

				Client objClient = new Client(iClientId, sFirstName, sLastName, sNickname);
				objLogger.info(sMethod + "Add client to list: [" + objClient.toString() + "]");
				lstClients.add(objClient);
			}
		}
		objLogger.debug(sMethod + "lstClients: [" + lstClients.toString() + "]");
		return (lstClients);
	}

	//
	// ###
	@Override
	public Client getClientById(int iClientId) throws SQLException {
		String sMethod = "getClientById(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			// '?' is a placeholder for client id
			String sSQL = "SELECT * FROM " + csClientTable + " WHERE " + csClientTblClientId + " = ?";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);

			objPreparedStatmnt.setInt(1, iClientId); // set passed in client id in place of ?

			// Execute the query
			ResultSet objResultSet = objPreparedStatmnt.executeQuery();

			// This should be a unique get based on the client id
			// If there is a record then success, else not found
			if (objResultSet.next()) {
				int iRecordId = objResultSet.getInt(csClientTblClientId);
				String sFirstName = objResultSet.getString(csClientTblFirstName);
				String sLastName = objResultSet.getString(csClientTblLastName);
				String sNickname = objResultSet.getString(csClientTblNickname);

				Client objClient = new Client(iRecordId, sFirstName, sLastName, sNickname);

				objLogger.debug(sMethod + "Return Client: [" + objClient.toString() + "].");
				return (objClient);
			} else {
				objLogger.debug(sMethod + "Client with id: [" + iClientId + "] not found in database.");
				return null;
			}
		}
	}

	//
	// ###
	@Override
	public Client addClient(AddOrEditClientDTO objAddClientDTO) throws SQLException {
		String sMethod = "addClient(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "INSERT INTO " + csClientTable + " (" + csClientTblFirstName + ", " 
							+ csClientTblLastName + ", " 
							+ csClientTblNickname + ") VALUES (?, ?, ?)";
			
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL, Statement.RETURN_GENERATED_KEYS);
			
			String sFirstName = objAddClientDTO.getFirstName();
			String sLastName = objAddClientDTO.getLastName();
			String sNickname = objAddClientDTO.getNickname();

			objPreparedStatmnt.setString(1, sFirstName);
			objPreparedStatmnt.setString(2, sLastName);
			objPreparedStatmnt.setString(3, sNickname);

			// Use executeUpdate when working with INSERT, UPDATE, or DELETE
			int iRecsAdded = objPreparedStatmnt.executeUpdate();
			// Use executeQuery() if you need a ResultSet (obviously coming from using SELECT)
			if (iRecsAdded != 1) {
				String sMsg = sMethod + "Error inserting new client record: [" + sFirstName + "]" + " ["
						+ sLastName + "] [" + sNickname + "]";
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}

			//
			// Return the Client record inserted in the database and include the automatic
			// generated primary key for the record key
			ResultSet objRSGeneratedKeys = objPreparedStatmnt.getGeneratedKeys();
			if (objRSGeneratedKeys.next()) {
				Client objCreatedClient = new Client(objRSGeneratedKeys.getInt(1), objAddClientDTO.getFirstName(),
						objAddClientDTO.getLastName(), objAddClientDTO.getNickname());
				objLogger.debug(sMethod + "Return Created Client: [" + objCreatedClient.toString() + "].");
				return objCreatedClient;
			} else {
				String sMsg = sMethod + "Autogenerated client id could not be obtained for: [" + objAddClientDTO.toString() + "]";						
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}

		}
	}

	//
	// ###
	@Override
	public Client editClient(int iClientId, AddOrEditClientDTO objEditClientDTO) throws SQLException {
		String sMethod = "editClient(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "UPDATE " + csClientTable 
						+ " SET " + csClientTblFirstName + " = ?," 
						+ csClientTblLastName + " = ?," 
						+ csClientTblNickname + " = ?"						
						+ " WHERE " + csClientTblClientId + " = ?";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);

			objPreparedStatmnt.setString(1, objEditClientDTO.getFirstName());
			objPreparedStatmnt.setString(2, objEditClientDTO.getLastName());
			objPreparedStatmnt.setString(3, objEditClientDTO.getNickname());
			objPreparedStatmnt.setInt(4, iClientId);

			int iRecUpdated = objPreparedStatmnt.executeUpdate();
			if (iRecUpdated != 1) {
				String sMsg = sMethod + "Error updating existing client record: [" + objEditClientDTO.toString() + "]";
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}
			
			Client objRetClient = new Client(iClientId, objEditClientDTO.getFirstName(), objEditClientDTO.getLastName(), objEditClientDTO.getNickname());
			objLogger.debug(sMethod + "objRetClient: [" + objRetClient.toString() + "]");

			return objRetClient;
		}

	}

	//
	// ###
	@Override
	public void deleteClient(int iClientId) throws SQLException {
		String sMethod = "deleteClient(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "DELETE FROM " + csClientTable + " WHERE " + csClientTblClientId + " = ?";

			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);

			objPreparedStatmnt.setInt(1, iClientId);

			int iRecssDeleted = objPreparedStatmnt.executeUpdate();

			// if it is not 1, we know that no records were actually deleted
			if (iRecssDeleted != 1) {
				String sMsg = sMethod + "Error deleting client record with id: [" + iClientId + "]";
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}
		}

	}

}
