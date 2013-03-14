package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
	private String jdbcDriver = "com.mysql.jdbc.Driver"; // should this be stored somewhere else?
	private String serverUrl;
	private String username; // temporary 
	private String password; // temporary
	private Connection connection;

	public DatabaseConnection(String serverUrl, String username, char[] password) {
		this.serverUrl = serverUrl;
		this.username = username;
		this.password = String.copyValueOf(password);
	}

	public void init() throws ClassNotFoundException, SQLException {
		Class.forName(jdbcDriver);

		Properties info = new Properties();
		info.setProperty("user", username);
		info.setProperty("password", password);
		connection = DriverManager.getConnection(serverUrl, info);
	}
	
	public void close() throws SQLException {
		connection.close();
	}

	public ResultSet query(String sql) throws SQLException, ClassNotFoundException {
		Statement st = connection.createStatement();
		return st.executeQuery(sql);
	}
	
	public void update(String sql) throws SQLException {
		Statement st = connection.createStatement();
		st.executeUpdate(sql);
	}
	
	public PreparedStatement preparedStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
}
