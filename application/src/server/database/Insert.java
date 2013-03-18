package server.database;

import data.*;

import temp.TestObjects;

public class Insert {
	private User currentUser;
	private DatabaseCommunication dbComm;

	public Insert(User currentUser, DatabaseCommunication dbComm) {
		this.currentUser = currentUser;
		this.dbComm = dbComm;
	}

	// TODO add insert methods
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Insert insert = new Insert(TestObjects.getUser00(), dbComm);
	}
	
}
