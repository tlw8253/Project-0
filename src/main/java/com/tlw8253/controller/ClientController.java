package com.tlw8253.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.service.AccountService;
import com.tlw8253.service.ClientService;
import com.tlw8253.application.Constants;
import com.tlw8253.dto.AccountAddDTO;
import com.tlw8253.dto.AccountEditDTO;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.model.Account;
import com.tlw8253.model.Client;

import io.javalin.Javalin;
import io.javalin.http.Handler;

/**
 * From Project-0 requirements these are the expected requests and responses
 * (endpoints):
 * 
 * * 20210807 - both POST requests initially implemented * -(COMPLETED) `POST
 * /clients`: Creates a new client -(COMPLETED) `POST
 * /clients/{client_id}/accounts`: Create a new account for a client with id of
 * X (if client exists)
 * 
 * 20210807 - both PUT requests initially implemented - (COMPLETED) `PUT
 * /clients/{id}`: Update client with an id of X (if the client exists) -
 * (COMPLETED) `PUT /clients/{client_id}/accounts/{account_id}`: Update account
 * with id of Y belonging to client with id of X (if client and account exist
 * AND if account belongs to client)
 * 
 * 20210808 - both DELETE requests initially implemented - (COMPLETED) `DELETE
 * /clients/{id}`: Delete client with an id of X (if the client exists) -
 * (COMPLETED) `DELETE /clients/{client_id}/accounts/{account_id}`: Delete
 * account with id of Y belonging to client with id of X (if client and account
 * exist AND if account belongs to client)
 * 
 * 20210806 - all GETs initially implemented -(COMPLETED) `GET /clients`: Gets
 * all clients -(COMPLETED) `GET /clients/{id}`: Get client with an id of X (if
 * the client exists) * -(COMPLETED) `GET /clients/{client_id}/accounts`: Get
 * all accounts for client with id of X (if client exists) * -(COMPLETED) `GET
 * /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400`: Get
 * all accounts for client id of X with balances between 400 and 2000 (if client
 * exists)
 * 
 * This requirement is not real clear. I believe they want to get a client's
 * account by account number, if and only if the account belongs to the client.
 * - (COMPLETED) `GET /clients/{client_id}/accounts/{account_id}`: Get account
 * with id of Y belonging to client with id of X (if client and account exist
 * AND if account belongs to client)
 * 
 * @author tlw8748253
 *
 */

public class ClientController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ClientController.class);
	private ClientService objClientService;
	AccountService objAccountService;

	public ClientController() {
		this.objClientService = new ClientService();
		objAccountService = new AccountService();
	}

	//
	// ### `GET /clients`: Gets all clients
	// Requirement did not specify to return client's accounts with this
	// request. So have this request just return list of clients and
	// add a request to get all clients with thier accounts
	private Handler getAllClients = (objCtx) -> {
		String sMethod = "getAllClients(): ";
		objLogger.trace(sMethod + "Entered");

		// list of all clients in the database
		List<Client> lstClients = objClientService.getAllClients();

		// list of clients without accounts
		objLogger.debug(sMethod + "lstClients: [" + lstClients.toString() + "]");

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstClients);
	};

	//
	private Handler getAllClientsWithAccountsByPass = (objCtx) -> {
		String sMethod = "getAllClientsWithAccountsByPass(): ";
		objLogger.trace(sMethod + "Entered");

		// list of all clients in the database
		List<Client> lstClients = objClientService.getAllClients();

		// get the accounts for each client
		for (int iCtr = 0; iCtr < lstClients.size(); iCtr++) {
			List<Account> lstAccounts = objAccountService.getAccountsForClient(lstClients.get(iCtr).getClientId());
			lstClients.get(iCtr).setAccounts(lstAccounts);
		}

		// list of clients with accounts
		objLogger.debug(sMethod + "lstClients: [" + lstClients.toString() + "]");
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstClients);

	};

	private Handler getAllClientsWithAccounts = (objCtx) -> {
		String sMethod = "getAllClientsWithAccounts(): ";
		objLogger.trace(sMethod + "Entered");

		// list of all clients in the database
		List<Client> lstClients = objClientService.getAllClients();

		// get the accounts for each client
		for (int iCtr = 0; iCtr < lstClients.size(); iCtr++) {
			List<Account> lstAccounts = objAccountService.getAccountsForClient(lstClients.get(iCtr).getClientId());
			lstClients.get(iCtr).setAccounts(lstAccounts);
		}

		// list of clients with accounts
		objLogger.debug(sMethod + "lstClients: [" + lstClients.toString() + "]");
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstClients);
	};

	//
	// ### `GET /clients/{id}`: Get client with an id of X (if the client exists)
	// The requirements have a specific end point to retrieve client information
	// with all their accounts. This method should not automatically add them
	// instead just return the client.
	private Handler getClientById = (objCtx) -> {
		String sMethod = "getClientById(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");

		Client objClient = objClientService.getClientById(sClientId);
		objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");

//		//get the accounts for this client
//		List<Account> lstAccounts = objAccountService.getAccountsForClient(sClientId);
//		objClient.setAccounts(lstAccounts);

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objClient);
	};

	//
	// ### `GET /clients/{client_id}/accounts`: Get all accounts for client with id
	// of X (if client exists)
	private Handler getClientAccounts = (objCtx) -> {
		String sMethod = "getClientAccounts(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context path parameter map: [" + mPathParmaMap + "]");

		Map<String, List<String>> mQueryParmaMap = objCtx.queryParamMap();
		objLogger.debug(sMethod + "Context query parameter map: [" + mQueryParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");

		// get client from database / make sure they exists
		Client objClient = objClientService.getClientById(sClientId);
		objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");

		objLogger.debug(
				sMethod + "Checking to see if we received parameters to restrict the retrieval of the accounts.");
		if (mQueryParmaMap.isEmpty()) {

			objLogger.debug(sMethod + "No parameters received, get all accounts for this client.");
			// no qualifying get account parameters, get all accounts for this client
			// use int method since we know client exists
			List<Account> lstAccounts = objAccountService.getAccountsForClient(objClient.getClientId());
			objLogger.debug(sMethod + "Client accounts from AccountSerice: [" + lstAccounts.toString() + "]");

			objClient.setAccounts(lstAccounts); // add accounts in client for the json response
			objCtx.json(objClient); // send client object with accounts
			
		} else {

			objLogger.debug(sMethod + "Recived parameters, getting the two expected parameters.");
			// could also use the query parameter map by key, but lets use the context
			// object gets
			String sLowerRange = objCtx.queryParam(csParamAccountsGreaterThan);
			String sUpperRange = objCtx.queryParam(csParamAccountsLessThan);

			String sMsg = csParamAccountsGreaterThan + ": [" + sLowerRange + "] " + csParamAccountsLessThan + ": [" + sUpperRange + "]";
			objLogger.debug(sMethod + sMsg);

			if ((sLowerRange.length() > 0) && (sUpperRange.length() > 0)) {
				AccountService objAccountService = new AccountService();

				// ranges have not been validated so use string method
				List<Account> lstAccounts = objAccountService.getAccountsForClientInRange(sClientId, sUpperRange,
						sLowerRange);
				objLogger.debug(
						sMethod + "Client accounts from AccountSerice within range: [" + lstAccounts.toString() + "]");

				objClient.setAccounts(lstAccounts); // add accounts in client for the json response
				objCtx.json(objClient); // send client object with accounts
			}
			else {
				sMsg = "Bad parameters received for getting client records: [" + sMsg + "]";
				objLogger.debug(sMethod + sMsg );
				objCtx.json(sMsg); 
			}

		}

		
		objCtx.status(ciStatusCodeSuccess);

	};

	//
	// ### - `POST /clients`: Creates a new client
	private Handler postAddClient = (objCtx) -> {
		String sMethod = "postAddClient(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");
		
		int iPathParmMapKeyCount = mPathParmaMap.size();
		objLogger.debug(sMethod + "Context parameter map number of keys: [" + iPathParmMapKeyCount + "]");

		String sFirstName = objCtx.pathParam(csParamClientFirstName);
		objLogger.debug(sMethod + "Context parameter " + csParamClientFirstName + ": [" + sFirstName + "]");

		String sLastName = objCtx.pathParam(csParamClientLastName);
		objLogger.debug(sMethod + "Context parameter " + csParamClientLastName + ": [" + sLastName + "]");

		String sNickname = "";
		if (iPathParmMapKeyCount > 2) {
			sNickname = objCtx.pathParam(csParamClientNickname);
			objLogger.debug(sMethod + "Context parameter " + csParamClientNickname + ": [" + sNickname + "]");
		}

		AddOrEditClientDTO objClientToAdd = new AddOrEditClientDTO(sFirstName, sLastName, sNickname);
		objLogger.debug(sMethod + "objClientToAdd: [" + objClientToAdd.toString() + "]");

		Client objAddedClient = objClientService.addClient(objClientToAdd);
		objLogger.debug(sMethod + "objAddedClient: [" + objAddedClient.toString() + "]");
		objCtx.json(objAddedClient);

		/*
		 * Problem: Couldn't deserialize body to AddClientDTO Error shown in Postman
		 * AddClientDTO objClientToAdd = objCtx.bodyAsClass(AddClientDTO.class);
		 * objLogger.debug(sMethod + "objClientToAdd" + objClientToAdd.toString());
		 * 
		 * The above problem stemmed from not filling out the body in postman.
		 */

	};

	//
	// ### - `POST /clients`: Creates a new client from post body
	private Handler postAddClientByBody = (objCtx) -> {
		String sMethod = "postAddClientByBody(): ";
		objLogger.trace(sMethod + "Entered");

		AddOrEditClientDTO objClientAddDTO = objCtx.bodyAsClass(AddOrEditClientDTO.class);
		objLogger.debug(sMethod + "objClientToAdd" + objClientAddDTO.toString());

		Client objAddedClient = objClientService.addClient(objClientAddDTO);
		objLogger.debug(sMethod + "objAddedClient: [" + objAddedClient.toString() + "]");
		objCtx.json(objAddedClient);
	};

	//
	// ### - `PUT /clients/{id}`: Update client with an id of X (if the client
	// exists)
	private Handler putUpdateClient = (objCtx) -> {
		String sMethod = "postUpdateClient(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
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
	// ### - `GET
	// /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400`:
	// Get all accounts for client id of X with balances between 400 and 2000 (if
	// client exists)
	private Handler getClientAccountsInRangeByPass = (objCtx) -> {
		String sMethod = "getClientAccountsInRangeByPass(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");

		String sAccounts = objCtx.pathParam(csParamAccounts);
		objLogger.debug(sMethod + "Context parameter accounts: [" + sAccounts + "]");

		// first make sure this is an actual accounts request
		if (sAccounts.equals(csParamAccounts)) {

			// second get client from database / make sure they exists
			Client objClient = objClientService.getClientById(sClientId);
			objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");

			// now get account range values
			String sUpperRange = objCtx.pathParam(csParamAccountsLessThan);
			objLogger.debug(sMethod + "Context parameter for upper range: [" + sUpperRange + "]");

			String sLowerRange = objCtx.pathParam(csParamAccountsGreaterThan);
			objLogger.debug(sMethod + "Context parameter for upper range: [" + sLowerRange + "]");

			// third get the accounts for this client
			AccountService objAccountService = new AccountService();

			// ranges have not been validated so use string method
			List<Account> lstAccounts = objAccountService.getAccountsForClientInRange(sClientId, sUpperRange,
					sLowerRange);
			objLogger.debug(
					sMethod + "Client accounts from AccountSerice within range: [" + lstAccounts.toString() + "]");

			objClient.setAccounts(lstAccounts); // add accounts in client for the json response

			objCtx.status(ciStatusCodeSuccess);
			objCtx.json(objClient); // send client object with accounts
			// objCtx.json(lstAccounts);
		} else {
			objCtx.status(ciStatusCodeErrorBadRequest);
		}
		/*	*/

	};

	// * This requirement is not real clear. I believe they want to get a client's
	// account by account number, if and
	// * only if the account belongs to the client.
	// *### - `GET /clients/{client_id}/accounts/{account_id}`: Get account with id
	// of Y belonging to client with id of
	// * X (if client and account exist AND if account belongs to client)
	private Handler getClientAccountByAccountNumber = (objCtx) -> {
		String sMethod = "getClientAccountByAccountNumber(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");

		String sAccount = objCtx.pathParam(csParamAccount);
		objLogger.debug(sMethod + "Context parameter get account identifier: [" + sAccount + "]");

		String sAccountNumber = objCtx.pathParam(csParamAccountNumber);
		objLogger.debug(sMethod + "Context parameter account number: [" + sAccountNumber + "]");

		// first make sure this is an actual account request
		if (sAccount.equals(csParamAccount)) {

			// second get client from database / make sure they exist
			Client objClient = objClientService.getClientById(sClientId);
			objLogger.debug(sMethod + "Client object from database: [" + objClient.toString() + "]");

			// third get the specific account by number that belongs to this client
			AccountService objAccountService = new AccountService();

			Account objAccount = objAccountService.getAccountByAccountNumberForClientId(objClient.getClientId(),
					sAccountNumber);
			objClient.setAccount(objAccount); // set the account for this client

			objCtx.status(ciStatusCodeSuccess);
			objCtx.json(objClient); // send client object with accounts
			// objCtx.json(lstAccounts);
		} else {
			objCtx.status(ciStatusCodeErrorBadRequest);
		}

	};

	//
	// ### - `POST /clients/{client_id}/accounts`: Create a new account for a client
	// with id of X (if client exists)
	// generate a random number for account number
	private Handler postAddClientAccount = (objCtx) -> {
		String sMethod = "postAddClientAccount(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter " + csParamClientId + ": [" + sClientId + "]");

		String sAccountType = objCtx.pathParam(csParamAccountType);
		objLogger.debug(sMethod + "Context parameter " + csParamAccountType + ": [" + sAccountType + "]");

		String sAccountBalance = objCtx.pathParam(csParamAccountBalance);
		objLogger.debug(sMethod + "Context parameter " + csParamAccountBalance + ": [" + sAccountBalance + "]");

		// First make sure client exists
		Client objClient = objClientService.getClientById(sClientId);
		AccountAddDTO objAccountAddDTO = new AccountAddDTO(sAccountType, sAccountBalance, sClientId);
		objLogger.debug(sMethod + "objAccountAddDTO: [" + objAccountAddDTO.toString() + "]");
		Account objNewAccount = objAccountService.createNewAccountForClient(objClient.getClientId(), objAccountAddDTO);
		objClient.setAccount(objNewAccount);

		objCtx.json(objClient); // return the client with the account added
	};

	//
	// ### - `POST /clients/{client_id}/accounts`: Create a new account for a client
	// with id of X (if client exists)
	// generate a random number for account number
	private Handler postAddClientAccountByBody = (objCtx) -> {
		String sMethod = "postAddClientAccount(): ";
		objLogger.trace(sMethod + "Entered");

		AccountAddDTO objAccountAddDTO = objCtx.bodyAsClass(AccountAddDTO.class);
		objLogger.debug(sMethod + "objAccountAddDTO: [" + objAccountAddDTO.toString() + "]");

		// First make sure client exists
		Client objClient = objClientService.getClientById(objAccountAddDTO.getClientId());

		objLogger.debug(sMethod + "objAccountAddDTO: [" + objAccountAddDTO.toString() + "]");
		Account objNewAccount = objAccountService.createNewAccountForClient(objClient.getClientId(), objAccountAddDTO);
		objClient.setAccount(objNewAccount);

		objCtx.json(objClient); // return the client with the account added

	};

	// - `PUT /clients/{client_id}/accounts/{account_id}`: Update account with id of
	// Y belonging to client with id of
	// X (if client and account exist AND if account belongs to client)
	private Handler putUpdateClientAccount = (objCtx) -> {
		String sMethod = "putUpdateClientAccount(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter " + csParamClientId + ": [" + sClientId + "]");

		String sAccountNumber = objCtx.pathParam(csParamAccountNumber);
		objLogger.debug(sMethod + "Context parameter " + csParamAccountNumber + ": [" + sAccountNumber + "]");

		String sAccountBalance = objCtx.pathParam(csParamAccountBalance);
		objLogger.debug(sMethod + "Context parameter " + csParamAccountBalance + ": [" + sAccountBalance + "]");

		// First make sure client exists
		Client objClient = objClientService.getClientById(sClientId);
		AccountEditDTO objAccountEditDTO = new AccountEditDTO(sAccountNumber, sAccountBalance, sClientId);
		objLogger.debug(sMethod + "objAccountEditDTO: [" + objAccountEditDTO.toString() + "]");
		Account objUpdatedAccount = objAccountService.updateAccountForClient(objClient.getClientId(),
				objAccountEditDTO);
		objClient.setAccount(objUpdatedAccount);

		objCtx.json(objClient); // return the client with the account added

	};

	//
	// ###- `DELETE /clients/{id}`: Delete client with an id of X (if the client
	// exists)
	private Handler deleteClientById = (objCtx) -> {
		String sMethod = "deleteClientById(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");

		// first see if client exists
		objLogger.debug(sMethod + "Checking to see if client id: [" + sClientId + "] is in the database.");
		objClientService.getClientById(sClientId); // exception thrown will prevent rest of this method to complete
		objLogger.debug(sMethod + "Client id: [" + sClientId + "] is in the database.");

		objClientService.deleteClientById(sClientId);
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json("Client with id: [" + sClientId + "] and their accounts removed from database.");

		/*
		 * Due to having orphan client records if there is an database error after
		 * deleting accounts. Have the DAO handle both transactions.
		 * 
		 * // first we must delete all accounts for this client boolean bAccountsDeleted
		 * = objAccountService.deleteAllAccountsForClient(sClientId);
		 * 
		 * if (bAccountsDeleted) { objLogger.debug(sMethod +
		 * "all accounts if any have been deleted from client id: [" + sClientId + "]");
		 * objLogger.debug(sMethod + "Calling to delete from dabase with client id: [" +
		 * sClientId + "]");
		 * 
		 * objClientService.deleteClient(sClientId); objCtx.status(ciStatusCodeSuccess);
		 * objCtx.json("Client with id: [" + sClientId +
		 * "] and their accounts removed from database.");
		 * 
		 * } else { String sMsg = "Error deleting client id: [" + sClientId +
		 * "] from data base.  Issue deleting thier accounts."; objLogger.debug(sMethod
		 * + sMsg); objCtx.status(ciStatusCodeSuccess); objCtx.json(sMsg); }
		 */

	};

	//
	// ### - `DELETE /clients/{client_id}/accounts/{account_id}`: Delete account
	// with id of Y belonging to
	// client with id of X (if client and account exist AND if account belongs to
	// client)
	private Handler deleteAccountForClientId = (objCtx) -> {
		String sMethod = "deleteAccountForClientId(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String, String> mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csParamClientId);
		objLogger.debug(sMethod + "Context parameter client id: [" + sClientId + "]");

		String sAccount = objCtx.pathParam(csParamAccount);
		objLogger.debug(sMethod + "Context parameter client id: [" + sAccount + "]");

		String sAccountNumber = objCtx.pathParam(csParamAccountNumber);
		objLogger.debug(sMethod + "Context parameter client id: [" + sAccountNumber + "]");

		// First make sure client exists, if not an exception will be thrown
		objLogger.debug(sMethod + "Checking if client id: [" + sClientId + "] exists in database.");
		Client objClient = objClientService.getClientById(sClientId);
		objLogger.debug(sMethod + "Client exists: [" + objClient.toString() + "]");

		objLogger.debug(
				sMethod + "Attempting to delete account: [" + sAccountNumber + "] for client id: [" + sClientId + "]");
		int iDeleteStatus = objAccountService.deleteAccountForClient(objClient.getClientId(), sAccountNumber);

		String sMsg = "";

		switch (iDeleteStatus) {
		case ciDelAcctSuccess: {
			sMsg = "Account number: [" + sAccountNumber + "] for Client with id: [" + sClientId
					+ "] removed from database.";
			objLogger.debug(sMethod + sMsg);
			break;
		}
		case ciDelAcctRecordNotFound: {
			sMsg = "Account number: [" + sAccountNumber + "] for Client with id: [" + sClientId
					+ "] was not found in the database.";
			objLogger.debug(sMethod + sMsg);
			break;
		}
		case ciDelAccountNotClients: {
			sMsg = "Account number: [" + sAccountNumber + "] does not belong to Client with id: [" + sClientId + "]";
			objLogger.debug(sMethod + sMsg);
			break;

		}
		default:
			sMsg = "Error attempting to delete ccount number: [" + sAccountNumber + "] for Client with id: ["
					+ sClientId + "] from the database.";
			objLogger.debug(sMethod + sMsg);
			break;

		}

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(sMsg);

	};

	@Override
	public void mapEndpoints(Javalin app) {
		String sMethod = "mapEndpoints(): ";
		objLogger.trace(sMethod + "Entered");

		/**
		 * CLARIFICATION DURING CLASS ON THE DAY BEFORE THIS WAS DUE IS TO USE THE
		 * ENDPOINT AS DEFINED AND TRY AND DETERMINE PROCESSING BY THE PARAMETERS
		 * 
		 * To resolve a conflict with the two put request, create signatures for request
		 * on a client(s) and request for client accounts (client_acct).
		 */

		// `GET /clients`: Gets all clients ==> requirement did not specifically state
		// to include accounts or not. I can see a use for just the Clients without
		// their
		// accounts so have one endpoint for just clients and another for with accounts.
		// 000.00 GET Get all clients request VALID request /clients
		// http://localhost:3005/clients
		app.get("/clients", getAllClients);

		// `NOT an MVP item: GET /clients/accounts`: Gets all clients with their
		// accounts
		// 005.00 GET Get all clients with thier account information BYPASS
		// http://localhost:3005/clients_accts
		app.get("/clients_accts", getAllClientsWithAccountsByPass); // first implementation

		//
		// 006.00 GET Gets all clients with their account information VALID request
		// http://localhost:3005/clients/accounts
		app.get("/clients/accounts", getAllClientsWithAccounts); // per endpoint requirement

		// - `GET /clients/{client_id}`: Get client with an id of X (if the client
		// exists)
		// 010.00 GET Get a client by VALID client id without accounts
		// http://localhost:3005/clients/4
		String sEndPoint = "app.get(\"/clients/:" + csParamClientId + "\", getClientById);";
		objLogger.debug(sMethod + sEndPoint);

		// app.get("/clients/:client_id", getClientById);
		app.get("/clients/:" + csParamClientId, getClientById); // per endpoint requirement

		// - `GET /clients/{client_id}/accounts`: Get all accounts for client with id of
		// X (if client exists)
		sEndPoint = "app.get(\"/clients/:" + csParamClientId + "/:" + csParamAccounts + "\", getClientAccounts);";
		objLogger.debug(sMethod + sEndPoint);

		// app.get("/clients/:client_id/:accounts", getClientAccounts);
		// getClientAccounts() method needs to check for queryParam to determine if
		// constraints on
		// client account records to return to meet this requirement:
		// - `GET /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400`:
		// Get all accounts for client id of X with balances between 400 and 2000 (if client exists)
		sEndPoint = "app.get(\"/clients/:" + csParamClientId + "/:" + csParamAccounts + "\", getClientAccounts);";
		objLogger.debug(sMethod + sEndPoint);
		
		// 015.00 GET Get a client by VALID client id with accounts
		// http://localhost:3005/clients/4/accounts
		// 021.00 GET Get client accounts with VALID client id between a VALID range
		// http://localhost:3005/clients/1/accounts?acct_greater_than=400&acct_less_than=2000
		//app.get("/clients/:client_id/:accounts", getClientAccounts);
		app.get("/clients/:" + csParamClientId + "/:" + csParamAccounts, getClientAccounts); // per endpoint requirement

		// - `GET
		// /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400`:
		// Get all accounts for client id of X with balances between 400 and 2000 (if
		// client exists)
		// 020.00 GET client accounts with VALID client id between a range
		// http://localhost:3005/client_acct/1/accounts/2000/400
		sEndPoint = "app.get(\"/client_acct/:" + csParamClientId + "/:" + csParamAccounts + "/:"
				+ csParamAccountsLessThan + "/:" + csParamAccountsGreaterThan + "\", getClientAccountsInRangeByPass);";
		objLogger.debug(sMethod + sEndPoint);
		// app.get("/client_acct/:client_id/:accounts/:acct_less_than/:acct_greater_than",
		// getClientAccountsInRange);
		//
		app.get("/client_acct/:" + csParamClientId + "/:" + csParamAccounts + "/:" + csParamAccountsLessThan + "/:"
				+ csParamAccountsGreaterThan, getClientAccountsInRangeByPass);

		// * This requirement is not real clear. I believe they want to get a client's
		// account by account number, if and
		// * only if the account belongs to the client.
		// * - `GET /clients/{client_id}/accounts/{account_id}`: Get account with id of
		// Y belonging to client with id of
		// * X (if client and account exist AND if account belongs to client)		
		sEndPoint = "app.get(\"/clients/:" + csParamClientId + "/:" + csParamAccounts 
						+ "/:" + csParamAccountNumber
						+ "\", getClientAccountByAccountNumber);";
		objLogger.debug(sMethod + sEndPoint);

		// 025.00 GET Get client account with VALID client id and a VALID account number that belongs to the client
		// http://localhost:3005/client_acct/1/account/00002
		// app.get("/client_acct/:client_id/:accounts/:acct_number", getClientAccountByAccountNumber);
		app.get("/clients/:" + csParamClientId + "/:" + csParamAccount + "/:" + csParamAccountNumber,
				getClientAccountByAccountNumber); // per endpoint requirement

		// - `POST /clients`: Creates a new client
		// http://localhost:3005/client/Michael/Biehn/Kyle Reese
		sEndPoint = "app.post(\"/clients/:" + csClientTblFirstName + "/:" + csClientTblLastName + "/:"
				+ csClientTblNickname + "\", postAddClient);";
		objLogger.debug(sMethod + sEndPoint);

		//000.00 POST Add new client all parameters VALID
		// app.post("/client/:client_first_name/:client_last_name/:client_nickname,
		// postAddClient);
		app.post("/clients/:" + csClientTblFirstName + "/:" + csClientTblLastName + "/:" + csClientTblNickname,
				postAddClient); // per endpoint requirement
		
		// 001.00 POST Add new client VALID first & last name but no optional nickname
		// /clients/client_first_name/client_last_name
		//Client nickname is optionial and is not identified if left blank in the parameter map so create new endpoint
		app.post("/clients/:" + csClientTblFirstName + "/:" + csClientTblLastName, postAddClient); // per endpoint requirement

		// add.post add a client using body for greater security
		app.post("/clients", postAddClientByBody);

		// - `POST /clients/{client_id}/accounts`: Create a new account for a client
		// with id of X (if client exists)
		// 005.00 POST client add an account
		// /client_acct/client_id/account/account_type/account_balance
		// http://localhost:3005/client_acct/5/account/Checking/10000.23 ==> generated
		// number: [84073]
		// generate a random number for account number
		sEndPoint = "app.post(\"/client_acct/:" + csParamClientId + "/:" + csParamAccounts + "/:" + csParamAccountType
				+ "/:" + csParamAccountBalance + "\", postAddClientAccount);";
		objLogger.debug(sMethod + sEndPoint);

		// app.post("/client_acct/:client_id/:accounts/:acct_type/:acct_balance",
		// postAddClientAccount);
		app.post("/client_acct/:" + csParamClientId + "/:" + csParamAccount // account number is system generated
				+ "/:" + csParamAccountType // can set account type
				+ "/:" + csParamAccountBalance, // can create a balance
				postAddClientAccount);

		// add.post add an account for client using body for greater security
		app.post("/client_acct", postAddClientAccountByBody);

		// - `PUT /clients/{id}`: Update client with an id of X (if the client exists)
		// 000.00 PUT update client information
		// http://localhost:3005/client/5/Michael/Biehn/John Connor Kyle Reese is your
		// father
		sEndPoint = "app.put(\"/client/:" + csParamClientId + "/:" + csClientTblFirstName + "/:" + csClientTblLastName
				+ "/:" + csClientTblNickname + "\", putUpdateClient);";
		objLogger.debug(sMethod + sEndPoint);

		// app.get("/client/:client_id/:client_first_name/:client_last_name/:client_nickname",
		// putUpdateClient);
		app.put("/client/:" + csParamClientId + "/:" + csClientTblFirstName + "/:" + csClientTblLastName + "/:"
				+ csClientTblNickname, putUpdateClient);

		// There is a conflict with PUT to update a client and PUT to update a client
		// account
		// both signatures are the similar and update a client is called first.
		// So introducing a client_acct signature to use on account related calls.

		// - `PUT /clients/{client_id}/accounts/{account_id}`: Update account with id of
		// Y belonging to client with id of
		// X (if client and account exist AND if account belongs to client)
		// 005.00 PUT Update an account for a given client
		// /client_acct/client_id/account/account number/account balance
		// http://localhost:3005/client_acct/5/account/84073/50655.23
		sEndPoint = "app.put(\"/client_acct/:" + csParamClientId + "/:" + csParamAccount + "/:" + csParamAccountNumber
				+ "/:" + csParamAccountBalance + "\", putUpdateClientAccount);";
		objLogger.debug(sMethod + sEndPoint);

		// app.get("/client_acct/:client_id/:account/:acct_number/:acct_balance",
		// putUpdateClientAccount);
		app.put("/client_acct/:" + csParamClientId // cannot update client id
				+ "/:" + csParamAccount // identifies this is an account action
				+ "/:" + csParamAccountNumber // cannot update account number or account type
				+ "/:" + csParamAccountBalance, // can only update balance and really should be deposit or withdrawal
												// for now will just implement changing the balance
				putUpdateClientAccount);

		// - `DELETE /clients/{id}`: Delete client with an id of X (if the client
		// exists)
		// 000.00 DELETE Delete client with VALID id with account (s)
		// http://localhost:3005/client/5
		app.delete("/client/:" + csParamClientId, deleteClientById);

		// - `DELETE /clients/{client_id}/accounts/{account_id}`: Delete account with id
		// of Y belonging to
		// client with id of X (if client and account exist AND if account belongs to
		// client)
		// 005.00 DELETE /client: Delete VALID Account for VALID client id, where
		// account belongs to client.
		// /client_acct/client_id/account/account number
		// http://localhost:3005/client_acct/2/account/00014
		sEndPoint = "app.delete(\"/client_acct/:" + csParamClientId + "/:" + csParamAccount + "/:"
				+ csParamAccountNumber + "\", deleteAccountForClientId);";
		objLogger.debug(sMethod + sEndPoint);

		// app.get("/client_acct/:client_id/:account/:acct_number",
		// deleteAccountForClientId);
		app.delete("/client_acct/:" + csParamClientId // client id
				+ "/:" + csParamAccount // identifies this is an account action
				+ "/:" + csParamAccountNumber, // account number to delete
				deleteAccountForClientId);

	}

}
