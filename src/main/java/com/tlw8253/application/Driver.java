package com.tlw8253.application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.model.Client;
import com.tlw8253.service.ClientService;

/**
 * This is a driver used during development to test functionality as it is
 * built. The Application class will be the main entry point for the program
 * when done.
 * 
 * @author tlw8748253
 *
 */
public class Driver {
	private final static Logger objLogger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {
		

		getAllCients();
		// getCientById("1");
		//addClient();
		//editClient();
		//deleteClient();
		//getAllCients();
		
	}
	
	//
	// ###
	public static void deleteClient() {
		String sMethod = "deleteCient(): ";
		ClientService objClientService = new ClientService();
		String sClientId = "5";

		try {
			String sMsg = sMethod + "Deleting client with id of: [" +  sClientId + "]";
			objLogger.debug(sMsg);
			
			objClientService.deleteClient(sClientId);

			sMsg = sMethod + "Checking if client with id of: [" +  sClientId + "] still exists.";
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
