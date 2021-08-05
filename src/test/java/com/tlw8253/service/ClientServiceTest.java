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
	private ClientService objClientService;
	private ClientDAO objClientDAO;

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
		this.objClientDAO = mock(ClientDAO.class); //fake client DAO using Mockito
		//inject mocked object into client service object
		this.objClientService = new ClientService(objClientDAO);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown()");
	}

	@Test
	public void test_getAllClients_success() throws DatabaseException, SQLException {
		//this test will only work after the initial database load
		//after other database updates, this is expected to fail
		List<Client> mockRetValues = new ArrayList<>(); //for mock dao
		//this is the initial database load with 4 records
		mockRetValues.add(new Client(1, "Arnold", "Schwarzeneg", "Terminator"));
		mockRetValues.add(new Client(2, "Linda", "Hamilton", "Sarah Connor"));
		mockRetValues.add(new Client(3, "Edward", "Furlong", "John Connor"));
		mockRetValues.add(new Client(4, "Robert", "Patrick", "T-1000"));
		when(objClientDAO.getAllClients()).thenReturn(mockRetValues);
		
		//get the actual values from client services
		List<Client> objActualValues = objClientService.getAllClients();
		
		//set the expected values
		List<Client> objExpectedValues = new ArrayList<>();
		objExpectedValues.add(new Client(1, "Arnold", "Schwarzeneg", "Terminator"));
		objExpectedValues.add(new Client(2, "Linda", "Hamilton", "Sarah Connor"));
		objExpectedValues.add(new Client(3, "Edward", "Furlong", "John Connor"));
		objExpectedValues.add(new Client(4, "Robert", "Patrick", "T-1000"));

		assertEquals(objExpectedValues,objActualValues);
	}

	@Test
	public void test_getAllClients_failure() throws SQLException {
		//simulate DAO database exception
		when(objClientDAO.getAllClients()).thenThrow(SQLException.class);
		
		try {
			objClientService.getAllClients();
			
			fail();
			
		} catch (DatabaseException objE) {
		
			assertEquals("Error with database getting all clients.", objE.getMessage());
		}		
	}
	
	
	
	
	
}































