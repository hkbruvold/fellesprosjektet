package server;

import java.util.ArrayList;
import java.util.Properties;

import data.*;

public class DatabaseMethods {
	private static final String FIELDS_ALARM = TableFields.ALARM.getFieldsString();
	private static final String FIELDS_EVENT = TableFields.EVENT.getFieldsString();
	private static final String FIELDS_GROUPS = TableFields.GROUPS.getFieldsString();
	private static final String FIELDS_IS_MEMBER_OF = TableFields.IS_MEMBER_OF.getFieldsString();
	private static final String FIELDS_IS_PARTICIPANT = TableFields.IS_PARTICIPANT.getFieldsString();
	private static final String FIELDS_NOTIFICATION = TableFields.NOTIFICATION.getFieldsString();
	private static final String FIELDS_NOTIFICATION_TO = TableFields.NOTIFICATION_TO.getFieldsString();
	private static final String FIELDS_RESERVED_ROOM = TableFields.RESERVED_ROOM.getFieldsString();
	private static final String FIELDS_ROOM = TableFields.ROOM.getFieldsString();
	private static final String FIELDS_USER = TableFields.USER.getFieldsString();

	private static final String SELECT_FROM = "SELECT %s FROM %s";
	private static final String SELECT_FROM_WHERE = "SELECT %s FROM %s WHERE %s";
	
	private DatabaseCommunication dbComm;
	
	public DatabaseMethods(DatabaseCommunication dbComm) {
		this.dbComm = dbComm;
	}
	
	public ArrayList<Alarm> queryAlarms() {
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_ALARM, "alarm"));
		for (Properties p : pl) {
			alarms.add(makeAlarm(p));
		}
		return alarms;
	}
	public Alarm queryAlarm(int alarmID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_ALARM, "alarm", "alarmID=" + alarmID));
		Properties p = pl.get(0);
		return makeAlarm(p);
	}
	private Alarm makeAlarm(Properties p) {
		Alarm alarm = new Alarm();
		String id = p.getProperty("alarmID");
		String dateTime = p.getProperty("time");
		String message = p.getProperty("message");
		String username = p.getProperty("username");
		String eventID = p.getProperty("eventID");
		
		alarm.setID(Integer.parseInt(id));
		alarm.setDate(dateTime.split(" ")[0], false);
		alarm.setTime(dateTime.split(" ")[1], false);
		alarm.setMessage(message, false);
		alarm.setOwner(queryUser(username), false);
		alarm.setEvent(queryEvent(Integer.parseInt(eventID)), false);
		return alarm;
	}
	
	public ArrayList<Event> queryEvents() {
		return null;
	}
	public Event queryEvent(int eventID) {
		return null;
	}
	
	public ArrayList<Group> queryGroups() {
		return null;
	}
	public Group queryGroup(int groupID) {
		return null;
	}
	
	public ArrayList<Notification> queryNotifications() {
		return null;
	}
	public Group queryNotification(int notificationID) {
		return null;
	}
	
	public ArrayList<Room> queryRooms() {
		return null;
	}
	public Group queryRoom(int roomID) {
		return null;
	}
	
	public ArrayList<User> queryUsers() {
		return null;
	}
	public User queryUser(String username) {
		return null;
	}
	
	
	
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		DatabaseMethods dm = new DatabaseMethods(dbComm);
		Alarm alarm = dm.queryAlarm(1);
		System.out.println(alarm);
	}
}
