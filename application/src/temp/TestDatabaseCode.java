package temp;

import server.database.DatabaseCommunication;
import server.database.DatabaseConnection;
import server.database.Update;

public class TestDatabaseCode {
	private Update update;

	public TestDatabaseCode() {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		this.update = new Update(dbComm);
		fillDatabase();
	}

	private void fillDatabase() {
		update.startVersion();
		insertUsers();
		insertGroups();
		insertRooms();
		insertEvents();
		insertAlarms();
		insertNotifications();
	}
	private void insertUsers() {
		update.insertUser(TestObjects.getUser00());
		update.insertUser(TestObjects.getUser01());
		update.insertUser(TestObjects.getUser02());
	}
	private void insertGroups() {
		update.insertGroup(TestObjects.getGroup00());
		update.insertGroup(TestObjects.getGroup01());
		update.insertGroup(TestObjects.getGroup02());
	}
	private void insertRooms() {
		update.insertRoom(TestObjects.getRoom00());
		update.insertRoom(TestObjects.getRoom01());
		update.insertRoom(TestObjects.getRoom02());
	}
	private void insertEvents() {
		update.insertEvent(TestObjects.getAppointment00());
		update.insertEvent(TestObjects.getAppointment01());
		update.insertEvent(TestObjects.getMeeting00());
		update.insertEvent(TestObjects.getMeeting01());
	}
	private void insertAlarms() {
		update.insertAlarm(TestObjects.getAlarm00());
		update.insertAlarm(TestObjects.getAlarm01());
	}
	private void insertNotifications() {
		update.insertNotification(TestObjects.getNotification00());
		update.insertNotification(TestObjects.getNotification01());
	}


	public static void main(String[] args) {
		new TestDatabaseCode();
	}
}
