package com.tlw8253.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*; // You may need to type this import manually to make use of 
// the argument matchers for Mockito, such as eq() or any()

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tlw8253.dao.ClientDAO;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.model.Client;
import com.tlw8253.exception.*;

public class ClientServiceTest {
	private ClientService objMockClientService;
	private ClientService objRealClientService;
	private ClientDAO objMockClientDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass()");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass()");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp()");
		this.objMockClientDAO = mock(ClientDAO.class); // fake client DAO using Mockito
		// inject mocked object into client service object
		this.objMockClientService = new ClientService(objMockClientDAO);
		this.objRealClientService = new ClientService();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown()");
	}

	/// * Begin comment out working tests

	@Test
	public void test_getAllClients_success() throws DatabaseException, SQLException {
		// this test will only work after the initial database load
		// after other database updates, this is expected to fail
		List<Client> mockRetValues = new ArrayList<>();
		// for mock dao //this is the initial database load with 4 records
		mockRetValues.add(new Client(1, "Arnold", "Schwarzeneg", "Terminator"));
		mockRetValues.add(new Client(2, "Linda", "Hamilton", "Sarah Connor"));
		mockRetValues.add(new Client(3, "Edward", "Furlong", "John Connor"));
		mockRetValues.add(new Client(4, "Robert", "Patrick", "T-1000"));

		when(objMockClientDAO.getAllClients()).thenReturn(mockRetValues);

		// get the actual values from client services
		List<Client> objActualValues = objMockClientService.getAllClients();

		// set the expected values
		List<Client> objExpectedValues = new ArrayList<>();
		objExpectedValues.add(new Client(1, "Arnold", "Schwarzeneg", "Terminator"));
		objExpectedValues.add(new Client(2, "Linda", "Hamilton", "Sarah Connor"));
		objExpectedValues.add(new Client(3, "Edward", "Furlong", "John Connor"));
		objExpectedValues.add(new Client(4, "Robert", "Patrick", "T-1000"));

		assertEquals(objExpectedValues, objActualValues);
	}

	@Test
	public void test_getAllClients_failure() throws SQLException {
		// simulate DAO database exception
		when(objMockClientDAO.getAllClients()).thenThrow(SQLException.class);

		try {
			objMockClientService.getAllClients();

			fail();

		} catch (DatabaseException objE) {

			assertEquals("Error with database getting all clients.", objE.getMessage());
		}
	}

	@Test
	public void test_getClientById_success()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {
		// this test will work as long as record 2 exists and is un modified
		Client mockRetValues = new Client(2, "Linda", "Hamilton", "Sarah Connor"); // for mock dao
		when(objMockClientDAO.getClientById(2)).thenReturn(mockRetValues);

		// get the actual values from client services
		Client objActualValues = objMockClientService.getClientById("2");

		// set the expected values
		Client objExpectedValues = new Client(2, "Linda", "Hamilton", "Sarah Connor");

		assertEquals(objExpectedValues, objActualValues);
	}

	@Test
	public void test_getClientById_failure_CNF_exception()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {

		try {
			objMockClientService.getClientById("9999");

			fail();

		} catch (ClientNotFoundException objE) {
			assertEquals("Client was not found in the database.", objE.getMessage());
		}
	}

	@Test
	public void test_getClientById_failure_DB_exception()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {
		when(objMockClientDAO.getClientById(2)).thenThrow(SQLException.class);

		try {
			objMockClientService.getClientById("2");

			fail();

		} catch (DatabaseException objE) {

			assertEquals("Database error getting the client by id.", objE.getMessage());
		}
	}

	@Test
	public void test_getClientById_failure_BadParam_exception()
			throws DatabaseException, SQLException, ClientNotFoundException, BadParameterException {
		// simulate DAO database exception
		// when(objMockClientDAO.getClientById(2)).thenThrow(SQLException.class);

		try {
			objMockClientService.getClientById("Not a Number");

			fail();

		} catch (BadParameterException objE) {

			assertEquals("Client Id is not an integer.", objE.getMessage());
		}
	}

	@Test
	public void test_addClient_success() throws DatabaseException, SQLException, BadParameterException {
		// ISSUE: with this test. Returns true always???
		try {

			// get the actual values from client services
			AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese");

			when(objMockClientDAO.addClient(eq(objAddClientDTO))).thenReturn(new Client(5, "Michael", "Biehn", "Kyle Reese"));

			// The mock ClientService ran into some sort of security manager issue in
			// ClassLoader.
			Client objActualValues = objRealClientService.addClient(objAddClientDTO);
			System.out.println("objActualValues: [" + objActualValues.toString() + "]");

			// Interesting note: client id not part of assertEquals?
			// When record was inserted and auto id was 9 and again at 10, the assert came
			// back true
			// Client objExpectedValues = new Client(5, "Michael", "Biehn", "Kyle Reese");

			// this return true even when objects do not match?
			// assertEquals(objExpectedValues,objActualValues);
			assertEquals(new Client(5, "Michael", "Biehn", "Kyle Reese"), objActualValues);

		} catch (Exception objE) {
			System.out.println("Exception: [" + objE.getMessage() + "]");
			objE.printStackTrace();
		}

	}

	@Test
	public void test_addClient_failure_BadParam_Exception()
			throws DatabaseException, SQLException, BadParameterException {
		//blank in first or last name

		try {
			AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "", "Kyle Reese");
			objRealClientService.addClient(objAddClientDTO);

			fail();

		} catch (BadParameterException objE) {

			assertEquals("Client first and last name must contain values.", objE.getMessage());
		}
	}


	
	@Test
	public void test_addClient_failure_DB_Exception()
			throws DatabaseException, SQLException, BadParameterException {
		
		AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese");
		when(objMockClientDAO.addClient(objAddClientDTO)).thenThrow(SQLException.class);

		try {
			objMockClientService.addClient(objAddClientDTO);

			fail();

		} catch (DatabaseException objE) {

			assertEquals("Database error when adding a client.", objE.getMessage());
		}
	}

	// End comment out working tests*/
	
	
	@Test
	public void test_editClient_failure_BadParam_Exception()
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		//not an integer for the client id

		try {
			AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese");
			objRealClientService.editClient("Not an int",objAddClientDTO);

			fail();

		} catch (BadParameterException objE) {

			assertEquals("Client Id is not an integer.", objE.getMessage());
		}
	}

	@Test
	public void test_editClient_failure_DB_Exception()
			throws DatabaseException, SQLException, BadParameterException {
		
		AddOrEditClientDTO objAddClientDTO = new AddOrEditClientDTO("Michael", "Biehn", "Kyle Reese");
		when(objMockClientDAO.addClient(objAddClientDTO)).thenThrow(SQLException.class);

		try {
			objMockClientService.addClient(objAddClientDTO);

			fail();

		} catch (DatabaseException objE) {

			assertEquals("Database error updating a client.", objE.getMessage());
		}
	}

	

	
	
	


}













