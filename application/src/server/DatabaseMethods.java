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

	private static final String TABLE_ALARM = TableFields.ALARM.getTableName();
	private static final String TABLE_EVENT = TableFields.EVENT.getTableName();
	private static final String TABLE_GROUPS = TableFields.GROUPS.getTableName();
	private static final String TABLE_IS_MEMBER_OF = TableFields.IS_MEMBER_OF.getTableName();
	private static final String TABLE_IS_PARTICIPANT = TableFields.IS_PARTICIPANT.getTableName();
	private static final String TABLE_NOTIFICATION = TableFields.NOTIFICATION.getTableName();
	private static final String TABLE_NOTIFICATION_TO = TableFields.NOTIFICATION_TO.getTableName();
	private static final String TABLE_RESERVED_ROOM = TableFields.RESERVED_ROOM.getTableName();
	private static final String TABLE_ROOM = TableFields.ROOM.getTableName();
	private static final String TABLE_USER = TableFields.USER.getTableName();

	private static final String SELECT_FROM = "SELECT %s FROM %s";
	private static final String SELECT_FROM_WHERE = "SELECT %s FROM %s WHERE %s";

	private DatabaseCommunication dbComm;

	public DatabaseMethods(DatabaseCommunication dbComm) {
		this.dbComm = dbComm;
	}

	public ArrayList<Alarm> queryAlarms() {
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_ALARM, TABLE_ALARM));
		for (Properties p : pl) {
			alarms.add(makeAlarm(p));
		}
		return alarms;
	}
	public Alarm queryAlarm(int alarmID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_ALARM, TABLE_ALARM, "alarmID=" + alarmID));
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
		alarm.setDate(dateTime.split(" ")[0]);
		alarm.setTime(dateTime.split(" ")[1]);
		alarm.setMessage(message);
		alarm.setOwner(queryUser(username));
		alarm.setEvent(queryEvent(Integer.parseInt(eventID)));
		return alarm;
	}


	public ArrayList<Event> queryEvents() {
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_EVENT, TABLE_EVENT));
		for (Properties p : pl) {
			events.add(makeEvent(p));
		}
		return events;
	}
	public Event queryEvent(int eventID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_EVENT, TABLE_EVENT, "eventID=" + eventID));
		Properties p = pl.get(0);
		return makeEvent(p);
	}
	private Event makeEvent(Properties p) {
		return null; // remember to add participants!
	}


	public ArrayList<Group> queryGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_GROUPS, TABLE_GROUPS));
		for (Properties p : pl) {
			groups.add(makeGroup(p));
		}
		return groups;
	}
	public Group queryGroup(int groupID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_GROUPS, TABLE_GROUPS, "groupID=" + groupID));
		Properties p = pl.get(0);
		return makeGroup(p);
	}
	private Group makeGroup(Properties p) {
		return null; // remember to add users to the group!
	}


	public ArrayList<Notification> queryNotifications() {
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_NOTIFICATION, TABLE_NOTIFICATION));
		for (Properties p : pl) {
			notifications.add(makeNotification(p));
		}
		return notifications;
	}
	public Notification queryNotification(int notificationID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_NOTIFICATION, TABLE_NOTIFICATION, "notificationID=" + notificationID));
		Properties p = pl.get(0);
		return makeNotification(p);
	}
	private Notification makeNotification(Properties p) {
		Notification notification = new Notification();
		String id = p.getProperty("notificationID");
		String description = p.getProperty("description");
		String eventID = p.getProperty("eventID");
		
		notification.setId(Integer.parseInt(id));
		notification.setMessage(description);
		notification.setEvent(eventID != null ? queryEvent(Integer.parseInt(eventID)) : null);
		return notification;
	}


	public ArrayList<Room> queryRooms() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_ROOM, TABLE_ROOM));
		for (Properties p : pl) {
			rooms.add(makeRoom(p));
		}
		return rooms;
	}
	public Room queryRoom(int roomID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_ROOM, TABLE_ROOM, "roomID=" + roomID));
		Properties p = pl.get(0);
		return makeRoom(p);
	}
	private Room makeRoom(Properties p) {
		Room room = new Room();
		String roomId = p.getProperty("roomID");
		String size = p.getProperty("size");
		String description = p.getProperty("description");
		
		room.setId(Integer.parseInt(roomId));
		room.setSize(Integer.parseInt(size));
		room.setDescription(description);
		return room;
	}


	public ArrayList<User> queryUsers() {
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM, FIELDS_USER, TABLE_USER));
		for (Properties p : pl) {
			users.add(makeUser(p));
		}
		return users;
	}
	public User queryUser(String username) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_USER, TABLE_USER, "username=" + username));
		Properties p = pl.get(0);
		return makeUser(p);
	}
	private User makeUser(Properties p) {
		User user = new User();
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		String name = p.getProperty("name");
		String type = p.getProperty("type");
		
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setType(type);
		return user;
	}

	// TODO notification_to
	// TODO reserved_room
	
	// TODO consider: is_member_of & is_participant



	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		DatabaseMethods dm = new DatabaseMethods(dbComm);
		Alarm alarm = dm.queryAlarm(1);
		System.out.println(alarm);
	}
}
