package com.tlw8253.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.service.AccountService;
import com.tlw8253.service.ClientService;
import com.tlw8253.application.Constants;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.model.Account;
import com.tlw8253.model.Client;

import io.javalin.Javalin;
import io.javalin.http.Handler;

/**
 * From Project-0 requirements these are the expected requests and responses:
 * 
 * 	 * -(COMPLETED) `POST /clients`: Creates a new client 
	 * - `POST /clients/{client_id}/accounts`: Create a new account for a client with id of X (if client exists)
	 * 
	 * - (COMPLETED) `PUT /clients/{id}`: Update client with an id of X (if the client exists)
	 * - `PUT /clients/{client_id}/accounts/{account_id}`: Update account with id of Y belonging to client with id of 
	 * 		X (if client and account exist AND if account belongs to client)
	 * 
	 * - (COMPLETED) `DELETE /clients/{id}`: Delete client with an id of X (if the client exists)
	 * - `DELETE /clients/{client_id}/accounts/{account_id}`: Delete account with id of Y belonging to 
	 * 		client with id of X (if client and account exist AND if account belongs to client)
	 * 
	 * -(COMPLETED) `GET /clients`: Gets all clients
	 * -(COMPLETED) `GET /clients/{id}`: Get client with an id of X (if the client exists)	 * 
	 * -(COMPLETED) `GET /clients/{client_id}/accounts`: Get all accounts for client with id of X (if client exists)
	 * 
	 * - `GET /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400`: 
	 * 		Get all accounts for client id of X with balances between 400 and 2000 (if client exists)
	 * - `GET /clients/{client_id}/accounts/{account_id}`: Get account with id of Y belonging to client with id of 
	 * 		X (if client and account exist AND if account belongs to client)
 * 
 * @author tlw87
 *
 */


public class ClientController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ClientController.class);
	private ClientService objClientService;
	
	public ClientController() {
		this.objClientService = new ClientService();
	}
	

	
	
	//
	//### `GET /clients`: Gets all clients
	private Handler getAllClients = (objCtx) -> {	
		String sMethod = "getAllClients(): ";
		objLogger.trace(sMethod + "Entered");

		List<Client> lstClients = objClientService.getAllClients();
		
		objLogger.debug(sMethod + "lstClients: [" + lstClients.toString() + "]");
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstClients);
	};

	//
	//### `GET /clients/{id}`: Get client with an id of X (if the client exists)
	private Handler getClientById = (objCtx) -> {		
		String sMethod = "getClientById(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String,String> mPathParmaMap =  objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");
		
		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");		
		
		Client objClient = objClientService.getClientById(sClientId);
		objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objClient);
	};

	//
	//### `GET /clients/{client_id}/accounts`: Get all accounts for client with id of X (if client exists)
	private Handler getClientAccounts = (objCtx) -> {		
		String sMethod = "getClientAccounts(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String,String> mPathParmaMap =  objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");
		
		String sAccounts = objCtx.pathParam(csParamAccounts);
		objLogger.debug(sMethod + "Context parameter client id: [" + sAccounts + "]");		
		
		//first make sure this is an actual accounts request
		if (sAccounts.equals(csParamAccounts)) {
			
			//second get client from database / make sure they exists
			Client objClient = objClientService.getClientById(sClientId);
			objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");
			
			//third get the accounts for this client
			AccountService objAccountService = new AccountService();
			//use int method since we know client exists
			List<Account> lstAccounts = objAccountService.getAccountsForClient(objClient.getRecordId()); 
			objLogger.debug(sMethod + "Client accounts from AccountSerice: [" + lstAccounts.toString() + "]");
			
			objClient.set(lstAccounts); // add accounts in client for the json response
			
			objCtx.status(ciStatusCodeSuccess);
			objCtx.json(objClient);	//send client object with accounts
			//objCtx.json(lstAccounts);
		}
		else {
			objCtx.status(ciStatusCodeErrorBadRequest);			
		}		
		
	};
	
	//
	//### - `POST /clients`: Creates a new client
	private Handler postAddClient = (objCtx) -> {
		String sMethod = "postAddClient(): ";
		objLogger.trace(sMethod + "Entered");
		
		Map<String,String> mPathParmaMap =  objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");
		
		String sFirstName = objCtx.pathParam(csParamClientFirstName);
		objLogger.debug(sMethod + "Context parameter " + csParamClientFirstName + ": [" + sFirstName + "]");
		
		String sLastName = objCtx.pathParam(csParamClientLastName);
		objLogger.debug(sMethod + "Context parameter " + csParamClientLastName + ": [" + sLastName + "]");
		
		String sNickname = objCtx.pathParam(csParamClientNickname);
		objLogger.debug(sMethod + "Context parameter " + csParamClientNickname + ": [" + sNickname + "]");
		
		AddOrEditClientDTO objClientToAdd = new AddOrEditClientDTO(sFirstName, sLastName, sNickname);
		objLogger.debug(sMethod + "objClientToAdd: [" + objClientToAdd.toString() + "]");
		
		Client objAddedClient = objClientService.addClient(objClientToAdd);
		objLogger.debug(sMethod + "objAddedClient: [" + objAddedClient.toString() + "]");
		objCtx.json(objAddedClient);
		

		/*  GETTING: Couldn't deserialize body to AddClientDTO Error from Postman
		AddClientDTO objClientToAdd = objCtx.bodyAsClass(AddClientDTO.class);
		objLogger.debug(sMethod + "objClientToAdd" + objClientToAdd.toString());
		*/
		
	};
	
	//
	//### - `POST /clients`: Creates a new client
	private Handler postUpdateClient = (objCtx) -> {
		String sMethod = "postUpdateClient(): ";
		objLogger.trace(sMethod + "Entered");
		
		Map<String,String> mPathParmaMap =  objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");
		
		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter " + csParamClientId + ": [" + sClientId + "]");

		String sFirstName = objCtx.pathParam(csParamClientFirstName);
		objLogger.debug(sMethod + "Context parameter " + csParamClientFirstName + ": [" + sFirstName + "]");
		
		String sLastName = objCtx.pathParam(csParamClientLastName);
		objLogger.debug(sMethod + "Context parameter " + csParamClientLastName + ": [" + sLastName + "]");
		
		String sNickname = objCtx.pathParam(csParamClientNickname);
		objLogger.debug(sMethod + "Context parameter " + csParamClientNickname + ": [" + sNickname + "]");
		
		AddOrEditClientDTO objClientToUpdate = new AddOrEditClientDTO(sFirstName, sLastName, sNickname);
		objLogger.debug(sMethod + "objClientToAdd: [" + objClientToUpdate.toString() + "]");
		
		Client objAddedClient = objClientService.editClient(sClientId, objClientToUpdate);
		objLogger.debug(sMethod + "objAddedClient: [" + objAddedClient.toString() + "]");
		objCtx.json(objAddedClient);
		
	};


	//
	//### `GET /clients/{id}`: Get client with an id of X (if the client exists)
	private Handler deleteClientById = (objCtx) -> {		
		String sMethod = "deleteClientById(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String,String> mPathParmaMap =  objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");
		
		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");		
		
		objClientService.deleteClient(sClientId);
		//objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json("Client with id: [" + sClientId + "] removed from database.");
	};

	
	
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/clients", getAllClients);	//`GET /clients`: Gets all clients
		//- `GET /clients/{client_id}`: Get client with an id of X (if the client exists)
		app.get("/client/:" + csParamClientId, getClientById);	
		//- `GET /clients/{client_id}/accounts`: Get all accounts for client with id of X (if client exists)
		app.get("/client/:" + csParamClientId + "/:" + csParamAccounts, getClientAccounts);
		
		//- `POST /clients`: Creates a new client
		app.post("/client/:"  + csClientTblFirstName 
				+ "/:" + csClientTblLastName 
				+ "/:" + csClientTblNickname
		, postAddClient);
		
		//- `PUT /clients/{id}`: Update client with an id of X (if the client exists)
		app.put("/client/:"  + csParamClientId
				+ "/:" + csClientTblFirstName 
				+ "/:" + csClientTblLastName 
				+ "/:" + csClientTblNickname
		, postUpdateClient);
		
		//- `DELETE /clients/{id}`: Delete client with an id of X (if the client exists)
		app.delete("/client/:" + csParamClientId, deleteClientById);	
		
		
		
		/*
		app.get("/ship", getAllShips);
		app.get("/ship/:shipid", getShipById);
		app.post("/ship", addShip);
		app.put("/ship/:shipid", editShip);
		app.delete("/ship/:shipid", deleteShip);
		*/
	}

}
