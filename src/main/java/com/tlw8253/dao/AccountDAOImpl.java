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
import com.tlw8253.dto.AccountAddDTO;
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
				String sAccountName = objResultSet.getString(csAccountTblAccountType);
				double dAccountBalance = objResultSet.getDouble(csAccountTblAccountBalance);
				int iClientId = objResultSet.getInt(csClientTblClientId);

				Account objAccount = new Account(sAccountNumber, sAccountName, dAccountBalance, iClientId);
				objLogger.debug(sMethod + "Add account to list: [" + objAccount.toString() + "]");
				lstAccount.add(objAccount);
			}
		}
		return lstAccount;
	}

	//
	// ###
	public List<Account> getAccountsForClient(int iClientId) throws SQLException {
		String sMethod = "getAccountsForClient(): ";
		objLogger.trace(sMethod + "Entered");

		List<Account> lstAccount = new ArrayList<>();

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "SELECT * FROM " + csAccountTable + " WHERE " + csClientTblClientId + " = ?";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "] using client id: [" + iClientId + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);
			objPreparedStatmnt.setInt(1, iClientId); // set passed in client id in place of ?
			objLogger.debug(sMethod + "objPreparedStatmnt: [" + objPreparedStatmnt.toString() + "]");

			ResultSet objResultSet = objPreparedStatmnt.executeQuery();
			while (objResultSet.next()) {// data exists in the results set

				String sAccountNumber = objResultSet.getString(csAccountTblAccountNumber);
				String sAccountName = objResultSet.getString(csAccountTblAccountType);
				double dAccountBalance = objResultSet.getDouble(csAccountTblAccountBalance);
				// int iClientId = objResultSet.getInt(csClientTblClientId); Don't need used
				// passed in value

				Account objAccount = new Account(sAccountNumber, sAccountName, dAccountBalance, iClientId);
				objLogger.debug(sMethod + "Add account to client's list: [" + objAccount.toString() + "]");
				lstAccount.add(objAccount);
			}
		}
		return lstAccount;
	}

	
	//
	// ###
	public List<Account> getAccountsForClientInRange(int iClientId, int iUpperRange, int iLowerRange) throws SQLException {
		String sMethod = "getAccountsForClientInRange(): ";
		objLogger.trace(sMethod + "Entered");

		List<Account> lstAccount = new ArrayList<>();

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			
			//SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance >= 400.00 AND acct_balance <= 2000.00 ORDER BY acct_balance;
			/*Both SQL statements work as long as setInt is not used for when using the first hardcoded statement
			String sSQL = "SELECT * FROM " + csAccountTable + " WHERE " + csClientTblClientId + " = " + iClientId
							+ " AND " + csAccountTblAccountBalance + " >= " + iLowerRange 
							+ " AND " + csAccountTblAccountBalance + " <= " + iUpperRange 
							+ " ORDER BY " + csAccountTblAccountBalance;		
			*/

			String sSQL2 = "SELECT * FROM " + csAccountTable + " WHERE " + csClientTblClientId + " = ? "
					+ " AND " + csAccountTblAccountBalance + " >= ? " 
					+ " AND " + csAccountTblAccountBalance + " <= ? "
					+ " ORDER BY " + csAccountTblAccountBalance;		

			
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL2 + "] using client id: [" + iClientId + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL2);
			objPreparedStatmnt.setInt(1, iClientId); // set passed in client id in place of ?
			objPreparedStatmnt.setInt(2, iLowerRange); // set passed in lower range in place of ?
			objPreparedStatmnt.setInt(3, iUpperRange); // set passed in upper range in place of ?
			
			objLogger.debug(sMethod + "objPreparedStatmnt: [" + objPreparedStatmnt.toString() + "]");

			ResultSet objResultSet = objPreparedStatmnt.executeQuery();
			while (objResultSet.next()) {// data exists in the results set

				String sAccountNumber = objResultSet.getString(csAccountTblAccountNumber);
				String sAccountName = objResultSet.getString(csAccountTblAccountType);
				double dAccountBalance = objResultSet.getDouble(csAccountTblAccountBalance);
				// int iClientId = objResultSet.getInt(csClientTblClientId); Don't need used
				// passed in value

				Account objAccount = new Account(sAccountNumber, sAccountName, dAccountBalance, iClientId);
				objLogger.debug(sMethod + "Add account to client's list: [" + objAccount.toString() + "]");
				lstAccount.add(objAccount);
			}
		}
		return lstAccount;
	}

	
	
	//
	// ### Record Identifier is used as a string for account table
	public Account getByRecordIdentifer(String sRecordIdentifier) throws SQLException {
		String sMethod = "getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");

		try (Connection conConnection = ConnectionUtility.getConnection()) {
			String sSQL = "SELECT * FROM " + csAccountTable + " WHERE " + csAccountTblAccountNumber + " = ?";
			objLogger.debug(
					sMethod + "sSQL statement: [" + sSQL + "] using sRecordIdentifier: [" + sRecordIdentifier + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);
			objPreparedStatmnt.setString(1, sRecordIdentifier); // set passed in record id in place of ?
			objLogger.debug(sMethod + "objPreparedStatmnt: [" + objPreparedStatmnt.toString() + "]");

			ResultSet objResultSet = objPreparedStatmnt.executeQuery();

			if (objResultSet.next()) {// data exists in the results set

				String sAccountNumber = objResultSet.getString(csAccountTblAccountNumber);
				String sAccountName = objResultSet.getString(csAccountTblAccountType);
				double dAccountBalance = objResultSet.getDouble(csAccountTblAccountBalance);
				int iClientId = objResultSet.getInt(csClientTblClientId);

				Account objAccount = new Account(sAccountNumber, sAccountName, dAccountBalance, iClientId);
				objLogger.debug(sMethod + "Account record from database: [" + objAccount.toString() + "]");

				return objAccount;

			} else {
				objLogger
						.debug(sMethod + "Account with identifier: [" + sRecordIdentifier + "] not found in database.");
				return null;
			}
		}
	}

	//
	// ### not using the addRecord from the interface, we want to use the
	// Account DTO and not the AddDTO
	public Account addRecord(AccountAddDTO objAccountAddDTO) throws SQLException {
		String sMethod = "addRecord(AccountAddDTO): ";
		objLogger.trace(sMethod + "Entered");

		String sAccountNumber = objAccountAddDTO.getAccountNumber();
		String sAccountType = objAccountAddDTO.getAccountType();
		double dAccountBalance = objAccountAddDTO.getAccountBalanceAsDouble();
		int iClientId = objAccountAddDTO.getClientIdAsInt();

		try (Connection conConnection = ConnectionUtility.getConnection()) {

			String sSQL = "INSERT INTO " + csAccountTable + " (" + csAccountTblAccountNumber + ", "
					+ csAccountTblAccountType + ", " + csAccountTblAccountBalance + ", " + csClientTblClientId
					+ ") VALUES (?, ?, ?, ?)";

			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);
			objPreparedStatmnt.setString(1, sAccountNumber);
			objPreparedStatmnt.setString(2, sAccountType);
			objPreparedStatmnt.setDouble(3, dAccountBalance);
			objPreparedStatmnt.setInt(4, iClientId);

			objLogger.debug(sMethod + "objPreparedStatmnt: [" + objPreparedStatmnt.toString() + "]");

			// Use executeUpdate when working with INSERT, UPDATE, or DELETE
			int iRecsAdded = objPreparedStatmnt.executeUpdate();

			if (iRecsAdded != 1) {
				String sMsg = "Account record not added to database: [" + objAccountAddDTO.toStringByKeys() + "]";
				objLogger.debug(sMethod + sMsg);
				throw new SQLException(sMsg);
			} else {

				Account objAccount = new Account(sAccountNumber, sAccountType, dAccountBalance, iClientId);
				objLogger.debug(
						sMethod + "Account record created. Retrieved from database: [" + objAccount.toString() + "]");

				return objAccount;
			}

		}
	}

	public Account editRecord(String sRecordIdentifier, EditDTO objGenericEditDTO) throws SQLException {
		Account objAccount = new Account();

		return objAccount;

	}

	public void deleteRecord(String sRecordIdentifier) throws SQLException {

	}

	@Override
	public Account addRecord(AddDTO objGenericAddDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
