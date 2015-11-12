package com.inno.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Database {

	private final String DRIVERNAME = "org.sqlite.JDBC";
	private final String WORKPATH = "jdbc:sqlite:";
	private Connection connection = null;

	public Database() throws SQLException, ClassNotFoundException {
		// It needs TO make sure the driver is installed.
		Class.forName(DRIVERNAME);
		// Create a connection to the database.
		connection = DriverManager.getConnection(WORKPATH + "ACCOUNTS.sqlite");
		System.out.println("DatabaseHandler: Successfully connected to the server!");

		// Create a table if it DOES not EXIST.
		Statement statement = connection.createStatement();
		String sqlString = "CREATE TABLE IF NOT EXISTS ACCOUNTS" + "(ID VARCHAR(40) PRIMARY KEY NOT NULL,"
				+ "VALUE INT DEFAULT 0)";

		statement.executeUpdate(sqlString);
		statement.close();
		System.out.println("DatabaseHandler: Table created successfully.");
	}

	public boolean doesAccountExsists(String id) {
		try {
			this.getOneAccount(id);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public void addDirect(String id, int value) throws SQLException {
		String sqlStringAdd = "INSERT INTO ACCOUNTS (ID, VALUE)" + "VALUES (" + id + "," + value + ")";
		Statement statementAdd;
		statementAdd = connection.createStatement();
		statementAdd.executeUpdate(sqlStringAdd);
		statementAdd.close();
	}

	public int getOneAccount(String id) throws SQLException {
		Statement statementGetOneAccount = connection.createStatement();
		String sqlStringGetOneAccount = "SELECT * FROM ACCOUNTS WHERE ID = " + id;
		ResultSet resultOneAccount = statementGetOneAccount.executeQuery(sqlStringGetOneAccount);
		resultOneAccount.next();
		Account account = new Account();
		account.id = resultOneAccount.getString("ID");
		account.value = resultOneAccount.getInt("VALUE");
		statementGetOneAccount.close();
		return account.value;
	}

	public List<Account> getAllAccounts() throws SQLException {
		Statement statementGetAllAccounts = connection.createStatement();
		String sqlStringGetAllAccounts = "SELECT * FROM ACCOUNTS";
		ResultSet resultAllAccounts = statementGetAllAccounts.executeQuery(sqlStringGetAllAccounts);
		List<Account> accounts = new LinkedList<Account>();
		while (resultAllAccounts.next()) {
			Account account = new Account();
			account.id = resultAllAccounts.getString("ID");
			account.value = resultAllAccounts.getInt("VALUE");
			accounts.add(account);
		}

		statementGetAllAccounts.close();
		return accounts;

	}

	public void setValueDirect(String id, int value) throws SQLException {
		String sqlStringSetMoney = "UPDATE ACCOUNTS set VALUE =" + value + " WHERE ID = " + id;

		Statement statementAdd;
		statementAdd = connection.createStatement();
		statementAdd.executeUpdate(sqlStringSetMoney);
		statementAdd.close();
	}

	public void close() throws SQLException {
		this.connection.close();
	}
}
