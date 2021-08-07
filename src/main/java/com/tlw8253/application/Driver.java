package com.tlw8253.application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.dao.AccountDAOImpl;
import com.tlw8253.dto.AccountAddDTO;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.dto.AddDTO;
import com.tlw8253.exception.*;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.javalin.JavalinHelper;
import com.tlw8253.model.Account;
import com.tlw8253.model.Client;
import com.tlw8253.service.AccountService;
import com.tlw8253.service.ClientService;

import com.tlw8253.util.*;

/**
 * This is a driver used during development to test functionality as it is
 * built. The Application class will be the main entry point for the program
 * when done.
 * 
 * Concepts, classes, methods and coding are in part based on the
 * javalin-jdbc-demo and jdbc-demo projects shown in the Revature
 * java-with-automation training by Bach Tran. This project is not entirely a
 * direct copy from the training but concepts and coding examples where used and
 * modified for the purposes of Project-0. With that said, parts of the code
 * where used verbatim with class, method, and variable name changes.
 * 
 * Another source for inspiration and code manipulation came from the Javalin
 * website: https://javalin.io/tutorials/testing and a cloning of the
 * https://github.com/tipsy/javalin-testing-example.git repository. Code was
 * modified for starting this project. However not much will remain at the end
 * once implementing the .app, .controller, .dao, .dto. .exception, .model,
 * .service, .util model from the Revature training. The Javalin website was
 * extremely helpful in starting this project prior to the javalin-jdbc-demo and
 * jdbc-demo training.
 * 
 * 
 * @author tlw8748253
 *
 */
public class Driver implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {

		// Test initial architecture based on Javalin website example
//		JavalinHelper objJavalinHelper = new JavalinHelper();
//		objJavalinHelper.createRoutes();
//		objJavalinHelper.start(ciListingPort);

		// Test database access for Client
		// getAllCients();
		// getCientById("1");
		// addClient();
		// editClient();
		// deleteClient();
		// getAllCients();

		// testGenericAddDTO();
		// testAccountAddDTO();
		// testAccountDAOImplGetAllRecords();
		// testAccountDAOImplAddRecord();

		// testGetAccountsForClientInRange();
		// testGetRandomIntBetween();
		// testPaddIntegerLeadingZero();

		testCreateNewAccountForClient();

	}

	//
	// ###
	public static void testCreateNewAccountForClient() {
		String sMethod = "testCreateNewAccountForClient(): ";
		AccountService objAccountService = new AccountService();

		try {
			Account objNewAccount = objAccountService.createNewAccountForClient(4, csAccountTypeValueSavings, "5231.10");
			objLogger.debug(sMethod + "objNewAccount: [" + objNewAccount.toString() + "]");
			
		} catch (Exception objE) {
			objLogger.debug(sMethod + "Exception: [" + objE.getMessage() + "]");
		}
	}

	//
	// ###
	public static void testPaddIntegerLeadingZero() {
		String sMethod = "testPaddIntegerLeadingZero(): ";

		String sPadValue = Utility.padIntLeadingZero(5, 5);
		objLogger.debug(sMethod + "String: [" + sPadValue + "]");

		sPadValue = Utility.padIntLeadingZero(555, 5);
		objLogger.debug(sMethod + "String: [" + sPadValue + "]");

		sPadValue = Utility.padIntLeadingZero(5555, 5);
		objLogger.debug(sMethod + "String: [" + sPadValue + "]");

		sPadValue = Utility.padIntLeadingZero(55555, 5);
		objLogger.debug(sMethod + "String: [" + sPadValue + "]");

		sPadValue = Utility.padIntLeadingZero(555555, 5);
		objLogger.debug(sMethod + "String: [" + sPadValue + "]");

	}

	//
	// ###
	public static void testGetRandomIntBetween() {
		String sMethod = "testGetRandomIntBetween(): ";
		int iValue = Utility.getRandomIntBetween(1, 99999);
		objLogger.debug(sMethod + "iValue: [" + iValue + "]");
	}

	//
	// ###
	public static void testGetAccountsForClientInRange() {
		String sMethod = "testGetAccountsForClientInRange(): ";
		AccountService objAccountService = new AccountService();

		try {
			List<Account> lstAccounts = objAccountService.getAccountsForClientInRange("1", "2000", "400");
			objLogger.debug(sMethod + "lstAccounts: [" + lstAccounts.toString() + "]");
		} catch (Exception objE) {
			objLogger.debug(sMethod + "caught Exception: [" + objE.toString() + "]");
		}

	}

	//
	// ###
	public static void testAccountDAOImplAddRecord() {
		String sMethod = "testAccountDAOImplAddRecord(): ";
		AccountDAOImpl objAccountDAOImpl = new AccountDAOImpl();
		AccountAddDTO objAccountAddDTO = new AccountAddDTO("80000", csAccountTypeValueSavings, "1158.21", "4");
		objAccountAddDTO.setAccountBalance(1158.21);
		objAccountAddDTO.setClientId(4);

		try {
			Account objAccounts = objAccountDAOImpl.addRecord(objAccountAddDTO);

			objLogger.debug(
					sMethod + "Account returned by objAccountDAOImpl.addRecord():[" + objAccounts.toString() + "]");

		} catch (Exception objE) {
			objLogger.error(sMethod + "Exception: [" + objE.getMessage() + "]");
		}
	}

	//
	// ###
	public static void testAccountDAOImplGetAllRecords() {
		String sMethod = "testAccountDAOImplGetAllRecords(): ";
		AccountDAOImpl objAccountDAOImpl = new AccountDAOImpl();

		try {
			List<Account> lstAccounts = objAccountDAOImpl.getAllRecords();

			for (int iCtr = 0; iCtr < lstAccounts.size(); iCtr++) {
				Account objAccount = lstAccounts.get(iCtr);
				objLogger.debug(sMethod + objAccount.toString());
			}
		} catch (Exception objE) {
			objLogger.error(sMethod + "Exception: [" + objE.getMessage() + "]");
		}
	}

	//
	// ###
	public static void testAccountAddDTO() {
		String sMethod = "testAccountAddDTO(): ";
		AccountAddDTO objAccountAddDTO = new AccountAddDTO();

		objAccountAddDTO.setAccountNumber("07913");
		objAccountAddDTO.setAccountType("Checking");
		objAccountAddDTO.setAccountBalance("5032.64");
		objLogger.debug(sMethod + objAccountAddDTO.toString());
	}

	//
	// ###
	public static void testGenericAddDTO() {
		String sMethod = "testGenericAddDTO(): ";
		AddDTO objGenericAddDTO = new AddDTO();

		objGenericAddDTO.setDataElement("Test1", "TestValue1");
		objGenericAddDTO.setDataElement("Test2", "TestValue2");
		objLogger.debug(sMethod + objGenericAddDTO.toString());
	}

	//
	// ###
	public static void deleteClient() {
		String sMethod = "deleteCient(): ";
		ClientService objClientService = new ClientService();
		String sClientId = "5";

		try {
			String sMsg = sMethod + "Deleting client with id of: [" + sClientId + "]";
			objLogger.debug(sMsg);

			objClientService.deleteClient(sClientId);

			sMsg = sMethod + "Checking if client with id of: [" + sClientId + "] still exists.";
			objLogger.debug(sMsg);

			getCientById("5");

		} catch (Exception objE) {
			objLogger.error(sMethod + "Exception: [" + objE + "]");

		}

	}

	// ###
	public static void editClient() {
		String sMethod = "editClient(): ";
		ClientService objClientService = new ClientService();
		AddOrEditClientDTO objEditClientDTO = new AddOrEditClientDTO();

		String sClientId = "5";

		objEditClientDTO.setFirstName("Earl2");
		objEditClientDTO.setLastName("Boen2");
		objEditClientDTO.setNickname("Dr. Siberman2");

		try {
			String sMsg = "";
			Client objClient = objClientService.editClient(sClientId, objEditClientDTO);

			sMsg = sMethod + "Client was updated: [" + objClient.toString() + "]";
			objLogger.info(sMsg);

		} catch (Exception objE) {
			objLogger.error(sMethod + "Exception: [" + objE + "]");

		}

	}

	//
	// ###
	public static void addClient() {
		String sMethod = "addClient(): ";
		ClientService objClientService = new ClientService();
		AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO();

		objAddClientDTO.setFirstName("Earl");
		objAddClientDTO.setLastName("Boen");
		objAddClientDTO.setNickname("Dr. Siberman");

		try {
			String sMsg = "";
			Client objClient = objClientService.addClient(objAddClientDTO);

			sMsg = sMethod + "Client was added: [" + objClient.toString() + "]";
			objLogger.info(sMsg);

		} catch (Exception objE) {
			objLogger.error(sMethod + "Exception: [" + objE + "]");

		}

	}

	//
	// ###
	public static void getAllCients() {
		String sMethod = "getAllCients(): ";
		ClientService objClientService = new ClientService();

		try {
			String sMsg = "";
			List<Client> lstClients = objClientService.getAllClients();
			for (int iCtr = 0; iCtr < lstClients.size(); iCtr++) {
				sMsg = sMethod + "List element: [" + iCtr + "]: " + lstClients.get(iCtr).toString();
				objLogger.info(sMsg);

			}

		} catch (DatabaseException objE) {
			objLogger.error(sMethod + "DatabaseException: [" + objE + "]");

		}

	}

	//
	// ###
	public static void getCientById(String sClientId) {
		String sMethod = "getCientById(): ";
		ClientService objClientService = new ClientService();

		try {
			String sMsg = "";
			Client objClient = objClientService.getClientById(sClientId);

			sMsg = sMethod + "Client by id of: [" + sClientId + "]: [" + objClient.toString() + "]";
			objLogger.info(sMsg);

		} catch (Exception objE) {
			objLogger.error(sMethod + "Exception: [" + objE + "]");

		}

	}

}
