package server;

import java.util.ArrayList;
import java.util.Properties;

import data.*;

public class DatabaseMethods {
	private static final String FIELDS_ALARM = "alarmID, time";
	private static final String FIELDS_ALARM_FOR_APPOINTMENT = "appointmentID, alarmID";
	private static final String FIELDS_APPOINTMENT = "appointmentID, startDateTime, endDateTime, location, description";
	private static final String FIELDS_GROUPS = "groupID, groupname, description";
	private static final String FIELDS_IS_MEMBER_OF = "username, groupID";
	private static final String FIELDS_IS_OWNER = "username, appointmentID";
	private static final String FIELDS_IS_PARTICIPANT = "username, appointmentID, status";
	private static final String FIELDS_NOTIFICATION = "notificationID, description, appointmentID";
	private static final String FIELDS_NOTIFICATION_TO = "username, notificationID";
	private static final String FIELDS_OWNED_BY_USER = "username, alarmID";
	private static final String FIELDS_RESERVED_ROOM = "appointmentID, roomID";
	private static final String FIELDS_ROOM = "roomID, size, description";
	private static final String FIELDS_USER = "username, password, name, type";

	private static final String SELECT_FROM = "SELECT %s FROM %s";
	private static final String SELECT_FROM_WHERE = "SELECT %s FROM %s WHERE %s";
	
	public static ArrayList<Alarm> queryAlarm(DatabaseCommunication dbComm) {
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		
		return alarms;
	}
	public static Alarm queryAlarm(DatabaseCommunication dbComm, int alarmID) {
		Alarm alarm = new Alarm();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_ALARM, "alarm", "alarmID=" + alarmID));
		Properties p = pl.get(0);
		
		String id = p.getProperty("alarmID");
		String dateTime = p.getProperty("time");
		
		alarm.setID(Integer.parseInt(id));
		alarm.setDate(dateTime.split(" ")[0]);
		alarm.setMessage("TODO! Not stored in database");
		alarm.setTime(dateTime.split(" ")[1]);
		
		return alarm;
	}
	
	
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Alarm alarm = queryAlarm(dbComm, 1);
		System.out.println(alarm);
	}
}
