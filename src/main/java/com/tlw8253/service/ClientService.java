package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.exception.DatabaseException;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.ClientNotFoundException;
import com.tlw8253.application.Constants;
import com.tlw8253.dao.ClientDAO;
import com.tlw8253.dao.ClientDAOImpl;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.model.Client;

public class ClientService implements Constants{
	private Logger objLogger = LoggerFactory.getLogger(ClientService.class);

	private ClientDAO objClientDAO;

	public ClientService() {
		this.objClientDAO = new ClientDAOImpl();
	}

	// Fake Client object used for testing
	public ClientService(ClientDAO objMockedClientDAO) {
		this.objClientDAO = objMockedClientDAO;
	}

	// Dependency on ClientDAO to invoke the getAllClients() method
	public List<Client> getAllClients() throws DatabaseException {
		String sMethod = "getAllClients(): ";
		List<Client> lstClients;
		try {
			lstClients = objClientDAO.getAllClients();

		} catch (SQLException objE) {
			String sMsg = csMsgDB_ErrorGettingAllClients;
			objLogger.error(sMethod + sMsg);
			throw new DatabaseException(sMsg); //easier to have static message for JUnit testing
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
				String sMsg = "Client with id: [" + iClientId + "] not found in the database.";
				objLogger.debug( sMethod + sMsg);
				throw new ClientNotFoundException(csMsgClientNotFound); //easier to have static message for JUnit testing
			}
			return (objClient);
		} catch (SQLException objE) {
			objLogger.error(sMethod + csMsgDB_ErrorGettingByClientId + "[" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingByClientId);
			
		} catch (NumberFormatException objE) {

			String sMsg = "Client Id: [" + sClientId + "] is not an integer.";
			objLogger.debug(sMethod + sMsg + "[" + objE.getMessage() + "]");
			throw new BadParameterException(csMsgBadParamClientId); //easier to have static message for JUnit testing
		}

	}

	//
	// ###
	public Client addClient(AddOrEditClientDTO objAddClientDTO) throws DatabaseException, BadParameterException {
		String sMethod = "addClient(): ";

		//check first and last name for values, nickname is not required
		if (objAddClientDTO.getFirstName().trim().equals("") || 
				objAddClientDTO.getLastName().trim().equals("")) {
			String sMsg = "Client name must not contain a blank: first name: [" + objAddClientDTO.getFirstName()
					+ "]" + " last name [" + objAddClientDTO.getLastName() + "]";
			objLogger.debug(sMethod + sMsg);

			throw new BadParameterException(csMsgBadParamClientName);//easier to have static message for JUnit testing
		}

		try {
			Client objAddedClient = objClientDAO.addClient(objAddClientDTO);
			objLogger.debug(sMethod + "objAddedClient: [" + objAddedClient.toString() + "]");
			return (objAddedClient);
		} catch (SQLException objE) {
			String sMsg = "Database error adding client: first name: [" + objAddClientDTO.getFirstName() + "]"
					+ " last name [" + objAddClientDTO.getLastName() + "]";
			objLogger.error(sMethod + sMsg + "[" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingClient); //easier to have static message for JUnit testing
		}
	}

	//
	// ###
	public Client editClient(String sClientId, AddOrEditClientDTO objEditClientDTO)
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		String sMethod = "editClient(): ";
		String sMsg = "";

		try {
			int iClientId = Integer.parseInt(sClientId);

			// Client must exist to edit if client does not exist, throw an Exception
			if (objClientDAO.getClientById(iClientId) == null) {
				sMsg = "Client with id: [" + iClientId + "] was not found, unable to update.";
				objLogger.debug(sMethod + sMsg);
				throw new ClientNotFoundException(csMsgClientNotFound);
			}

			// record found, update the Client
			Client objEditedClient = objClientDAO.editClient(iClientId, objEditClientDTO);
			return objEditedClient;
			
		} catch (SQLException objE) {
			sMsg = "Database error updating client: first name: [" + objEditClientDTO.getFirstName() + "]"
					+ " last name [" + objEditClientDTO.getLastName() + "]";
			objLogger.error(sMethod + sMsg + "[" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorUpdatingClient); //easier to have static message for JUnit testing
			
		} catch (NumberFormatException objE) {
			sMsg = "Client Id is not an integer: [" + sClientId + "]";
			objLogger.error(sMethod + sMsg + "[" + objE.getMessage() + "]");
			throw new BadParameterException(csMsgBadParamClientId); //easier to have static message for JUnit testing
		}

	}

	//
	//###
	public boolean deleteClient(String sClientId)
			throws BadParameterException, DatabaseException, ClientNotFoundException {
		String sMethod = "deleteClient(): ";
		String sMsg = "";
		boolean bClientDelete = false;

		try {
			int iClientId = Integer.parseInt(sClientId);

			// Client must exist to delete if client does not exist, throw an Exception
			if (objClientDAO.getClientById(iClientId) == null) {
				sMsg = "Client with id: [" + iClientId + "] was not found, unable to delete.";
				objLogger.debug(sMethod + sMsg);
				throw new ClientNotFoundException(csMsgClientNotFound); //easier to have static message for JUnit testing
			}

			// record found, delete the Client
			objClientDAO.deleteClient(iClientId);
			bClientDelete=true;
			
		} catch (SQLException objE) {
			sMsg = "Database error deleting client with id: [" + sClientId + "]";
			objLogger.error(sMethod + sMsg + "[" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorDeletingClient); //easier to have static message for JUnit testing
			
		} catch (NumberFormatException objE) {
			sMsg = sMethod + "Client Id is not an integer: [" + sClientId + "]";
			objLogger.error(sMethod + sMsg + "[" + objE.getMessage() + "]");
			throw new BadParameterException(csMsgBadParamClientId); //easier to have static message for JUnit testing
		}

		return(bClientDelete);
	}
	
	

	/*
	
	*/


}
