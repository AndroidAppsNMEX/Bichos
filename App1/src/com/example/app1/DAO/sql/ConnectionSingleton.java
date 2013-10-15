package com.example.app1.DAO.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.driver.OracleDriver;

public class ConnectionSingleton {

	private static Connection connection;
	private static String url = "jdbc:oracle:thin:"
			+ "@carlos-e8890517:1521:xe";

	private ConnectionSingleton() {
		// TODO Auto-generated constructor stub

	}

	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Properties prop = new Properties();
		if (connection == null) {
			prop.setProperty("user", "xarly");
			prop.setProperty("password", "neo");
			DriverManager.registerDriver(new OracleDriver());
			connection = DriverManager.getConnection(url, prop);
		}
		return connection;
	}

	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}
}
