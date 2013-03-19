package server.database;

import java.util.ArrayList;
import java.util.Properties;

import temp.TestObjects;

import data.*;

public class Query {
	private static final String TABLE_ALARM = TableFields.ALARM.getTableName();
	private static final String TABLE_EVENT = TableFields.EVENT.getTableName();
	private static final String TABLE_GROUPS = TableFields.GROUPS.getTableName();
	private static final String TABLE_IS_MEMBER_OF = TableFields.IS_MEMBER_OF.getTableName();
	private static final String TABLE_IS_OWNER = TableFields.IS_OWNER.getTableName();
	private static final String TABLE_IS_PARTICIPANT = TableFields.IS_PARTICIPANT.getTableName();
	private static final String TABLE_NOTIFICATION = TableFields.NOTIFICATION.getTableName();
	private static final String TABLE_NOTIFICATION_FOR_EVENT = TableFields.NOTIFICATION_FOR_EVENT.getTableName();
	private static final String TABLE_NOTIFICATION_TO = TableFields.NOTIFICATION_TO.getTableName();
	private static final String TABLE_RESERVED_ROOM = TableFields.RESERVED_ROOM.getTableName();
	private static final String TABLE_ROOM = TableFields.ROOM.getTableName();
	private static final String TABLE_USER = TableFields.USER.getTableName();
	
	private static final String FIELDS_ALARM = TableFields.ALARM.getFieldsString();
	private static final String FIELDS_EVENT = TableFields.EVENT.getFieldsString();
	private static final String FIELDS_GROUPS = TableFields.GROUPS.getFieldsString();
	private static final String FIELDS_IS_MEMBER_OF = TableFields.IS_MEMBER_OF.getFieldsString();
	private static final String FIELDS_IS_OWNER = TableFields.IS_OWNER.getFieldsString();
	private static final String FIELDS_IS_PARTICIPANT = TableFields.IS_PARTICIPANT.getFieldsString();
	private static final String FIELDS_NOTIFICATION = TableFields.NOTIFICATION.getFieldsString();
	private static final String FIELDS_NOTIFICATION_FOR_EVENT = TableFields.NOTIFICATION_FOR_EVENT.getFieldsString();
	private static final String FIELDS_NOTIFICATION_TO = TableFields.NOTIFICATION_TO.getFieldsString();
	private static final String FIELDS_RESERVED_ROOM = TableFields.RESERVED_ROOM.getFieldsString();
	private static final String FIELDS_ROOM = TableFields.ROOM.getFieldsString();
	private static final String FIELDS_USER = TableFields.USER.getFieldsString();

	private static final String SELECT_FROM = "SELECT %s FROM %s";
	private static final String SELECT_FROM_WHERE = "SELECT %s FROM %s WHERE %s";

	private static final String BIT_FALSE = "0";
	private static final String BIT_TRUE = "1";

	private static final String PARTICIPANT_STATUS_INVITED = "0";
	private static final String PARTICIPANT_STATUS_ACCEPTED = "1";
	private static final String PARTICIPANT_STATUS_DECLINED = "2";
	
	private User currentUser;
	private DatabaseCommunication dbComm;

	public Query(User currentUser, DatabaseCommunication dbComm) {
		this.currentUser = currentUser;
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
	private Alarm queryAlarm(User user, int eventID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_ALARM, TABLE_ALARM, "username='" + user.getUsername() + "', eventID=" + eventID));
		Properties p = pl.get(0);
		return makeAlarm(p);
	}
	private Alarm makeAlarm(Properties p) {
		Alarm alarm = new Alarm();
		String time = p.getProperty("time");
		String message = p.getProperty("message");
		String username = p.getProperty("username");
		String eventID = p.getProperty("eventID");

		alarm.setTimeBefore(time);
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
		Event event = null;
		String id = p.getProperty("eventID");
		String start = p.getProperty("startDataTime");
		String end = p.getProperty("endDateTime");
		String location = p.getProperty("location");
		String description = p.getProperty("description");
		String isMeeting = p.getProperty("isMeeting");
		
		if (isMeeting.equals(BIT_FALSE)) {
			event = new Appointment();
		} else if (isMeeting.equals(BIT_TRUE)) {
			event = new Meeting();
		}
		event.setId(Integer.parseInt(id));
		event.setStartDateTime(start);
		event.setEndDateTime(end);
		event.setLocation(location);
		event.setDescription(description);
		event.setAlarm(queryAlarm(currentUser, Integer.parseInt(id)));
		// TODO remember room reservation?
		if (event instanceof Appointment) {
			((Appointment)event).setOwner(queryOwner(Integer.parseInt(id)));
		} else if (event instanceof Meeting) {
			((Meeting)event).setLeader(queryOwner(Integer.parseInt(id)));
			ArrayList<User> invited = queryParticipants(Integer.parseInt(id), PARTICIPANT_STATUS_INVITED);
			ArrayList<User> accepted = queryParticipants(Integer.parseInt(id), PARTICIPANT_STATUS_ACCEPTED);
			ArrayList<User> declined = queryParticipants(Integer.parseInt(id), PARTICIPANT_STATUS_DECLINED);
			((Meeting)event).setUsersInvited(invited);
			((Meeting)event).setUsersAccepted(accepted);
			((Meeting)event).setUsersDeclined(declined);
		}
		return event; 
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
		Group group = new Group();
		String id = p.getProperty("groupID");
		String groupname = p.getProperty("groupname");
		String description = p.getProperty("description");
		
		group.setId(Integer.parseInt(id));
		group.setName(groupname);
		group.setDescription(description);
		ArrayList<User> members = queryMembers(Integer.parseInt(id));
		group.addMembers(members);
		return group;
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
	private Notification makeNotification(Properties p) { // TODO get recipient
		Notification notification = new Notification();
		String id = p.getProperty("notificationID");
		String description = p.getProperty("description");
		
		notification.setId(Integer.parseInt(id));
		notification.setMessage(description);
		Event event = queryNotificationEvent(Integer.parseInt(id));
		notification.setEvent(event);
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
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_USER, TABLE_USER, "username='" + username + "'"));
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

	private User queryOwner(int eventID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_IS_OWNER, TABLE_IS_OWNER, "eventID=" + eventID));
		Properties p = pl.get(0);
		return queryUser(p.getProperty("username"));
	}
	
	private ArrayList<User> queryParticipants(int eventID, String status) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_IS_PARTICIPANT, TABLE_IS_PARTICIPANT, "eventID=" + eventID + ", status=" + status));
		ArrayList<User> users = new ArrayList<User>();
		for (Properties p : pl) {
			users.add(queryUser(p.getProperty("username")));
		}
		return users;
	}
	
	private ArrayList<User> queryMembers(int groupID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_IS_MEMBER_OF, TABLE_IS_MEMBER_OF, "groupID=" + groupID));
		ArrayList<User> members = new ArrayList<User>();
		for (Properties p : pl) {
			members.add(queryUser(p.getProperty("username")));
		}
		return members;
	}
	
	private Event queryNotificationEvent(int notificationID) {
		ArrayList<Properties> pl = dbComm.query(String.format(SELECT_FROM_WHERE, FIELDS_NOTIFICATION_FOR_EVENT, TABLE_NOTIFICATION_FOR_EVENT, "notificationID=" + notificationID));
		Properties p = pl.get(0);
		Event event = queryEvent(Integer.parseInt(p.getProperty("eventID")));
		return event;
	}


	// TODO notification_to
	
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Query query = new Query(TestObjects.getUser00(), dbComm);
		Event event = query.queryEvent(2);
		System.out.println(event);
	}
	
}
