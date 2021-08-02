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
			//String sql = "SELECT * FROM jdbc_demo.ship";
			String sSQL  = "SELECT * FROM project0.client";
			//String sSQL = "SELECT * FROM " + csClientTable + ";";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");
			ResultSet objResultSet = objStatement.executeQuery(sSQL);

			// 4. Process the results
			while (objResultSet.next()) {// data exists in the results set

				int iClientId = objResultSet.getInt("client_id");
				String sFirstName = objResultSet.getString("client_first_name");
				String sLastName = objResultSet.getString("client_last_name");

				Client objClient = new Client(iClientId, sFirstName, sLastName);
				lstClients.add(objClient);
				objLogger.info(sMethod + "add client to list: [" + objClient.toString() + "]");
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

			PreparedStatement ObjPreparedStatmnt = conConnection.prepareStatement(sSQL);

			ObjPreparedStatmnt.setInt(1, iClientId); // set passed in client id in place of ?

			// Execute the query
			ResultSet ObjResultSet = ObjPreparedStatmnt.executeQuery();

			// This should be a unique get based on the client id
			// If there is a record then success, else not found
			if (ObjResultSet.next()) {
				int iRecordId = ObjResultSet.getInt(csClientTblClientId);
				String sFirstName = ObjResultSet.getString(csClientTblFirstName);
				String sLastName = ObjResultSet.getString(csClientTblLastName);

				Client objClient = new Client(iRecordId, sFirstName, sLastName);

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
	public Client addClient(AddOrEditClientDTO objClient) throws SQLException {
		String sMethod = "addClient(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "INSERT INTO " + csClientTable + " (" + csClientTblFirstName + ", " + csClientTblLastName
					+ ") VALUES (?, ?)";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement ObjPreparedStatmnt = conConnection.prepareStatement(sSQL,
					Statement.RETURN_GENERATED_KEYS);

			ObjPreparedStatmnt.setString(1, objClient.getFirstName());
			ObjPreparedStatmnt.setString(2, objClient.getLastName());

			// Use executeUpdate when working with INSERT, UPDATE, or DELETE
			int iRecsAdded = ObjPreparedStatmnt.executeUpdate();
			// Use executeQuery() if you need a ResultSet (obviously coming from using
			// SELECT)
			if (iRecsAdded != 1) {
				String sMsg = sMethod + "Error inserting new client record: [" + objClient.getFirstName() + "]" + "["
						+ objClient.getLastName() + "]";
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}

			//
			// Return the Client record inserted in the database and include the automatic
			// generated primary key for the record key
			ResultSet objRSGeneratedKeys = ObjPreparedStatmnt.getGeneratedKeys();
			if (objRSGeneratedKeys.next()) {
				Client objCreatedClient = new Client(objRSGeneratedKeys.getInt(1), objClient.getFirstName(),
						objClient.getLastName());
				objLogger.debug(sMethod + "Return Created Client: [" + objCreatedClient.toString() + "].");
				return objCreatedClient;
			} else {
				String sMsg = sMethod + "Autogenerated client id could not be obtained for: ["
						+ objClient.getFirstName() + "]" + "[" + objClient.getLastName() + "]";
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}

		}
	}

	//
	// ###
	@Override
	public Client editClient(int iClientId, AddOrEditClientDTO objClient) throws SQLException {
		String sMethod = "editClient(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "UPDATE " + csClientTable + " SET " + csClientTblFirstName + " = ?," + csClientTblLastName
					+ " = ? WHERE " + csClientTblClientId + " = ?";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement ObjPreparedStatmnt = conConnection.prepareStatement(sSQL);

			ObjPreparedStatmnt.setString(1, objClient.getFirstName());
			ObjPreparedStatmnt.setString(2, objClient.getLastName());
			ObjPreparedStatmnt.setInt(3, iClientId);

			int iRecUpdated = ObjPreparedStatmnt.executeUpdate();
			if (iRecUpdated != 1) {
				String sMsg = sMethod + "Error updating existing client record: [" + objClient.getFirstName() + "]"
						+ "[" + objClient.getLastName() + "]";
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}

			return new Client(iClientId, objClient.getFirstName(), objClient.getLastName());
		}

	}

	//
	// ###
	@Override
	public void deleteClient(int iClientId) throws SQLException {
		String sMethod = "deleteClient(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "DELETE FROM jdbc_demo.ship WHERE id = ?";

			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement ObjPreparedStatmnt = conConnection.prepareStatement(sSQL);

			ObjPreparedStatmnt.setInt(1, iClientId);

			int iRecssDeleted = ObjPreparedStatmnt.executeUpdate();

			// if it is not 1, we know that no records were actually deleted
			if (iRecssDeleted != 1) {
				String sMsg = sMethod + "Error deleting client record with id: [" + iClientId + "]";
				objLogger.warn(sMsg);
				throw new SQLException(sMsg);
			}
		}

	}

}
