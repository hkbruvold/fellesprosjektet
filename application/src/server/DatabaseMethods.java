package server;

import java.util.ArrayList;
import java.util.Properties;

import data.*;

public class DatabaseMethods {
	private static final String FIELDS_ALARM = "alarmID, time, message, username, appointmentID";
	private static final String FIELDS_APPOINTMENT = "appointmentID, startDateTime, endDateTime, location, description";
	private static final String FIELDS_GROUPS = "groupID, groupname, description";
	private static final String FIELDS_IS_MEMBER_OF = "username, groupID";
	private static final String FIELDS_IS_PARTICIPANT = "username, appointmentID, status";
	private static final String FIELDS_NOTIFICATION = "notificationID, description, appointmentID";
	private static final String FIELDS_NOTIFICATION_TO = "username, notificationID";
	private static final String FIELDS_RESERVED_ROOM = "appointmentID, roomID";
	private static final String FIELDS_ROOM = "roomID, size, description";
	private static final String FIELDS_USER = "username, password, name, type";

	private static final String SELECT_FROM = "SELECT %s FROM %s";
	private static final String SELECT_FROM_WHERE = "SELECT %s FROM %s WHERE %s";
	
	public static ArrayList<Alarm> queryAlarm(DatabaseCommunication dbComm) {
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_ALARM, "alarm"));
		for (Properties p : pl) {
			alarms.add(makeAlarm(p));
		}
		return alarms;
	}
	public static Alarm queryAlarm(DatabaseCommunication dbComm, int alarmID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_ALARM, "alarm", "alarmID=" + alarmID));
		Properties p = pl.get(0);
		return makeAlarm(p);
	}
	private static Alarm makeAlarm(Properties p) {
		Alarm alarm = new Alarm();
		String id = p.getProperty("alarmID");
		String dateTime = p.getProperty("time");
		String message = p.getProperty("message");
		String username = p.getProperty("username");
		String appointmentID = p.getProperty("appointmentID");
		
		alarm.setID(Integer.parseInt(id));
		alarm.setDate(dateTime.split(" ")[0]);
		alarm.setTime(dateTime.split(" ")[1]);
		alarm.setMessage(message);
//		alarm.set
		return alarm;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Alarm alarm = queryAlarm(dbComm, 1);
		System.out.println(alarm);
	}
}
