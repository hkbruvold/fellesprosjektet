package server;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCommunication {
	private DatabaseConnection dbConn;
	
	public DatabaseCommunication(DatabaseConnection dbConn) {
		this.dbConn = dbConn;
	}

	public int query(String query) {
		try {
			makeQuery(query);
			return 0;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public int update(String update) {
		try {
			makeUpdate(update);
			return 0;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public int batchUpdate(/* TODO */) {
		// TODO
		return -2;
	}

	private void makeQuery(String query) throws ClassNotFoundException, SQLException {
		dbConn.init();
		ResultSet rs = dbConn.query(query);
		rs.beforeFirst();
		int colCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			for (int i = 1; i < colCount+1; i++) {
				System.out.print(rs.getMetaData().getColumnLabel(i) + ": ");
				System.out.println(rs.getString(i));
			}
			System.out.println();
		}
		rs.close();
		dbConn.close();
	}
	private void makeUpdate(String update) throws ClassNotFoundException, SQLException {
		dbConn.init();
		dbConn.update(update);
		dbConn.close();
	}
}
