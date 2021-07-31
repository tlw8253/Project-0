package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.exception.ClientNotFoundException;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.model.Client;
import com.tlw8253.exception.BadParameterException;
import com.revature.dto.AddOrEditShipDTO;
import com.revature.exception.ShipNotFoundException;
import com.revature.model.Ship;
import com.tlw8253.dao.ClientDAO;
import com.tlw8253.dao.ClientDAOImpl;
import com.tlw8253.dto.AddOrEditClientDTO;


public class ClientService {
	private Logger objLogger = LoggerFactory.getLogger(ClientService.class);
	
	// ClientDAO is a dependency of ClientService
	private ClientDAO objClientDAO;

	
	public ClientService() {
		this.objClientDAO = new ClientDAOImpl();
	}

	// This method is dependent on a ClientDAO object
	// Because we're invoking the getAllClients() method from ClientDAO
	public List<Client> getAllClients() throws DatabaseException {
		String sMethod = "getAllClients(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			List<Client> lstClients = objClientDAO.getAllClients();
			
			return (lstClients);
		} catch (SQLException objE) {
			objLogger.error(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException("DAO operation error.");
		}
	}

	//
	//###
	public Client getClientById(String sId) throws DatabaseException, ClientNotFoundException, BadParameterException {
		String sMethod = "getClientById(): ";
		objLogger.trace(sMethod + "Entered");
		
		try {
			int iId = Integer.parseInt(sId);
			
			Client objClient = objClientDAO.getClientById(iId);
			
			if (objClient == null) {
				throw new ClientNotFoundException("Client with id " + iId + " was not found");
			}
			
			return objClient;
		} catch (SQLException objE) {
			objLogger.error(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException("DAO operation error.");
		} catch (NumberFormatException objE) {
			objLogger.warn(sMethod + "NumberFormatException: [" + objE.getMessage() + "]");
			throw new BadParameterException("ClientId received: [" + sId + "] is not an integer value.");
		}
	}

	//
	//###
	public Client addClient(AddOrEditClientDTO objClient) throws DatabaseException, BadParameterException {
		String sMethod = "addClient(): ";
		objLogger.trace(sMethod + "Entered");

		if (objClient.getFirstName().trim().equals("")) {
			throw new BadParameterException("Client first name cannot be blank.");
		}
		
		if (objClient.getLastName().trim().equals("")) {
			throw new BadParameterException("Client last name cannot be blank.");
		}

		try {
			Client objAddedClient = objClientDAO.addClient(objClient);
			
			return objAddedClient;
		} catch (SQLException objE) {
			objLogger.error(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException("DAO operation error.");
		}
	}

	//
	//###
	public Client editClient(String sId, AddOrEditClientDTO objClient) throws DatabaseException, ClientNotFoundException, BadParameterException {
		String sMethod = "editClient(): ";
		objLogger.trace(sMethod + "Entered");
		
		try {
			int iClientId = Integer.parseInt(sId);
			
			// Before we can edit a Ship, see if the ship already exists, and if not, throw an Exception
			if (objClientDAO.getClientById(iClientId) == null) {
				throw new ClientNotFoundException("Client with id [" + iClientId + "] was not found");
			}
			
			// If ship exists, we proceed to edit the ship
			Client objEditedClient = objClientDAO.editClient(iClientId, objClient);
			
			return objEditedClient;
			
		} catch (SQLException objE) {
			objLogger.error(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException("Something went wrong with our DAO operations");
		} catch (NumberFormatException objE) {
			objLogger.warn(sMethod + "NumberFormatException: [" + objE.getMessage() + "]");
			throw new BadParameterException("ClientId received: [" + sId + "] is not an integer value.");		}

	}

	
	
	
	
	
	
}
