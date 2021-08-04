package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.exception.DatabaseException;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.dao.AccountDAOImpl;
import com.tlw8253.dao.ClientDAO;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.exception.AccountNotFoundException;
//import com.tlw8253.dao.AccountDAO;
//import com.tlw8253.dao.AccountDAOImpl;
//import com.tlw8253.dto.AddOrEditAccountDTO;
import com.tlw8253.model.Account;

public class AccountService {
	private Logger objLogger = LoggerFactory.getLogger(AccountService.class);
	
	private GenericDAO<Account> objAccountDAO;

	public AccountService() {
		this.objAccountDAO = new AccountDAOImpl();
	}
	
/*
	// Fake Client object used for testing
	public ClientService(ClientDAO objMockedClientDAO) {
		this.objClientDAO = objMockedClientDAO;
	}
*/
	
	public List<Account> getAllAccounts() throws DatabaseException {
		String sMethod = "getAllAccounts(): ";
		List<Account> lstAccounts;
		try {
			lstAccounts = objAccountDAO.getAllRecords();

		} catch (SQLException objE) {
			String sMsg = sMethod + "Error with database getting all accounts.";
			objLogger.error(sMsg);
			throw new DatabaseException(sMsg);
		}
		return (lstAccounts);
		
	}
	
	/*
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
*/
	


}
