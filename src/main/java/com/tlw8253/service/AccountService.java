package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.exception.DatabaseException;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.application.Constants;
import com.tlw8253.dao.AccountDAOImpl;
import com.tlw8253.dto.AccountAddDTO;
import com.tlw8253.dto.AccountEditDTO;
import com.tlw8253.exception.AccountNotFoundException;
import com.tlw8253.model.Account;
import com.tlw8253.util.Utility;
import com.tlw8253.util.Validate;

/**
 * AccountService is used for data validation prior to adding or updating
 * elements in the database.
 * 
 * @author tlw8748253
 *
 */
public class AccountService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(AccountService.class);

	// private GenericDAO<Account> objAccountDAO;
	private AccountDAOImpl objAccountDAO;

	public AccountService() {
		this.objAccountDAO = new AccountDAOImpl();
	}

	/*
	 * // Fake Client object used for testing public ClientService(ClientDAO
	 * objMockedClientDAO) { this.objClientDAO = objMockedClientDAO; }
	 */

	//
	// ###
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

	//
	// ###
	public Account getAccountByAccountNumber(String sAccountNumber)
			throws DatabaseException, AccountNotFoundException, BadParameterException {
		String sMethod = "getAccountByAccountNumber(String): ";

		Account objAccount;

		// database does not store leading unless sent in as a string for the varchar
		// changed the initial data load to insert as string keeping leading zeros
		int iLen = sAccountNumber.length();

		if (iLen != ciAccountTblAccountNumberLen) {
			String sMsg = "Account Number has unexpected length of: [" + iLen + "].  Should have length of: ["
					+ ciAccountTblAccountNumberLen + "].";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParamAcctNumLen);
		}

		// account number should also be an int
		if (!Validate.isInt(sAccountNumber)) {
			String sMsg = "Account Number does not parse to int: [" + sAccountNumber + "].";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParamAcctNumNotNumber);
		}

		try {
			objAccount = objAccountDAO.getByRecordIdentifer(sAccountNumber);
			if (objAccount == null) {
				objLogger.debug(sMethod + "Account number: [" + sAccountNumber + "] not found in database.");
				throw new AccountNotFoundException(csMsgAccountNotFoundForClient);
			} // else objAccount is returned

		} catch (SQLException objE) {
			String sMsg = "Error with database getting account with identifier: [" + sAccountNumber + "]";
			objLogger.error(sMethod + sMsg);
			throw new DatabaseException(csMsgDB_ErrorGettingAccount);
		}
		return (objAccount);
	}

	//
	// ###
	public Account getAccountByAccountNumberForClientId(int iClientId, String sAccountNumber)
			throws DatabaseException, AccountNotFoundException, BadParameterException {
		String sMethod = "getAccountByAccountNumberForClientId(): ";

		// just call getAccountByAccountNumber so it can do the account validation.
		// then if record is found make sure it belongs to this client

		Account objAccount = getAccountByAccountNumber(sAccountNumber);
		int iAcctClientId = objAccount.getClientId();
		if (iAcctClientId != iClientId) {
			String sMsg = "Account with number: [" + sAccountNumber + "] found but belongs to another client: ["
					+ iAcctClientId + "] not this client: [" + iClientId + "]";
			objLogger.debug(sMethod + sMsg);
			throw new AccountNotFoundException(csMsgAcctDoesNotBelongToClient);
		}
		return (objAccount);
	}

	//
	// ### overload the getAccountsForClient() methods to use String or int input
	// depending on the caller.
	// If called directly from a HTTP GET ACCOUNT request, the String parameter
	// should be used
	// If called from a HTTP GET CLIENT request the int parameter can be used
	public List<Account> getAccountsForClient(String sClientId)
			throws DatabaseException, AccountNotFoundException, BadParameterException {
		String sMethod = "getAccountsForClient(String): ";

		objLogger.trace(sMethod + "Entered.");

		List<Account> lstAccounts;
		if (Validate.isInt(sClientId)) {
			objLogger.debug(sMethod + "parseInt client id input paramenter: [" + sClientId + "]");
			int iClientId = Integer.parseInt(sClientId);

			try {
				lstAccounts = getAccountsForClient(iClientId);
				return (lstAccounts);
			} catch (AccountNotFoundException objE) {
				objLogger.debug(sMethod + "No accounts found for client id: [" + sClientId + "]");
				throw new AccountNotFoundException(csMsgAccountsNotFoundForClient);
			}

		} else {
			String sMsg = "Error converting client id to int: [" + sClientId + "].";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParamClientId);
		}
	}

	//
	// ###
	public List<Account> getAccountsForClient(int iClientId) throws DatabaseException, AccountNotFoundException {
		String sMethod = "getAccountsForClient(int): ";
		List<Account> lstAccounts;

		objLogger.trace(sMethod + "Entered.");

		try {
			objLogger.debug(sMethod + "Getting all accounts for client id: [" + iClientId + "]");
			lstAccounts = objAccountDAO.getAccountsForClient(iClientId);

			if (lstAccounts.size() > 0) {
				objLogger.debug(
						sMethod + "Accounts for client id: [" + iClientId + "] [" + lstAccounts.toString() + "]");
			} else {
				String sMsg = "No accounts found for client id: [" + iClientId + "].";
				objLogger.error(sMethod + sMsg);
				throw new AccountNotFoundException(sMsg);
			}
		} catch (SQLException objE) {
			String sMsg = "Error with database getting all accounts for client id: [" + iClientId + "].";
			objLogger.warn(sMethod + sMsg);
			throw new DatabaseException(csMsgAccountsNotFoundForClient);
		}
		return (lstAccounts);

	}

	//
	// ###
	public List<Account> getAccountsForClientInRange(String sClientId, String sUpperRange, String sLowerRange)
			throws DatabaseException, AccountNotFoundException, BadParameterException {
		String sMethod = "getAccountsForClientInRange(String): ";

		List<Account> lstAccounts;
		if (Validate.isInt(sClientId) && Validate.isInt(sUpperRange) && Validate.isInt(sLowerRange)) {
			objLogger.debug(sMethod + "parseInt input paramenters: sClientId: [" + sClientId + "] sUpperRange: ["
					+ sUpperRange + "] sLowerRange: [" + sLowerRange + "]");

			int iClientId = Integer.parseInt(sClientId);
			int iUpperRange = Integer.parseInt(sUpperRange);
			int iLowerRange = Integer.parseInt(sLowerRange);

			try {// need to catch what the called method throws or could cause internal server
					// error
				lstAccounts = getAccountsForClientInRange(iClientId, iUpperRange, iLowerRange);
			} catch (AccountNotFoundException objE) {
				objLogger.debug(sMethod + objE.getMessage());
				throw new DatabaseException(csMsgAccountsNotFoundForClient);
			} catch (DatabaseException objE) {
				objLogger.debug(sMethod + objE.getMessage());
				throw new DatabaseException(csMsgAccountsNotFoundForClient);
			}

		} else {
			String sMsg = "Error converting input to ints: sClientId: [" + sClientId + "] sUpperRange: [" + sUpperRange
					+ "] sLowerRange: [" + sLowerRange + "]";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParamNotInts);
		}
		return (lstAccounts);
	}

	//
	// ###
	public List<Account> getAccountsForClientInRange(int iClientId, int iUpperRange, int iLowerRange)
			throws DatabaseException, AccountNotFoundException {
		String sMethod = "getAccountsForClientInRange(int): ";
		List<Account> lstAccounts;
		try {
			objLogger.debug(sMethod + "Getting accounts in range for: iClientId: [" + iClientId + "] iUpperRange: ["
					+ iUpperRange + "] iLowerRange: [" + iLowerRange + "]");

			lstAccounts = objAccountDAO.getAccountsForClientInRange(iClientId, iUpperRange, iLowerRange);

			if (lstAccounts.size() > 0) {
				objLogger.debug(
						sMethod + "Accounts for client id: [" + iClientId + "] [" + lstAccounts.toString() + "]");
			} else {
				String sMsg = "No accounts found for client id: [" + iClientId + "].";
				objLogger.error(sMethod + sMsg);
				throw new AccountNotFoundException(sMsg);
			}
		} catch (SQLException objE) {
			String sMsg = "Error with database getting accounts in range for client id: [" + iClientId
					+ "] iUpperRange: [" + iUpperRange + "]  iLowerRange: [" + iLowerRange + "]";
			objLogger.error(sMethod + sMsg);
			throw new DatabaseException(csMsgAccountsNotFoundForClient);
		}
		return (lstAccounts);

	}

	//
	// ### validate client id prior to calling add new method
	public Account addAccountForClient(AccountAddDTO objAccountAddDTO) throws DatabaseException, BadParameterException {
		String sMethod = "addAccountForClient(): ";
		String sClientId = objAccountAddDTO.getClientId();
		Account objAccountAdded;

		if (Validate.isInt(sClientId)) {
			int iClientId = Integer.parseInt(sClientId);
			objAccountAdded = createNewAccountForClient(iClientId, objAccountAddDTO);

		} else {
			objLogger.error(sMethod + "Client id: [" + sClientId + "] is not an int.");
			throw new BadParameterException(csMsgBadParamClientId);
		}
		return objAccountAdded;
	}

	//
	// ### Assume caller has validated client id.
	// validate account type and balance for format and value
	// generate random account number and check if it exists
	public Account createNewAccountForClient(int iClientId, AccountAddDTO objAccountAddDTO)
			throws BadParameterException, DatabaseException {
		String sMethod = "createNewAccountForClient(): ";
		Account objNewAccount = new Account();

		objLogger.trace(sMethod + "Entered.");

		String sAccountType = objAccountAddDTO.getAccountType();
		String sAccountBalance = objAccountAddDTO.getAccountBalance();

		objLogger.debug(sMethod + "Parameters from DTO: [" + objAccountAddDTO.toString() + "]");

		if ((csAccountTypeValueChecking.equalsIgnoreCase(sAccountType))
				|| (csAccountTypeValueSavings.equalsIgnoreCase(sAccountType))) {

			if (Validate.isDouble(sAccountBalance)) {

				objLogger.debug(sMethod + "Parameters from DTO validated.  Calling method to generate account number.");

				String sAccountNumber = generateAccountNumber();

				objLogger.debug(sMethod + "Account number generated: [" + sAccountNumber + "]");

				// have account number not in database so use to add record.
				objAccountAddDTO.setClientId(Integer.toString(iClientId)); // caller valided id is int
				objAccountAddDTO.setClientIdAsInt(iClientId); // set client id as int
				objAccountAddDTO.setAccountNumber(sAccountNumber); // validated to be new account number
				objAccountAddDTO.setAccountType(sAccountType);
				objAccountAddDTO.setAccountBalance(sAccountBalance); // already validated to be a double
				// now add the record
				objAccountAddDTO.setAccountBalanceAsDouble(Double.parseDouble(sAccountBalance)); // set double value

				objLogger.debug(sMethod + "Updated objAccountAddDTO: [" + objAccountAddDTO.toString() + "]");

				// Attempt adding a new account for the client
				try {

					objLogger.debug(sMethod + "Attempting to add account with number: [" + sAccountNumber + "]");
					// Client objAddedClient = objClientDAO.addClient(objAddClientDTO);
					objNewAccount = objAccountDAO.addRecord(objAccountAddDTO);
					objLogger.debug(sMethod + "Account object created: [" + objNewAccount.toString() + "]");
					// return objAccountAdded;

				} catch (SQLException objE) {
					String sMsg = "Database error adding account for client id: [" + iClientId + "]"
							+ " Account information: [" + objAccountAddDTO.toString() + "]";
					objLogger.error(sMethod + sMsg + "[" + objE.getMessage() + "]");
					throw new DatabaseException(csMsgDB_ErrorAddingAccountForClient);
				}

			} else {
				String sMsg = "Invalid balance parameter: [" + sAccountBalance + "] not a double.";
				objLogger.debug(sMethod + sMsg);
				throw new BadParameterException(csMsgBadParmAccountBalance);
			}

		} else {
			String sMsg = "Account type is either: [" + csAccountTypeValueChecking + "] or: ["
					+ csAccountTypeValueSavings + "] not: [" + sAccountType + "]";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParamAccountTypes);
		}

		objLogger.debug(sMethod + "objNewAccount: [" + objNewAccount.toString() + "]");
		return objNewAccount;
	}

	//
	// ###
	private String generateAccountNumber() throws DatabaseException {
		String sMethod = "generateAccountNumber";
		boolean bAccountExists = true;
		String sAccountNumber = "";

		objLogger.trace(sMethod + "Entered.");

		do {
			// get a random generated account number then check if it already exists
			// an after thought, could store the last number inserted as account number
			// in the database, then get it and increment.
			int iAccountNumber = Utility.getRandomIntBetween(ciAccountNumMinVal, ciAccountNumMaxVal);
			sAccountNumber = Integer.toString(iAccountNumber);

			objLogger.debug(sMethod + "Account number generated: [" + sAccountNumber + "]");

			if (sAccountNumber.length() < ciAccountTblAccountNumberLen) {
				sAccountNumber = Utility.padIntLeadingZero(iAccountNumber, ciAccountTblAccountNumberLen);
			}

			try {
				objLogger.debug(sMethod + "checking if account number: [" + sAccountNumber + "] already exists.");
				// check if account number already exists
				bAccountExists = doesAccountExist(sAccountNumber);
				objLogger.debug(sMethod + "results if account number: [" + sAccountNumber + "] exists: ["
						+ bAccountExists + "]");

			} catch (AccountNotFoundException objE) {
				objLogger.debug(sMethod + "caught AccountNotFoundException: [" + objE + "] returning account number: ["
						+ sAccountNumber + "]");
				bAccountExists = false;
				return (sAccountNumber);

			} catch (DatabaseException objE) {
				// any kind of exception here is a problem, throw it to caller.
				String sMsg = "Exception occurred while generating an account number";
				objLogger.debug(sMethod + sMsg);
				throw new DatabaseException(csMsgDB_ErrorAddingAccountForClient);
			}

		} while (bAccountExists);// while random generated account number is an account, try again

		return sAccountNumber;
	}

	//
	// ### Assume caller has validated client id.
	// Only the account balance can be updated at this time
	// Validate account balance is a double
	// Validate that account exists and belongs to this client
	// Update the account balance
	public Account updateAccountForClient(int iClientId, AccountEditDTO objAccountEditDTO)
			throws BadParameterException, DatabaseException, AccountNotFoundException {
		String sMethod = "updateAccountForClient(): ";
		Account objUpdatedAccount = new Account();

		String sAccountBalance = objAccountEditDTO.getAccountBalance();
		String sAccountNumber = objAccountEditDTO.getAccountNumber();

		if (Validate.isDouble(sAccountBalance)) {

			try {

				Account objAccountFound = getAccountByAccountNumber(objAccountEditDTO.getAccountNumber());
				int iClientIdAccountFound = objAccountFound.getClientId();
				if (iClientId == iClientIdAccountFound) {
					// update the account information
					objAccountEditDTO.setClientId(Integer.toString(iClientId)); // caller valided id is int
					objAccountEditDTO.setAccountNumber(sAccountNumber); // validated to be new account number
					objAccountEditDTO.setAccountType(objAccountFound.getAccountType());
					objAccountEditDTO.setAccountBalance(sAccountBalance); // already validated to be a double
					// now add the record
					objUpdatedAccount = objAccountDAO.updateAccountForClient(objAccountEditDTO);

				} else {

					String sMsg = "Account account number: [" + sAccountNumber
							+ "] does not belong to this client id: [" + iClientId + "]";
					objLogger.debug(sMethod + sMsg);
					throw new AccountNotFoundException(csMsgAccountNotFoundForClient);
				}

			} catch (AccountNotFoundException objE) {
				String sMsg = "Account not found for account number: [" + sAccountNumber + "].";
				objLogger.debug(sMethod + sMsg);
				throw new AccountNotFoundException(csMsgAccountNotFoundForClient);
			} catch (SQLException objE) {
				String sMsg = "Error updating account with account number: [" + sAccountNumber + "]";
				objLogger.error(sMethod + sMsg);
				throw new DatabaseException(csMsgDB_ErrorUpdatingAccountForClient);
			}

		} else {
			String sMsg = "Invalid balance parameter: [" + sAccountBalance + "] not a double.";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParmAccountBalance);
		}

		return objUpdatedAccount;
	}

	public boolean deleteAllAccountsForClient(String sClientId)
			throws DatabaseException, AccountNotFoundException, BadParameterException {
		String sMethod = "deleteAllAccountsForClient(): ";
		boolean bRecordsDeleted = false;

		objLogger.trace(sMethod + "Entered");

		if (Validate.isInt(sClientId)) {
			objLogger.debug(sMethod + "client id: [" + sClientId + "] is an int.");

			try {
				List<Account> lstAccountsForClient = getAccountsForClient(sClientId);
				int iListSize = lstAccountsForClient.size();

				objLogger.debug(sMethod + "account list size for client: [" + iListSize + "]");

				if (iListSize > 0) {
					int iClientId = Integer.parseInt(sClientId);
					objLogger.debug(sMethod + "list of accounts to delete: [" + lstAccountsForClient.toString() + "]");

					for (int iCtr = 0; iCtr < iListSize; iCtr++) {
						String sAccountNumber = lstAccountsForClient.get(iCtr).getAccountNumber();
						objLogger.debug(sMethod + "deleting account number: [" + sAccountNumber + "] from client id: ["
								+ iClientId + "]");

						try {
							objLogger.trace(sMethod + "calling: deleteAccountForClient(" + iClientId + ","
									+ sAccountNumber + ")");
							deleteAccountForClient(iClientId, sAccountNumber);
						} catch (AccountNotFoundException objE) {
							objLogger.debug(sMethod + "Record not not found for delete account number: ["
									+ sAccountNumber + "] from client id: [" + iClientId + "]");
							throw new AccountNotFoundException(csMsgAccountNotFoundForClient);

						} catch (DatabaseException objE) {// this is only exception expected
							objLogger.debug(sMethod + "databse error deleting account number: [" + sAccountNumber
									+ "] from client id: [" + iClientId + "]");
							throw new DatabaseException(csMsgDB_ErrorDeletingAccountForClient);
						}
						bRecordsDeleted = true; // all client records deleted
					}

				} else {
					objLogger.debug(sMethod + "client with client id: [" + sClientId + "] has no accounts.");
					bRecordsDeleted = true;
				}

			} catch (AccountNotFoundException objE) {
				objLogger.debug(sMethod + "client with client id: [" + sClientId + "] has no accounts.");
				bRecordsDeleted = true;
			}

		} else {
			String sMsg = "Client Id: [" + sClientId + "] is not a number.";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParamClientId);
		}

		return bRecordsDeleted;
	}

	//
	// ### Assume caller validated that client id is an int and belongs to a client
	// in the database
	// Get the account by account number to see if it exist
	// If account exists then check if it belongs to this client id
	// If it is this clients account, then delete it
	// public boolean deleteAccountForClient(int iClientId, String sAccountNumber)
	public int deleteAccountForClient(int iClientId, String sAccountNumber)
			throws DatabaseException, AccountNotFoundException, BadParameterException {
		String sMethod = "deleteAccountForClient(int,String): ";
		int iStatusCode = ciDelAcctSuccess;

		objLogger.trace(sMethod + "Entered.");

		try {
			objLogger.debug(sMethod + "Checking if account number: [" + sAccountNumber + "] exists.");
			// see if there is an account with this number
			boolean bAccountExists = doesAccountExist(sAccountNumber);
			if (bAccountExists) {
				objLogger.debug(sMethod + "Account number: [" + sAccountNumber + "] does exists.");
				// now get the client to see if the account is theirs

				// called method will validate the account number no need to validate it here
				objLogger.debug(sMethod + "Gettin Account object with number: [" + sAccountNumber + "]");
				Account objAccount = getAccountByAccountNumber(sAccountNumber);
				objLogger.debug(sMethod + "Account object from get call: [" + objAccount.toString() + "]");

				int iClientIdForAccount = objAccount.getClientId();
				if (iClientIdForAccount == iClientId) {
					// now we can delete this account
					objAccountDAO.deleteRecord(sAccountNumber);
					iStatusCode = ciDelAcctSuccess; // if a return from DAO with no exceptions, then we deleted the record

				} else {
					String sMsg = "Account with account number: [" + sAccountNumber
							+ "] does not belong to client id: [" + iClientId + "].";
					objLogger.debug(sMethod + sMsg);
					iStatusCode = ciDelAccountNotClients;
				}
			}
		} catch (AccountNotFoundException objE) {
			objLogger.debug(sMethod + "Account number: [" + sAccountNumber + "] not found in the database.");
			//record was not deleted but does not matter since it is gone.
			return ciDelAcctRecordNotFound;
		} catch (DatabaseException objE) {
			objLogger.debug(sMethod + "DatabaseException: [" + objE.toString() + "]");
			return ciDelDB_Error;
		} catch (SQLException objE) {
		objLogger.debug(sMethod + "DatabaseException: [" + objE.toString() + "]");
		return ciDelDB_Error;
	}

		return iStatusCode;

	}

	//
	// ###
	public boolean doesAccountExist(String sAccountNumber) throws DatabaseException, AccountNotFoundException {
		String sMethod = "doesAccountExist(String): ";
		boolean bAcctExists = false;

		objLogger.trace(sMethod + "Entered.");
		try {
			objLogger.trace(sMethod + "calling: objAccountDAO.doesAccountExist(" + sAccountNumber + ")");
			bAcctExists = objAccountDAO.doesAccountExist(sAccountNumber);
			objLogger.debug(sMethod + "Account with account number: [" + sAccountNumber + "] DOES exists.");
			bAcctExists = true;
		} catch (AccountNotFoundException objE) {
			String sMsg = "Account with number:[" + sAccountNumber + "] does NOT exist.";
			objLogger.debug(sMethod + sMsg);
			throw new AccountNotFoundException(sMsg);
		}
		return bAcctExists;
	}

	/*
	
	*/

}
