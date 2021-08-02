package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.exception.DatabaseException;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.ClientNotFoundException;
import com.tlw8253.dao.ClientDAO;
import com.tlw8253.dao.ClientDAOImpl;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.model.Client;

public class ClinetService {
	private Logger objLogger = LoggerFactory.getLogger(ClinetService.class);

	private ClientDAO objClientDAO;

	public ClinetService() {
		this.objClientDAO = new ClientDAOImpl();
	}

	// Fake Client object used for testing
	public ClinetService(ClientDAO objMockedClientDAO) {
		this.objClientDAO = objMockedClientDAO;
	}

	// Dependency on ClientDAO to invoke the getAllClients() method
	public List<Client> getAllClients() throws DatabaseException {
		String sMethod = "getAllClients(): ";
		List<Client> lstClients;
		try {
			lstClients = objClientDAO.getAllClients();

		} catch (SQLException objE) {
			String sMsg = sMethod + "Error with database getting all clients.";
			objLogger.error(sMsg);
			throw new DatabaseException(sMsg);
		}
		return (lstClients);
	}

	//
	// ###
	public Client getClientById(String sClientId)
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		String sMethod = "getClientById(): ";
		try {
			int iClientId = Integer.parseInt(sClientId);
			Client objClient = objClientDAO.getClientById(iClientId);

			if (objClient == null) {
				String sMsg = sMethod + "Client with id: [" + iClientId + "] not found in the database.";
				objLogger.info(sMsg);
				throw new ClientNotFoundException(sMsg);
			}
			return (objClient);
		} catch (SQLException objE) {
			String sMsg = sMethod + "Database error getting the client by id.";
			objLogger.error(sMsg + "[" + objE.getMessage() + "]");
			throw new DatabaseException(sMsg);
		} catch (NumberFormatException objE) {

			String sMsg = sMethod + "Client Id: [" + sClientId + "] is not an integer.";
			objLogger.debug(sMsg + "[" + objE.getMessage() + "]");
			throw new BadParameterException(sMsg);
		}

	}

	//
	// ###
	public Client addClient(AddOrEditClientDTO objClient) throws DatabaseException, BadParameterException {
		String sMethod = "addClient(): ";

		if (objClient.getFirstName().trim().equals("") || objClient.getLastName().trim().equals("")) {
			String sMsg = sMethod + "Client name must not contain a blank: first name: [" + objClient.getFirstName()
					+ "]" + " last name [" + objClient.getLastName() + "]";
			objLogger.debug(sMsg);

			throw new BadParameterException(sMsg);
		}

		try {
			Client objAddedClient = objClientDAO.addClient(objClient);

			return (objAddedClient);
		} catch (SQLException objE) {
			String sMsg = sMethod + "Database error adding client: first name: [" + objClient.getFirstName() + "]"
					+ " last name [" + objClient.getLastName() + "]";
			objLogger.error(sMsg + "[" + objE.getMessage() + "]");
			throw new DatabaseException(sMsg);
		}

	}

	//
	// ###
	public Client editClient(String sClientId, AddOrEditClientDTO objClient)
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		String sMethod = "editClient(): ";
		String sMsg = "";

		try {
			int iClientId = Integer.parseInt(sClientId);

			// Client must exist to edit if client does not exist, throw an Exception
			if (objClientDAO.getClientById(iClientId) == null) {
				sMsg = sMethod + "Client with id: [" + iClientId + "] was not found, unable to update.";
				objLogger.debug(sMsg);
				throw new ClientNotFoundException(sMsg);
			}

			// record found, update the Client
			Client objEditedClient = objClientDAO.editClient(iClientId, objClient);
			return objEditedClient;
		} catch (SQLException objE) {
			sMsg = sMethod + "Database error updating client: first name: [" + objClient.getFirstName() + "]"
					+ " last name [" + objClient.getLastName() + "]";
			objLogger.error(sMsg + "[" + objE.getMessage() + "]");
			throw new DatabaseException(sMsg);
		} catch (NumberFormatException objE) {
			sMsg = sMethod + "Client Id is not an integer: [" + sClientId + "]";
			objLogger.error(sMsg + "[" + objE.getMessage() + "]");
			throw new BadParameterException(sMsg);
		}

	}

	//
	//###
	public void deleteClient(String sClientId)
			throws BadParameterException, DatabaseException, ClientNotFoundException {
		String sMethod = "deleteClient(): ";
		String sMsg = "";

		try {
			int iClientId = Integer.parseInt(sClientId);

			// Client must exist to delete if client does not exist, throw an Exception
			if (objClientDAO.getClientById(iClientId) == null) {
				sMsg = sMethod + "Client with id: [" + iClientId + "] was not found, unable to delete.";
				objLogger.debug(sMsg);
				throw new ClientNotFoundException(sMsg);
			}

			// record found, delete the Client
			objClientDAO.deleteClient(iClientId);
		} catch (SQLException objE) {
			sMsg = sMethod + "Database error deleting client with id: [" + sClientId + "]";
			objLogger.error(sMsg + "[" + objE.getMessage() + "]");
			throw new DatabaseException(sMsg);
		} catch (NumberFormatException objE) {
			sMsg = sMethod + "Client Id is not an integer: [" + sClientId + "]";
			objLogger.error(sMsg + "[" + objE.getMessage() + "]");
			throw new BadParameterException(sMsg);
		}

	}

}
