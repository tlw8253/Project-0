package com.tlw8253.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.application.Constants;
import com.tlw8253.dto.AddDTO;
import com.tlw8253.dto.GenericEditDTO;
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
				objLogger.info(sMethod + "Add client to list: [" + objAccount.toString() + "]");
				lstAccount.add(objAccount);
			}			
		}
		return lstAccount;
	}
	
	public Account getByRecordId(int iRecordId) throws SQLException{
		Account objAccount = new Account();
		
		return objAccount;
	}
	
	
	public Account addRecord(AddDTO objGenericAddDTO) throws SQLException{
		Account objAccount = new Account();
		
		return objAccount;
	}
	
	
	public Account editRecord(int iRecordId, GenericEditDTO objGenericEditDTO) throws SQLException{
		Account objAccount = new Account();
		
		return objAccount;

	}
	
	
	public void deleteRecord(int iRecordId) throws SQLException{
		
	}
	
	
}
