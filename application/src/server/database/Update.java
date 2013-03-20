package server.database;

import data.*;

import temp.TestObjects;

public class Update {
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

	private static final String INSERT_INTO_VALUES = "INSERT INTO %s (%s) VALUES (%s)"; // "tableName", "fields", "values"
	private static final String UPDATE_SET_WHERE = "UPDATE %s SET %s WHERE %s"; // "tableName", "field1=value1, field2=value2 ...", "condition"
	private static final String DELETE_FROM_WHERE = "DELETE FROM %s WHERE %s"; // "tableName", "condition"
	
	private static final String AND = " AND ";

	private static final String BIT_FALSE = "0";
	private static final String BIT_TRUE = "1";

	private static final String DEFAULT_NEW_ID = "0";

	private static final String PARTICIPANT_STATUS_INVITED = "0";
	private static final String PARTICIPANT_STATUS_ACCEPTED = "1";
	private static final String PARTICIPANT_STATUS_DECLINED = "2";

	// 	Please ignore! I'm using this as a shortcut to open classes. Query TableFields Event TestObjects

	private User currentUser;
	private DatabaseCommunication dbComm;
	private boolean debugging = false;

	public Update(User currentUser, DatabaseCommunication dbComm) {
		this.currentUser = currentUser;
		this.dbComm = dbComm;
	}

	public int insertAlarm(Alarm alarm) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(alarm.getTimeBefore()).append("'").append(", ");
		values.append("'").append(alarm.getMessage()).append("'").append(", ");
		values.append("'").append(alarm.getOwner().getUsername()).append("'").append(", ");
		values.append(alarm.getEvent().getId()).append(" ");
		int id = insertObject(TABLE_ALARM, FIELDS_ALARM, values.toString());
		return id;
	}
	public void updateAlarm(Alarm alarm) {
		StringBuilder values = new StringBuilder();
		String[] fields = TableFields.ALARM.getFields();
		values.append(fields[0]).append("='").append(alarm.getTimeBefore()).append("', ");
		values.append(fields[1]).append("='").append(alarm.getMessage()).append("', ");
		values.append(fields[2]).append("='").append(alarm.getOwner().getUsername()).append("', ");
		values.append(fields[3]).append("=").append(alarm.getEvent().getId()).append(" ");
		StringBuilder condition = new StringBuilder();
		condition.append("username=").append("'").append(alarm.getOwner().getUsername()).append("'").append(AND);
		condition.append("eventID=").append(alarm.getEvent().getId()).append(" ");
		updateObject(TABLE_ALARM, values.toString(), condition.toString());
	}
	public void deleteAlarm(Alarm alarm) {
		StringBuilder condition = new StringBuilder();
		condition.append("username=").append("'").append(alarm.getOwner().getUsername()).append("'").append(AND);
		condition.append("eventID=").append(alarm.getEvent().getId()).append(" ");
		deleteObject(TABLE_ALARM, condition.toString());
	}

	public int insertEvent(Event event) {
		StringBuilder values = new StringBuilder();
		values.append(DEFAULT_NEW_ID).append(", ");
		values.append("'").append(event.getStartDateTime()).append("'").append(", ");
		values.append("'").append(event.getEndDateTime()).append("'").append(", ");
		values.append("'").append(event.getLocation()).append("'").append(", ");
		values.append("'").append(event.getDescription()).append("'").append(", ");
		if (event instanceof Appointment) {
			values.append(BIT_FALSE).append(" ");
		} else if (event instanceof Meeting) {
			values.append(BIT_TRUE).append(" ");
		}
		int id = insertObject(TABLE_EVENT, FIELDS_EVENT, values.toString());
		event.setId(id);
		if (event.getRoom() != null) {
			insertReservedRoom(event, event.getRoom());
		}
		insertIsOwner(event.getUser(), event);
		if (event instanceof Meeting) {
			Meeting meeting = (Meeting)event;
			for (User user : meeting.getUsersInvited()) {
				insertIsParticipant(user, event, PARTICIPANT_STATUS_INVITED);
			}
			for (User user : meeting.getUsersAccepted()) {
				insertIsParticipant(user, event, PARTICIPANT_STATUS_ACCEPTED);
			}
			for (User user : meeting.getUsersDeclined()) {
				insertIsParticipant(user, event, PARTICIPANT_STATUS_DECLINED);
			}
		}
		return id;
	}
	public void updateEvent(Event event) {
		StringBuilder values = new StringBuilder();
		// TODO
		StringBuilder condition = new StringBuilder();
		condition.append("eventID=").append(event.getId());
		updateObject(TABLE_EVENT, values.toString(), condition.toString());
	}
	public void deleteEvent(Event event) {
		StringBuilder condition = new StringBuilder();
		condition.append("eventID=").append(event.getId());
		deleteObject(TABLE_EVENT, condition.toString());
	}

	public void insertGroup(Group group) {
		StringBuilder values = new StringBuilder();
		values.append(group.getId()).append(", ");
		values.append("'").append(group.getName()).append("'").append(", ");
		values.append("'").append(group.getDescription()).append("'").append(" ");
		int id = insertObject(TABLE_GROUPS, FIELDS_GROUPS, values.toString());
		group.setId(id);
		for (User user : group.getMembers()) {
			insertIsMemberOf(user, group);
		}
	}
	public void updateGroup(Group group) {
		StringBuilder values = new StringBuilder();
		// TODO
		StringBuilder condition = new StringBuilder();
		condition.append("groupID=").append(group.getId());
		updateObject(TABLE_GROUPS, values.toString(), condition.toString());
	}
	public void deleteGroup(Group group) {
		StringBuilder condition = new StringBuilder();
		condition.append("groupID=").append(group.getId());
		deleteObject(TABLE_GROUPS, condition.toString());
	}

	public void insertNotification(Notification notification) {
		StringBuilder values = new StringBuilder();
		values.append(notification.getId()).append(", ");
		values.append("'").append(notification.getMessage()).append("'").append(" ");
		int id = insertObject(TABLE_NOTIFICATION, FIELDS_NOTIFICATION, values.toString());
		notification.setId(id);
		insertNotificationTo(notification.getRecipient(), notification);
		if (notification.getEvent() != null) {
			insertNotificationForEvent(notification, notification.getEvent());
		}
	}
	public void updateNotification(Notification notification) {
		StringBuilder values = new StringBuilder();
		// TODO
		StringBuilder condition = new StringBuilder();
		condition.append("notificationID=").append(notification.getId());
		updateObject(TABLE_NOTIFICATION, values.toString(), condition.toString());
	}
	public void deleteNotification(Notification notification) {
		StringBuilder condition = new StringBuilder();
		condition.append("notificationID=").append(notification.getId());
		deleteObject(TABLE_NOTIFICATION, condition.toString());
	}

	public void insertRoom(Room room) {
		StringBuilder values = new StringBuilder();
		values.append(room.getId()).append(", ");
		values.append(room.getSize()).append(", ");
		values.append("'").append(room.getDescription()).append("'").append(" ");
		insertObject(TABLE_ROOM, FIELDS_ROOM, values.toString());
	}
	public void updateRoom(Room room) {
		StringBuilder values = new StringBuilder();
		// TODO
		StringBuilder condition = new StringBuilder();
		condition.append("roomID=").append(room.getId());
		updateObject(TABLE_ROOM, values.toString(), condition.toString());
	}
	public void deleteRoom(Room room) {
		StringBuilder condition = new StringBuilder();
		condition.append("roomID=").append(room.getId());
		deleteObject(TABLE_ROOM, condition.toString());
	}

	public void insertUser(User user) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append("'").append(user.getPassword()).append("'").append(", ");
		values.append("'").append(user.getName()).append("'").append(", ");
		values.append("'").append(user.getType()).append("'").append(" ");
		insertObject(TABLE_USER, FIELDS_USER, values.toString());
	}
	public void updateUser(User user) {
		StringBuilder values = new StringBuilder();
		// TODO
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'");
		updateObject(TABLE_USER, values.toString(), condition.toString());
	}
	public void deleteUser(User user) {
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'");
		deleteObject(TABLE_USER, condition.toString());
	}

	public void insertIsMemberOf(User user, Group group) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(group.getId()).append(" ");
		insertObject(TABLE_IS_MEMBER_OF, FIELDS_IS_MEMBER_OF, values.toString());
	}
	// Should not have update
	public void deleteIsMemberOf(User user, Group group) {
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'").append(AND);
		condition.append("groupID=").append(group.getId());
		deleteObject(TABLE_IS_MEMBER_OF, condition.toString());
	}

	public void insertIsOwner(User user, Event event) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(event.getId()).append(" ");
		insertObject(TABLE_IS_OWNER, FIELDS_IS_OWNER, values.toString());
	}
	// Do we want update?
	// Should not have delete (delete the event)

	public void insertIsParticipant(User user, Event event, String status) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(event.getId()).append(", ");
		values.append("'").append(status).append("'").append(" ");
		insertObject(TABLE_IS_PARTICIPANT, FIELDS_IS_PARTICIPANT, values.toString());
	}
	public void updateIsParticipant(User user, Event event, String status) {
		StringBuilder values = new StringBuilder();
		// TODO
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'").append(AND);
		condition.append("eventID=").append(event.getId());
		updateObject(TABLE_IS_PARTICIPANT, values.toString(), condition.toString());
	}
	public void deleteIsParticipant(User user, Event event) {
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'").append(AND);
		condition.append("eventID=").append(event.getId());
		deleteObject(TABLE_IS_PARTICIPANT, condition.toString());
	}

	public void insertNotificationTo(User user, Notification notification) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(notification.getId()).append(" ");
		insertObject(TABLE_NOTIFICATION_TO, FIELDS_NOTIFICATION_TO, values.toString());
	}
	// Should not have update
	// Should not have delete (delete the notification)

	public void insertNotificationForEvent(Notification notification, Event event) {
		StringBuilder values = new StringBuilder();
		values.append(notification.getId()).append(", ");
		values.append(event.getId()).append(" ");
		insertObject(TABLE_NOTIFICATION_FOR_EVENT, FIELDS_NOTIFICATION_FOR_EVENT, values.toString());
	}
	// Should not have update
	// Should not have delete (delete the notification)

	public void insertReservedRoom(Event event, Room room) {
		StringBuilder values = new StringBuilder();
		values.append(event.getId()).append(", ");
		values.append(room.getId()).append(" ");
		insertObject(TABLE_RESERVED_ROOM, FIELDS_RESERVED_ROOM, values.toString());
	}
	// Should not have update
	public void deleteReservedRoom(Event event, Room room) {
		StringBuilder condition = new StringBuilder();
		condition.append("eventID=").append(event.getId()).append(AND);
		condition.append("roomID=").append(room.getId());
		deleteObject(TABLE_RESERVED_ROOM, condition.toString());
	}


	private int insertObject(String tableName, String fields, String values) {
		String updateString = String.format(INSERT_INTO_VALUES, tableName, fields, values);
		int id = 0;
		if (debugging) {
			System.out.println(updateString);
		} else {
			id = dbComm.update(updateString);
		}
		return id;
	}
	private void updateObject(String tableName, String values, String condition) {
		String updateString = String.format(UPDATE_SET_WHERE, tableName, values, condition);
		if (debugging) {
			System.out.println(updateString);
		} else {
			dbComm.update(updateString);
		}
	}
	private void deleteObject(String tableName, String condition) {
		String updateString = String.format(DELETE_FROM_WHERE, tableName, condition);
		if (debugging) {
			System.out.println(updateString);
		} else {
			dbComm.update(updateString);
		}
	}
	

	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Update update = new Update(TestObjects.getUser00(), dbComm);
		update.debugging = true;

		Alarm alarm = TestObjects.getAlarm00();
		update.insertAlarm(alarm);
		update.updateAlarm(alarm);
		update.deleteAlarm(alarm);
		System.out.println();
		
		Event event = TestObjects.getMeeting00();
		update.insertEvent(event);
		update.updateEvent(event);
		update.deleteEvent(event);
		System.out.println();
		
		Group group = TestObjects.getGroup00();
		update.insertGroup(group);
		update.updateGroup(group);
		update.deleteGroup(group);
		System.out.println();
		
	}

}
