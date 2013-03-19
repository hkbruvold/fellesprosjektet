package server.database;

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
	
	public int update(String sql) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet rs = null;
		st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		rs = st.getGeneratedKeys();
		int id = 0;
		if (rs != null) {
			rs.beforeFirst();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
		}
		return id;
	}
	
	public PreparedStatement preparedStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
}
