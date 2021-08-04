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

	//context parameters
	String csClientname = "clientname";
	
	//
	int ciMinNameLen = 5;
	int ciListingPort = 3005;
	
	//database constants 
	String csDatabaseName = "project0";							//database name
	String csClientTable = csDatabaseName + "." + "client";		//client table
//	String csPhoneTable = csDatabaseName + "." + "phone";		//phone table
	String csAccountTable = csDatabaseName + "." + "account";	//account table
	
	//client table constants these must match the table attributes
	String csClientTblClientId = "client_id";
	String csClientTblFirstName = "client_first_name";
	String csClientTblLastName = "client_last_name";
	String csClientTblNickname = "client_nickname";
	
	//account table constants these must match the table attributes
	String csAccountTblAccountNumber = "acct_number";
	int ciAccountTblAccountNumberLen = 5;
	String csAccountTblAccountName = "acct_name";
	String csAccountTblAccountBalance = "acct_balance";
	
	//HTTP request parameter constants
	String csParamClientId = csClientTblClientId; 
	String csParamAccounts = "accounts";	//request parameter used in conjunction with id to get all accounts
	String csParamAccountNumber = csAccountTblAccountNumber;
	String csParamAccountsLessThan = "acct_less_than";
	String csParamAccountsGreaterThan = "acct_greater_than";


}













