package com.tlw8253.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.service.ClientService;
import com.tlw8253.application.Constants;
import com.tlw8253.model.Client;

import io.javalin.Javalin;
import io.javalin.http.Handler;

/**
 * From Project-0 requirements these are the expected requests and responses:
 * 
 * 	 * - `POST /clients`: Creates a new client
	 * - `POST /clients/{client_id}/accounts`: Create a new account for a client with id of X (if client exists)
	 * 
	 * - `PUT /clients/{id}`: Update client with an id of X (if the client exists)
	 * - `PUT /clients/{client_id}/accounts/{account_id}`: Update account with id of Y belonging to client with id of 
	 * 		X (if client and account exist AND if account belongs to client)
	 * 
	 * - `DELETE /clients/{id}`: Delete client with an id of X (if the client exists)
	 * - `DELETE /clients/{client_id}/accounts/{account_id}`: Delete account with id of Y belonging to 
	 * 		client with id of X (if client and account exist AND if account belongs to client)
	 * 
	 * - `GET /clients`: Gets all clients
	 * - `GET /clients/{id}`: Get client with an id of X (if the client exists)
	 * - `GET /clients/{client_id}/accounts`: Get all accounts for client with id of X (if client exists)
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
		
		objCtx.status(200); // 200 means OK
		objCtx.json(lstClients);
	};

	//
	//### `GET /clients/{id}`: Get client with an id of X (if the client exists)
	private Handler getClientById = (objCtx) -> {		
		String sMethod = "getClientById(): ";
		objLogger.trace(sMethod + "Entered");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");
		
		Client objClient = objClientService.getClientById(sClientId);
		objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");
		
		objCtx.status(200); // 200 means OK
		objCtx.json(objClient);
	};

	
	
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/clients", getAllClients);	//`GET /clients`: Gets all clients
		app.get("/clients/:" + csParamClientId, getClientById);	//`GET /clients/{id}`: Get client with an id of X (if the client exists)
		
		
		/*
		app.get("/ship", getAllShips);
		app.get("/ship/:shipid", getShipById);
		app.post("/ship", addShip);
		app.put("/ship/:shipid", editShip);
		app.delete("/ship/:shipid", deleteShip);
		*/
	}

}
