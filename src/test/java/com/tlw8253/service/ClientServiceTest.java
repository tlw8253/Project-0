package com.tlw8253.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.*; // You may need to type this import manually to make use of 
// the argument matchers for Mockito, such as eq() or any()

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.tlw8253.application.Constants;
import com.tlw8253.dao.ClientDAO;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.model.Client;
import com.tlw8253.exception.*;

public class ClientServiceTest implements Constants {
	//using a debugger here to gain better understanding with JUnit / Mockito testing
	private static Logger objLogger = LoggerFactory.getLogger(ClientServiceTest.class);

	private ClientService objMockClientService;
	//private ClientService objRealClientService;	should not need this with new understanding of Mockito
	private ClientDAO objMockClientDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objLogger.trace("setUpBeforeClass()");		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		objLogger.trace("tearDownAfterClass()");
	}

	@Before
	public void setUp() throws Exception {
		objLogger.trace("setUp()");
		this.objMockClientDAO = mock(ClientDAO.class); // fake client DAO using Mockito
		// inject mocked object into client service object
		this.objMockClientService = new ClientService(objMockClientDAO);
		//this.objRealClientService = new ClientService();
	}

	@After
	public void tearDown() throws Exception {
		objLogger.trace("tearDown()");
	}

///* Begin comment out working tests

	//20210806 An Ah ha moment.  mockito is just that mocking data returns and not
	//actual updating the database.  Running the test_getAllClients_success() with
	//empty database tables provide the point.  Since settup record with mock values
	//we don't care what happens with the database outside the test cases.

	@Test
	public void test_getAllClients_success() throws DatabaseException, SQLException {

		objLogger.trace("test_getAllClients_success()");
		List<Client> mockRetValues = new ArrayList<>();
		// for mock dao use different data than the initial data load for clarity
		mockRetValues.add(new Client(1, "Earl", "Boen", "Dr. Siberman"));
		mockRetValues.add(new Client(2, "Lance", "Henriksen", "Detective Hal Vukovich"));
		mockRetValues.add(new Client(3, "Bill", "Paxton", "Punk Leader"));
		mockRetValues.add(new Client(4, "Paul", "Winfield", "Lieutenant Ed Traxler"));

		when(objMockClientDAO.getAllClients()).thenReturn(mockRetValues);

		// get the actual values from client services
		List<Client> objActualValues = objMockClientService.getAllClients();

		// set the expected values
		List<Client> objExpectedValues = new ArrayList<>();
		objExpectedValues.add(new Client(1, "Earl", "Boen", "Dr. Siberman"));
		objExpectedValues.add(new Client(2, "Lance", "Henriksen", "Detective Hal Vukovich"));
		objExpectedValues.add(new Client(3, "Bill", "Paxton", "Punk Leader"));
		objExpectedValues.add(new Client(4, "Paul", "Winfield", "Lieutenant Ed Traxler"));

		assertEquals(objExpectedValues, objActualValues);
	}
	

	@Test
	public void test_getAllClients_failure() throws SQLException {
		objLogger.trace("test_getAllClients_failure()");

		// simulate DAO database exception
		when(objMockClientDAO.getAllClients()).thenThrow(SQLException.class);

		try {
			objMockClientService.getAllClients();

			fail();

		} catch (DatabaseException objE) {
			//check message received against expected result
			assertEquals(csMsgDB_ErrorGettingAllClients, objE.getMessage());
		}
	}


	@Test
	public void test_getClientById_success()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {
		// keep consistent with mock data from getAllClients
		Client mockRetValues = new Client(2, "Lance", "Henriksen", "Detective Hal Vukovich");
		when(objMockClientDAO.getClientById(2)).thenReturn(mockRetValues);

		// get the actual values from client services
		Client objActualValues = objMockClientService.getClientById("2");

		// set the expected values
		Client objExpectedValues = new Client(2, "Lance", "Henriksen", "Detective Hal Vukovich");

		assertEquals(objExpectedValues, objActualValues);
	}

	@Test
	public void test_getClientById_failure_BadParam_exception()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {
		objLogger.trace("test_getClientById_failure_BadParam_exception()");

		//bad param is first condition inside the service method
		try {
			objMockClientService.getClientById("Not a Number");

			fail();

		} catch (BadParameterException objE) {

			assertEquals(csMsgBadParamClientId, objE.getMessage());
		}
	}

	@Test
	public void test_getClientById_failure_CNF_exception()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {
		objLogger.trace("test_getClientById_failure_CNF_exception()");

		//client not found is the next condition inside the service method
		try {
			objMockClientService.getClientById("9999");

			fail();

		} catch (ClientNotFoundException objE) {
			assertEquals(csMsgClientNotFound, objE.getMessage());
		}
	}


	@Test
	public void test_getClientById_failure_DB_exception()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {
		objLogger.trace("test_getClientById_failure_DB_exception()");
		
		//database exception is the last condition inside the service method
		when(objMockClientDAO.getClientById(2)).thenThrow(SQLException.class);

		try {
			objMockClientService.getClientById("2");

			fail();

		} catch (DatabaseException objE) {

			assertEquals(csMsgDB_ErrorGettingByClientId, objE.getMessage());
		}
	}

	
	@Test
	public void test_addClient_success() throws DatabaseException, SQLException, BadParameterException {
		objLogger.trace("test_addClient_success()");
		
			// using mocked data previously defined this add would result in client id of 5
			AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese");
			when(objMockClientDAO.addClient(eq(objAddClientDTO))).thenReturn(new Client(5, "Michael", "Biehn", "Kyle Reese"));

			Client objActualValues = objMockClientService.addClient(objAddClientDTO);

			assertEquals(new Client(5, "Michael", "Biehn", "Kyle Reese"), objActualValues);
	}

	@Test
	public void test_addClient_failure_BadParam_Exception_firstname()
			throws DatabaseException, SQLException, BadParameterException {
		objLogger.trace("test_addClient_failure_BadParam_Exception_firstname()");
		
		//bad param is first condition inside the service method checking first name first
		try {
			AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("", "Biehn", "Kyle Reese");
			objMockClientService.addClient(objAddClientDTO);

			fail();

		} catch (BadParameterException objE) {

			assertEquals(csMsgBadParamClientName, objE.getMessage());
		}
	}

	@Test
	public void test_addClient_failure_BadParam_Exception_lastname()
			throws DatabaseException, SQLException, BadParameterException {
		objLogger.trace("test_addClient_failure_BadParam_Exception_lastname()");
		
		//bad param is first condition inside the service method checking last name second
		try {
			AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "", "Kyle Reese");
			objMockClientService.addClient(objAddClientDTO);

			fail();

		} catch (BadParameterException objE) {

			assertEquals(csMsgBadParamClientName, objE.getMessage());
		}
	}
	
	@Test
	public void test_addClient_failure_DB_Exception()
			throws DatabaseException, SQLException, BadParameterException {
		objLogger.trace("test_addClient_failure_DB_Exception()");
		
		AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese");
		when(objMockClientDAO.addClient(objAddClientDTO)).thenThrow(SQLException.class);

		try {
			objMockClientService.addClient(objAddClientDTO);

			fail();

		} catch (DatabaseException objE) {

			assertEquals(csMsgDB_ErrorAddingClient, objE.getMessage());
		}
	}


	@Test
	public void test_editClient_success() throws DatabaseException, SQLException, BadParameterException, ClientNotFoundException {
		objLogger.trace("test_editClient_success()");
		
			// values to update for mocked existing client id = 5
			AddOrEditClientDTO objEditClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese 2");			
			
			Client objClient_5 = new Client(5, "Michael", "Biehn", "Kyle Reese"); //existing record in mock data
			when(objMockClientDAO.getClientById(eq(5))).thenReturn(objClient_5);			
			when(objMockClientDAO.editClient(eq(5), eq(objEditClientDTO))).
				thenReturn(new Client(5, "Michael", "Biehn", "Kyle Reese 2"));

			Client objActualValues = objMockClientService.editClient("5", objEditClientDTO);
			Client objExpectedValues = new Client(5, "Michael", "Biehn", "Kyle Reese 2");

			//objLogger.debug("test_editClient_success(): objActualValues: [" + objActualValues.toString() + "]");
			//objLogger.debug("test_editClient_success(): objExpectedValues: [" + objExpectedValues.toString() + "]");
			
			assertEquals(objExpectedValues, objActualValues);
	}

	@Test
	public void test_editClient_failure_BadParam_Exception()
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		objLogger.trace("test_editClient_failure_BadParam_Exception()");
		
		//not an integer for the client id
		try {
			AddOrEditClientDTO objEditClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese 3");
			objMockClientService.editClient("Not an int",objEditClientDTO);

			fail();

		} catch (BadParameterException objE) {

			assertEquals(csMsgBadParamClientId, objE.getMessage());
		}
	}

	@Test
	public void test_editClient_failure_CNF_Exception()
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		objLogger.trace("test_editClient_failure_CNF_Exception()");
		//client not found
		try {
			AddOrEditClientDTO objEditClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese 4");
			objMockClientService.editClient("9999",objEditClientDTO);

			fail();

		} catch (ClientNotFoundException objE) {

			assertEquals(csMsgClientNotFound, objE.getMessage());
		}
	}
	
	@Test
	public void test_editClient_DB_exception() throws DatabaseException, SQLException, BadParameterException, ClientNotFoundException {
		objLogger.trace("test_editClient_DB_exception()");
		
		AddOrEditClientDTO objEditClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese 5"); //mock to edit
		Client objClient_5 = new Client(5, "Michael", "Biehn", "Kyle Reese"); //existing record in mock data
		
		when(objMockClientDAO.getClientById(eq(5))).thenReturn(objClient_5);
		when(objMockClientDAO.editClient(5, objEditClientDTO)).thenThrow(SQLException.class);

		try {
			objMockClientService.editClient("5", objEditClientDTO);

			fail();

		} catch (DatabaseException objE) {

			assertEquals(csMsgDB_ErrorUpdatingClient, objE.getMessage());
		}
	}

	
	@Test
	public void test_deleteClient_success() throws DatabaseException, SQLException, BadParameterException, ClientNotFoundException {
		objLogger.trace("test_deleteClient_success()");
		
		//delete method first looks for the client so return a client
		// values for mocked existing client id = 5 after mock edit
		Client objClient_5 = new Client(5, "Michael", "Biehn", "Kyle Reese 2");	
		when(objMockClientDAO.getClientById(eq(5))).thenReturn(objClient_5);		
		
		when(objMockClientDAO.deleteClient(eq(5))).thenReturn(true);
		boolean bRet = objMockClientDAO.deleteClient(5);
				
		assertEquals(bRet, true);
		
		//doThrow(new RuntimeException()).when(mockedList).clear();
		//doNothing().when(objMockClientDAO).deleteClient(eq(5));

	}


	@Test
	public void test_deleteClient_failure_BadParam_Exception()
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		objLogger.trace("test_deleteClient_failure_BadParam_Exception()");
		
		//not an integer for the client id
		try {
			objMockClientService.deleteClient("Not an int");

			fail();

		} catch (BadParameterException objE) {

			assertEquals(csMsgBadParamClientId, objE.getMessage());
		}
	}

	
	@Test
	public void test_deleteClient_CNF_exception() throws DatabaseException, SQLException, BadParameterException, ClientNotFoundException {
		objLogger.trace("test_deleteClient_CNF_exception()");
		
		//client not found
		try {
			
			objMockClientService.deleteClient("9999");

			fail();

		} catch (ClientNotFoundException objE) {

			assertEquals(csMsgClientNotFound, objE.getMessage());
		}

	}

	@Test
	public void test_deleteClient_DB_exception() throws DatabaseException, SQLException, BadParameterException, ClientNotFoundException {
		objLogger.trace("test_editClient_DB_exception()");
		
		Client objClient_5 = new Client(5, "Michael", "Biehn", "Kyle Reese"); //existing record in mock data
		
		when(objMockClientDAO.getClientById(eq(5))).thenReturn(objClient_5);		
		when(objMockClientDAO.deleteClient(5)).thenThrow(SQLException.class);

		try {
			objMockClientService.deleteClient("5");

			fail();

		} catch (DatabaseException objE) {

			assertEquals(csMsgDB_ErrorDeletingClient, objE.getMessage());
		}
	}

	///*COMMENT OUT TESTS BEGINS HERE

	//END COMMENT OUT TEST */


	
/*


*/	

	
	
	


}













