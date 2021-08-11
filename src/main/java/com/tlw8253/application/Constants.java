package com.tlw8253.application;

/**
 * Implementing class Constants as an interface allows the class to 
 * use defined variable direction without using qualifying notation: Constants.varName;
 * 
 * Classes not implementing class Constants can still access variable using qualifying notation: Constants.varName;
 * 
 * @author tlw8748253
 *
 */

/*
 * Interface methods are by default abstract and public
 * Interface attributes are by default public, static and final
 */
public interface Constants {

	//Return status codes
	//	source: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status
	int ciStatusCodeSuccess = 200;				//The request has succeeded. 
	int ciStatusCodeSuccessCreated = 201;		//The request has succeeded and a new resource has been created as a result. 
	int ciStatusCodeSuccessNoContent = 204;		//There is no content to send for this request,

	int ciStatusCodeErrorBadRequest = 400;		//The server could not understand the request due to invalid syntax.
	int ciStatusCodeNotFound = 404;				//The server can not find the requested resource. In the browser.

	int ciStatusCodeInternalServerError = 500;	//The server has encountered a situation it doesn't know how to handle.
	
	//context parameters
	String csClientname = "clientname";
	
	//
	int ciMinNameLen = 5;
	int ciListingPort = 3005;
	
	//processing status code
	int ciDelAcctSuccess = 10;
	int ciDelAcctRecordNotFound = 20;
	int ciDelDB_Error = 30;
	int ciDelAccountNotClients = 40;
	
	//database constants 
	String csDatabaseName = "project0";							//database name
	String csClientTable = csDatabaseName + "." + "client";		//client table project0.client
	String csAccountTable = csDatabaseName + "." + "account";	//account table
	
	//client table constants these must match the table attributes
	String csClientTblClientId = "client_id";
	String csClientTblFirstName = "client_first_name";
	String csClientTblLastName = "client_last_name";
	String csClientTblNickname = "client_nickname";
	
	//account table constants these must match the table attributes
	String csAccountTblAccountNumber = "acct_number";
	int ciAccountTblAccountNumberLen = 5;
	String csAccountTblAccountType = "acct_type";
	String csAccountTblAccountBalance = "acct_balance";
	String csAccountTblClientId = csClientTblClientId;
	String csAccountTypeValueChecking = "CHECKING";
	String csAccountTypeValueSavings = "SAVINGS";
	int ciAccountNumMinVal = 1;
	int ciAccountNumMaxVal = 99999;
	
	//HTTP request parameter constants
	String csParamClientId = csClientTblClientId; 
	String csParamClientFirstName = csClientTblFirstName;
	String csParamClientLastName = csClientTblLastName;
	String csParamClientNickname = csClientTblNickname;
	
	String csParamAccounts = "accounts";	//request parameter used in conjunction with id to get all accounts
	String csParamAccount = "account";	//request parameter used in conjunction with id to get a specific account
	String csParamAccountNumber = csAccountTblAccountNumber;
	String csParamAccountType = csAccountTblAccountType;
	String csParamAccountBalance = csAccountTblAccountBalance;
	String csParamAccountsLessThan = "acct_less_than";
	String csParamAccountsGreaterThan = "acct_greater_than";


	//Define program messages to use in the program and for testing
	String csMsgDB_ErrorGettingAllClients = "Error with database getting all clients.";
	String csMsgDB_ErrorGettingByClientId = "Database error getting the client by id.";
	String csMsgDB_ErrorAddingClient = "Database error when adding a client.";
	String csMsgDB_ErrorUpdatingClient = "Database error when updating client information.";
	String csMsgDB_ErrorDeletingClient = "Database error when deleting client information.";
	String csMsgDB_ErrorAddingAccountForClient = "Database error while adding a new account for a client.";
	String csMsgDB_ErrorDeletingAccountForClient = "Database error while deleteing client's account.";
	String csMsgDB_ErrorUpdatingAccountForClient = "Database error while updating an existing account for a client.";
	String csMsgDB_ErrorGettingAccount = "Database error while getting an account.";
	
	String csMsgBadParamClientId = "Client Id must be a number.";
	String csMsgBadParamClientName = "Client first and last name must contain values.";
	String csMsgBadParamNotInts = "One or more parameters are not numbers.";
	String csMsgBadParamAcctNumLen = "Account number length is invalid must be " + ciAccountTblAccountNumberLen + " in length.";
	String csMsgBadParamAcctNumNotNumber = "Account number must be a number.";
	String csMsgBadParamAccountTypes = "Valid account types are: " + csAccountTypeValueChecking + " or " + csAccountTypeValueSavings;
	String csMsgBadParmAccountBalance = "Account balance did not tranlate to numeric value.";

	String csMsgClientNotFound = "Client was not found in the database.";
	String csMsgAccountNotFound = "Account not found in the database for the account number.";
	String csMsgAccountNotFoundForClient = "Account not found in the database for the client.";
	String csMsgAccountsNotFoundForClient = "Accounts were not found in the database for the client.";
	String csMsgAcctDoesNotBelongToClient = "Account number not assigned to this client.";

}













