package com.tlw8253.controller;

import java.util.List;

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

	
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/accounts", getAllAccounts);	//Not a MVP item
		
	}

	
}
