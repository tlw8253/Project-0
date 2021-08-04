package com.tlw8253.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Constants;
import com.tlw8253.model.Account;
import com.tlw8253.service.AccountService;
import com.tlw8253.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller, Constants{
	private Logger objLogger = LoggerFactory.getLogger(AccountController.class);
	private AccountService objAccountService;

	public AccountController() {
		// this.objClientService = new ClientService();
		this.objAccountService = new AccountService();
	}

	
	//
	//### 
	private Handler getAllAccounts = (objCtx) -> {	
		String sMethod = "getAllAccounts(): ";
		objLogger.trace(sMethod + "Entered");

		List<Account> lstAccount = objAccountService.getAllAccounts();
		
		objLogger.debug(sMethod + "lstAccount: [" + lstAccount.toString() + "]");
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstAccount);
	};


	//
	//### `GET /account/{acct_number}`: Get account with an id of X (if the account exists)
	private Handler getAccountByAccountNumber = (objCtx) -> {	
		String sMethod = "getAccountByAccountNumber(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String,String> mPathParmaMap =  objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sAccountNumber = objCtx.pathParam(csParamAccountNumber);
		objLogger.debug(sMethod + "Context parameter account identifier: [" + sAccountNumber + "]");		

		Account objAccount = objAccountService.getAccountByAccountNumber(sAccountNumber);
				
		objLogger.debug(sMethod + "objAccount: [" + objAccount.toString() + "]");
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objAccount);
	};
	
	//
	//### `GET /accounts/{client_id}`: Get all accounts for a given client
	private Handler getAccountsForClient = (objCtx) -> {	
		String sMethod = "getAccountsForClient(): ";
		objLogger.trace(sMethod + "Entered");

		Map<String,String> mPathParmaMap =  objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context parameter map: [" + mPathParmaMap + "]");

		String sClientId = objCtx.pathParam(csClientTblClientId);
		objLogger.debug(sMethod + "Context parameter client identifier: [" + sClientId + "]");		

		List<Account> lstAccount = objAccountService.getAccountsForClient(sClientId);
				
		objLogger.debug(sMethod + "lstAccount: [" + lstAccount.toString() + "]");
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstAccount);
	};


	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/accounts", getAllAccounts);	//Not a MVP item
		app.get("/account/:" + csParamAccountNumber, getAccountByAccountNumber);	//Not a MVP item
		app.get("/accounts/:" + csParamClientId, getAccountsForClient);	//Not a MVP item
		app.get("/accounts/:" + csParamClientId + "/:", getAccountsForClient);	//Not a MVP item
		
	}

	
}



















