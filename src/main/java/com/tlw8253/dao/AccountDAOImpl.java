package com.tlw8253.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Constants;
import com.tlw8253.dto.AddDTO;
import com.tlw8253.dto.EditDTO;
import com.tlw8253.model.Account;
import com.tlw8253.util.ConnectionUtility;

public class AccountDAOImpl implements GenericDAO<Account>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(AccountDAOImpl.class);

	public AccountDAOImpl() {
		super();
	}
	
	public List<Account> getAllRecords() throws SQLException {
		String sMethod = "getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");
		
		List<Account> lstAccount = new ArrayList<>();

		try (Connection conConnection = ConnectionUtility.getConnection()) {
			Statement objStatement = conConnection.createStatement();
			String sSQL = "SELECT * FROM " + csAccountTable;
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");
			
			ResultSet objResultSet = objStatement.executeQuery(sSQL);
			while (objResultSet.next()) {// data exists in the results set
				
				String sAccountNumber = objResultSet.getString(csAccountTblAccountNumber);
				String sAccountName = objResultSet.getString(csAccountTblAccountName);
				double dAccountBalance = objResultSet.getDouble(csAccountTblAccountBalance);
				int iClientId = objResultSet.getInt(csClientTblClientId);

				Account objAccount = new Account(sAccountNumber, sAccountName, dAccountBalance, iClientId);
				objLogger.info(sMethod + "Add account to list: [" + objAccount.toString() + "]");
				lstAccount.add(objAccount);
			}			
		}
		return lstAccount;
	}
	
	//
	//###
	public List<Account> getAccountsForClient(int iClientId) throws SQLException {
		String sMethod = "getAccountsForClient(): ";
		objLogger.trace(sMethod + "Entered");
		
		List<Account> lstAccount = new ArrayList<>();

		try (Connection conConnection = ConnectionUtility.getConnection()) {
			
			
			String sSQL = "SELECT * FROM " + csAccountTable + " WHERE " + csClientTblClientId + " = ?";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "] using client id: [" + iClientId + "]");
			
			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);
			objPreparedStatmnt.setInt(1, iClientId); // set passed in client id in place of ?
			
			ResultSet objResultSet = objPreparedStatmnt.executeQuery();
			while (objResultSet.next()) {// data exists in the results set
				
				String sAccountNumber = objResultSet.getString(csAccountTblAccountNumber);
				String sAccountName = objResultSet.getString(csAccountTblAccountName);
				double dAccountBalance = objResultSet.getDouble(csAccountTblAccountBalance);
				//int iClientId = objResultSet.getInt(csClientTblClientId); Don't need used passed in value

				Account objAccount = new Account(sAccountNumber, sAccountName, dAccountBalance, iClientId);
				objLogger.info(sMethod + "Add account to client's list: [" + objAccount.toString() + "]");
				lstAccount.add(objAccount);
			}			
		}
		return lstAccount;
	}
	

	//
	//### Record Identifier is used as a string for account table
	public Account getByRecordIdentifer(String sRecordIdentifier) throws SQLException{
		String sMethod = "getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");	
				
		try (Connection conConnection = ConnectionUtility.getConnection()) {
			String sSQL = "SELECT * FROM " + csAccountTable + " WHERE " + csAccountTblAccountNumber + " = ?";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "] using sRecordIdentifier: [" + sRecordIdentifier + "]");
			
			
			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);
			objPreparedStatmnt.setString(1, sRecordIdentifier); // set passed in record id in place of ?
			
			ResultSet objResultSet = objPreparedStatmnt.executeQuery();	
						
			if (objResultSet.next()) {// data exists in the results set
				
				String sAccountNumber = objResultSet.getString(csAccountTblAccountNumber);
				String sAccountName = objResultSet.getString(csAccountTblAccountName);
				double dAccountBalance = objResultSet.getDouble(csAccountTblAccountBalance);
				int iClientId = objResultSet.getInt(csClientTblClientId);

				Account objAccount = new Account(sAccountNumber, sAccountName, dAccountBalance, iClientId);
				objLogger.info(sMethod + "Account record from database: [" + objAccount.toString() + "]");
				
				return objAccount;
				
			}
			else {
				objLogger.debug(sMethod + "Account with identifier: [" + sRecordIdentifier + "] not found in database.");
				return null;
			}
		}
		
		
	}
	
	
	public Account addRecord(AddDTO objGenericAddDTO) throws SQLException{
		Account objAccount = new Account();
		
		return objAccount;
	}
	
	
	public Account editRecord(String sRecordIdentifier, EditDTO objGenericEditDTO) throws SQLException{
		Account objAccount = new Account();
		
		return objAccount;

	}
	
	
	public void deleteRecord(String sRecordIdentifier) throws SQLException{
		
	}
	
	
}
