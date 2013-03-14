package server;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCommunication {
	private DatabaseConnection dbConn;
	// TODO fields?
	
	public DatabaseCommunication(DatabaseConnection dbConn) {
		this.dbConn = dbConn;
	}

	// TODO
	
	public void /* result */ query(String query) {
		try {
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void /* status */ insert(/* TODO */) { // insert single element to database
		// TODO
	}
	public void /* status */ insertBatch(/* TODO */) { // batch insert (multiple items) to database
		// TODO
	}
	// TODO metod(s): change/update
}
