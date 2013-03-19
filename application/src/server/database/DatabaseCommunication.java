package server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseCommunication {
	private DatabaseConnection dbConn;
	
	public DatabaseCommunication(DatabaseConnection dbConn) {
		this.dbConn = dbConn;
	}

	public ArrayList<Properties> query(String query) {
		ArrayList<Properties> result = null;
		try {
			result = makeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public int update(String update) {
		try {
			int id = makeUpdate(update);
			return id;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public int batchUpdate(/* TODO */) {
		// TODO
		return -2;
	}

	private ArrayList<Properties> makeQuery(String query) throws ClassNotFoundException, SQLException {
		dbConn.init();
		ArrayList<Properties> result = new ArrayList<Properties>();
		ResultSet rs = dbConn.query(query);
		rs.beforeFirst();
		int colCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			Properties p = new Properties();
			for (int i = 1; i < colCount+1; i++) {
				String label = rs.getMetaData().getColumnLabel(i);
				String data = rs.getString(i);
				p.setProperty(label, (data != null ? data : ""));
			}
			result.add(p);
		}
		rs.close();
		dbConn.close();
		return result;
	}
	private int makeUpdate(String update) throws ClassNotFoundException, SQLException {
		dbConn.init();
		int id = dbConn.update(update);
		dbConn.close();
		return id;
	}
}
