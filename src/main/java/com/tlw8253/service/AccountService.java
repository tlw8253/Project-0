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
		String sMethod = "getAccountByAccountNumber(): ";

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

		} catch (SQLException objE) {
			String sMsg = "Error with database getting account with identifier: [" + sAccountNumber + "]";
			objLogger.error(sMethod + sMsg);
			throw new DatabaseException(sMsg);
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

		List<Account> lstAccounts;
		if (Validate.isInt(sClientId)) {
			objLogger.debug(sMethod + "parseInt client id input paramenter: [" + sClientId + "]");
			int iClientId = Integer.parseInt(sClientId);
			lstAccounts = getAccountsForClient(iClientId);
		} else {
			String sMsg = "Error converting client id to int: [" + sClientId + "].";
			objLogger.debug(sMethod + sMsg);
			throw new BadParameterException(csMsgBadParamClientId);
		}
		return (lstAccounts);
	}

	//
	// ###
	public List<Account> getAccountsForClient(int iClientId) throws DatabaseException, AccountNotFoundException {
		String sMethod = "getAccountsForClient(int): ";
		List<Account> lstAccounts;
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
			objLogger.error(sMethod + sMsg);
			throw new DatabaseException(csMsgAccountsNotFound);
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

			lstAccounts = getAccountsForClientInRange(iClientId, iUpperRange, iLowerRange);
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
			throw new DatabaseException(csMsgAccountsNotFound);
		}
		return (lstAccounts);

	}

	//
	// ### validate input parameters prior to sending to the DAO to add to the
	// database.
	public Account addAccountForClient(AccountAddDTO objAccountAddDTO) throws DatabaseException, BadParameterException {
		String sMethod = "addAccountForClient(String): ";
		String sMsg = "";

		try {

			// Expected the caller to do validation if required if this client already
			// exists prior
			// to adding. If not then database will throw the exception.
			String sClientId = objAccountAddDTO.getClientId();
			sMsg = "Error converting client id to int: [" + sClientId + "]";
			objLogger.debug(sMethod + "parseInt sClientId input paramenter: [" + sClientId + "]");
			int iClientId = Integer.parseInt(sClientId);
			objAccountAddDTO.setClientId(iClientId);

			String sAccountNumber = objAccountAddDTO.getAccountNumber();
			sMsg = "Error converting account number to int: [" + sAccountNumber + "]";
			objLogger.debug(sMethod + "parseInt sAccountNumber input paramenter: [" + sAccountNumber + "]");
			Integer.parseInt(sAccountNumber); // don't need the int value just checking that value is all numeric

			if (sAccountNumber.length() != ciAccountTblAccountNumberLen) {
				sMsg = "Account number is required to be " + ciAccountTblAccountNumberLen + " numeric in length: [" + sAccountNumber + "]";
				objLogger.debug(sMethod + sMsg);
				throw new BadParameterException(sMsg);
			}

			String sAccountType = objAccountAddDTO.getAccountType().toUpperCase();
			if ((sAccountType.equals(csAccountTypeValueChecking)) || sAccountType.equals(csAccountTypeValueSavings)) {
				// continue
			} else {

				sMsg = "Account type is either: [" + csAccountTypeValueChecking + "] or: [" + csAccountTypeValueSavings
						+ "] not: [" + sAccountType + "]";
				objLogger.debug(sMethod + sMsg);
				throw new BadParameterException(sMsg);
			}
			objAccountAddDTO.setAccountType(sAccountType); // set to validated case value

			String sAccountBalance = objAccountAddDTO.getAccountBalance();
			sMsg = "Error converting account balance to double: [" + sAccountBalance + "]";
			objLogger.debug(sMethod + "parseInt sAccountBalance input paramenter: [" + sAccountBalance + "]");
			double dAccountBalance = Double.parseDouble(sAccountBalance);
			objAccountAddDTO.setAccountBalance(dAccountBalance);

			// Attempt adding a new account for the client
			try {

				// Client objAddedClient = objClientDAO.addClient(objAddClientDTO);
				Account objAccountAdded = objAccountDAO.addRecord(objAccountAddDTO);
				objLogger.debug(sMethod + "Account object created: [" + objAccountAdded.toString() + "]");
				return objAccountAdded;

			} catch (SQLException objE) {
				sMsg = "Database error adding account for client id: [" + sClientId + "]" + " Account information: ["
						+ objAccountAddDTO.toString() + "]";
				objLogger.error(sMethod + sMsg + "[" + objE.getMessage() + "]");
				throw new DatabaseException(sMsg);
			}

		} catch (NumberFormatException objE) {
			objLogger.error(sMethod + sMsg); // message set prior to causing the exception
			throw new BadParameterException(sMsg);
		}
	}

	//
	// ### Assume caller has validated client id.
	// validate account type and balance for format and value
	// generate random account number and check if it exists
	public Account createNewAccountForClient(int iClientId, String sAccountType, String sAccountBalance)
			throws BadParameterException, Exception {
		String sMethod = "createNewAccountForClient(): ";
		Account objNewAccount = new Account();

		if ((csAccountTypeValueChecking.equalsIgnoreCase(sAccountType))
				|| (csAccountTypeValueSavings.equalsIgnoreCase(sAccountType))) {

			if (Validate.isDouble(sAccountBalance)) {

				boolean bAccountExists = true;
				String sAccountNumber = "";
				do {
					int iAccountNumber = Utility.getRandomIntBetween(ciAccountNumMinVal, ciAccountNumMaxVal);
					sAccountNumber = Integer.toString(iAccountNumber);

					if (sAccountNumber.length() < ciAccountTblAccountNumberLen) {
						sAccountNumber = Utility.padIntLeadingZero(iClientId, ciAccountTblAccountNumberLen);
					}

					try {
						// check if account number already exists
						bAccountExists = doesAccountExist(sAccountNumber);
					} catch (Exception objE) {
						// any kind of exception here is a problem, throw it to caller.
						String sMsg = "Exception occurred while accessing the database.";
						throw new Exception(sMsg);
					}

				} while (bAccountExists);

				// have account number not in database so use to add record.
				AccountAddDTO objAccountAddDTO = new AccountAddDTO();
				objAccountAddDTO.setClientId(Integer.toString(iClientId));
				objAccountAddDTO.setAccountNumber(sAccountNumber);
				objAccountAddDTO.setAccountType(sAccountType);
				objAccountAddDTO.setAccountBalance(sAccountBalance); //already validated to be a double
				//now add the record
				objNewAccount= addAccountForClient(objAccountAddDTO);
				
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

		return objNewAccount;
	}

	public boolean doesAccountExist(String sAccountNumber) throws Exception {
		boolean bAcctExists = true;

		try {
			bAcctExists = objAccountDAO.doesAccountExist(sAccountNumber);
		} catch (Exception objE) {
			// any kind of exception here is a problem, throw it to caller.
			String sMsg = "Exception occurred while accessing the database.";
			throw new Exception(sMsg);
		}
		return bAcctExists;
	}

	/*
	
	*/

}
