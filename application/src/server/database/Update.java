package server.database;

import java.util.ArrayList;
import java.util.Properties;

import data.*;
import data.communication.ChangeData;

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

	private DatabaseCommunication dbComm;
	private boolean debugging = false;

	public Update(DatabaseCommunication dbComm) {
		this.dbComm = dbComm;
	}

	public int insertAlarm(Alarm alarm) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(alarm.getTimeBefore()).append("'").append(", ");
		values.append("'").append(alarm.getMessage()).append("'").append(", ");
		values.append("'").append(alarm.getOwner().getUsername()).append("'").append(", ");
		values.append(alarm.getEvent().getId()).append(" ");
		int id = insertObject(TABLE_ALARM, FIELDS_ALARM, values.toString(), "");
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
		String identifier = alarm.getOwner().getUsername() + ":" + alarm.getEvent().getId();
		updateObject(TABLE_ALARM, values.toString(), condition.toString(), identifier);
	}
	public void deleteAlarm(Alarm alarm) {
		StringBuilder condition = new StringBuilder();
		condition.append("username=").append("'").append(alarm.getOwner().getUsername()).append("'").append(AND);
		condition.append("eventID=").append(alarm.getEvent().getId()).append(" ");
		String identifier = alarm.getOwner().getUsername() + ":" + alarm.getEvent().getId();
		deleteObject(TABLE_ALARM, condition.toString(), identifier);
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
		int id = insertObject(TABLE_EVENT, FIELDS_EVENT, values.toString(), "");
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
		String[] fields = TableFields.EVENT.getFields();
		values.append(fields[0]).append("=").append(event.getId()).append(", ");
		values.append(fields[1]).append("='").append(event.getStartDateTime()).append("', ");
		values.append(fields[2]).append("='").append(event.getEndDateTime()).append("', ");
		values.append(fields[3]).append("='").append(event.getLocation()).append("', ");
		values.append(fields[4]).append("='").append(event.getDescription()).append("', ");
		values.append(fields[5]).append("=").append(event instanceof Meeting ? BIT_TRUE : BIT_FALSE).append(" ");
		StringBuilder condition = new StringBuilder();
		condition.append("eventID=").append(event.getId());
		updateObject(TABLE_EVENT, values.toString(), condition.toString(), ""+event.getId());
		// todo relations? (room reservation etc.)
	}
	public void deleteEvent(Event event) {
		StringBuilder condition = new StringBuilder();
		condition.append("eventID=").append(event.getId());
		deleteObject(TABLE_EVENT, condition.toString(), ""+event.getId());
	}

	public void insertGroup(Group group) {
		StringBuilder values = new StringBuilder();
		values.append(group.getId()).append(", ");
		values.append("'").append(group.getName()).append("'").append(", ");
		values.append("'").append(group.getDescription()).append("'").append(" ");
		int id = insertObject(TABLE_GROUPS, FIELDS_GROUPS, values.toString(), "");
		group.setId(id);
		for (User user : group.getMembers()) {
			insertIsMemberOf(user, group);
		}
	}
	public void updateGroup(Group group) {
		StringBuilder values = new StringBuilder();
		String[] fields = TableFields.GROUPS.getFields();
		values.append(fields[0]).append("=").append(group.getId()).append(", ");
		values.append(fields[1]).append("='").append(group.getName()).append("', ");
		values.append(fields[2]).append("='").append(group.getDescription()).append("' ");
		StringBuilder condition = new StringBuilder();
		condition.append("groupID=").append(group.getId());
		updateObject(TABLE_GROUPS, values.toString(), condition.toString(), ""+group.getId());
		// todo relations? (members etc.)
	}
	public void deleteGroup(Group group) {
		StringBuilder condition = new StringBuilder();
		condition.append("groupID=").append(group.getId());
		deleteObject(TABLE_GROUPS, condition.toString(), ""+group.getId());
	}

	public void insertNotification(Notification notification) {
		StringBuilder values = new StringBuilder();
		values.append(notification.getId()).append(", ");
		values.append("'").append(notification.getMessage()).append("'").append(" ");
		int id = insertObject(TABLE_NOTIFICATION, FIELDS_NOTIFICATION, values.toString(), "");
		notification.setId(id);
		insertNotificationTo(notification.getRecipient(), notification);
		if (notification.getEvent() != null) {
			insertNotificationForEvent(notification, notification.getEvent());
		}
	}
	public void updateNotification(Notification notification) {
		StringBuilder values = new StringBuilder();
		String[] fields = TableFields.NOTIFICATION.getFields();
		values.append(fields[0]).append("=").append(notification.getId()).append(", ");
		values.append(fields[1]).append("='").append(notification.getMessage()).append("' ");
		StringBuilder condition = new StringBuilder();
		condition.append("notificationID=").append(notification.getId());
		updateObject(TABLE_NOTIFICATION, values.toString(), condition.toString(), ""+notification.getId());
	}
	public void deleteNotification(Notification notification) {
		StringBuilder condition = new StringBuilder();
		condition.append("notificationID=").append(notification.getId());
		deleteObject(TABLE_NOTIFICATION, condition.toString(), ""+notification.getId());
	}

	public void insertRoom(Room room) {
		StringBuilder values = new StringBuilder();
		values.append(room.getId()).append(", ");
		values.append(room.getSize()).append(", ");
		values.append("'").append(room.getDescription()).append("'").append(" ");
		insertObject(TABLE_ROOM, FIELDS_ROOM, values.toString(), "");
	}
	public void updateRoom(Room room) {
		StringBuilder values = new StringBuilder();
		String[] fields = TableFields.ROOM.getFields();
		values.append(fields[0]).append("=").append(room.getId()).append(", ");
		values.append(fields[1]).append("=").append(room.getSize()).append(", ");
		values.append(fields[2]).append("='").append(room.getSize()).append("' ");
		StringBuilder condition = new StringBuilder();
		condition.append("roomID=").append(room.getId());
		updateObject(TABLE_ROOM, values.toString(), condition.toString(), ""+room.getId());
	}
	public void deleteRoom(Room room) {
		StringBuilder condition = new StringBuilder();
		condition.append("roomID=").append(room.getId());
		deleteObject(TABLE_ROOM, condition.toString(), ""+room.getId());
	}

	public void insertUser(User user) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append("'").append(user.getPassword()).append("'").append(", ");
		values.append("'").append(user.getName()).append("'").append(", ");
		values.append("'").append(user.getType()).append("'").append(" ");
		insertObject(TABLE_USER, FIELDS_USER, values.toString(), user.getUsername());
	}
	public void updateUser(User user) {
		StringBuilder values = new StringBuilder();
		String[] fields = TableFields.USER.getFields();
		values.append(fields[0]).append("='").append(user.getUsername()).append("', ");
		values.append(fields[1]).append("='").append(user.getPassword()).append("', ");
		values.append(fields[2]).append("='").append(user.getName()).append("', ");
		values.append(fields[3]).append("='").append(user.getType()).append("' ");
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'");
		updateObject(TABLE_USER, values.toString(), condition.toString(), user.getUsername());
	}
	public void deleteUser(User user) {
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'");
		deleteObject(TABLE_USER, condition.toString(), user.getUsername());
	}

	public void insertIsMemberOf(User user, Group group) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(group.getId()).append(" ");
		insertObject(TABLE_IS_MEMBER_OF, FIELDS_IS_MEMBER_OF, values.toString(), user.getUsername() + ":" + group.getId());
	}
	// Should not have update
	public void deleteIsMemberOf(User user, Group group) {
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'").append(AND);
		condition.append("groupID=").append(group.getId());
		deleteObject(TABLE_IS_MEMBER_OF, condition.toString(), user.getUsername() + ":" + group.getId());
	}

	public void insertIsOwner(User user, Event event) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(event.getId()).append(" ");
		insertObject(TABLE_IS_OWNER, FIELDS_IS_OWNER, values.toString(), user.getUsername() + ":" + event.getId());
	}
	// Do we want update?
	// Should not have delete (delete the event)

	public void insertIsParticipant(User user, Event event, String status) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(event.getId()).append(", ");
		values.append("'").append(status).append("'").append(" ");
		insertObject(TABLE_IS_PARTICIPANT, FIELDS_IS_PARTICIPANT, values.toString(), user.getUsername() + ":" + event.getId());
	}
	public void updateIsParticipant(User user, Event event, String status) {
		StringBuilder values = new StringBuilder();
		String[] fields = TableFields.IS_PARTICIPANT.getFields();
		values.append(fields[0]).append("='").append(user.getUsername()).append("', ");
		values.append(fields[1]).append("=").append(event.getId()).append(", ");
		values.append(fields[2]).append("=").append(status).append(" ");
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'").append(AND);
		condition.append("eventID=").append(event.getId());
		updateObject(TABLE_IS_PARTICIPANT, values.toString(), condition.toString(), user.getUsername() + ":" + event.getId());
	}
	public void deleteIsParticipant(User user, Event event) {
		StringBuilder condition = new StringBuilder();
		condition.append("username='").append(user.getUsername()).append("'").append(AND);
		condition.append("eventID=").append(event.getId());
		deleteObject(TABLE_IS_PARTICIPANT, condition.toString(), user.getUsername() + ":" + event.getId());
	}

	public void insertNotificationTo(User user, Notification notification) {
		StringBuilder values = new StringBuilder();
		values.append("'").append(user.getUsername()).append("'").append(", ");
		values.append(notification.getId()).append(" ");
		insertObject(TABLE_NOTIFICATION_TO, FIELDS_NOTIFICATION_TO, values.toString(), user.getUsername() + ":" + notification.getId());
	}
	// Should not have update
	// Should not have delete (delete the notification)

	public void insertNotificationForEvent(Notification notification, Event event) {
		StringBuilder values = new StringBuilder();
		values.append(notification.getId()).append(", ");
		values.append(event.getId()).append(" ");
		insertObject(TABLE_NOTIFICATION_FOR_EVENT, FIELDS_NOTIFICATION_FOR_EVENT, values.toString(), notification.getId() + ":" + event.getId());
	}
	// Should not have update
	// Should not have delete (delete the notification)

	public void insertReservedRoom(Event event, Room room) {
		StringBuilder values = new StringBuilder();
		values.append(event.getId()).append(", ");
		values.append(room.getId()).append(" ");
		insertObject(TABLE_RESERVED_ROOM, FIELDS_RESERVED_ROOM, values.toString(), event.getId() + ":" + room.getId());
	}
	// Should not have update
	public void deleteReservedRoom(Event event, Room room) {
		StringBuilder condition = new StringBuilder();
		condition.append("eventID=").append(event.getId()).append(AND);
		condition.append("roomID=").append(room.getId());
		deleteObject(TABLE_RESERVED_ROOM, condition.toString(), event.getId() + ":" + room.getId());
	}


	private int insertObject(String tableName, String fields, String values, String identifier) {
		String updateString = String.format(INSERT_INTO_VALUES, tableName, fields, values);
		int id = 0;
		if (debugging) {
			System.out.println(updateString);
		} else {
			id = dbComm.update(updateString);
		}
		long newVersion = getVersion() + 1;
		if (identifier.length() > 0) {
			dbComm.update("INSERT changes SET versionNumber=" + newVersion + ", tableName='" + tableName + "', identifiers='" + identifier + "'");
		} else {
			dbComm.update("INSERT changes SET versionNumber=" + newVersion + ", tableName='" + tableName + "', identifiers='" + id + "'");
		}
		return id;
	}
	private void updateObject(String tableName, String values, String condition, String identifier) {
		String updateString = String.format(UPDATE_SET_WHERE, tableName, values, condition);
		if (debugging) {
			System.out.println(updateString);
		} else {
			dbComm.update(updateString);
		}
		long newVersion = getVersion() + 1;
		dbComm.update("INSERT changes SET versionNumber=" + newVersion + ", tableName='" + tableName + "', identifiers='" + identifier + "'");
	}
	private void deleteObject(String tableName, String condition, String identifier) {
		String updateString = String.format(DELETE_FROM_WHERE, tableName, condition);
		if (debugging) {
			System.out.println(updateString);
		} else {
			dbComm.update(updateString);
		}
		long newVersion = getVersion() + 1;
		dbComm.update("INSERT changes SET versionNumber=" + newVersion + ", tableName='" + tableName + "', identifiers='" + identifier + "'");
	}
	
	public void startVersion() {
		dbComm.update("INSERT changes SET versionNumber=0, tableName='dummy', identifiers='dummy'");
	}
	
	public long getVersion() {
		ArrayList<Properties> pl = dbComm.query("SELECT MAX(versionNumber) AS versionNumber FROM changes");
		if (pl.size() > 0) {
			Properties p = pl.get(0);
			String property = p.getProperty("versionNumber");
			System.out.println(property);
			return Long.parseLong(property);
		} else {
			return -1;
		}
	}
	
	public ChangeData getChanges(long versionNumber) { // todo make sure this actually does what it is supposed to do
		ChangeData cd = new ChangeData();
		long currentVersion = getVersion();
		cd.setVersionNumber(currentVersion);
		ArrayList<Properties> pl = dbComm.query("" +
				"SELECT versionNumber, tableName, identifiers " +
				"FROM changes " +
				"WHERE versionNumber > " + versionNumber + " AND versionNumber < " + currentVersion + " " + 
				"ORDER BY versionNumber ASC");
		ArrayList<String> tableNames = new ArrayList<String>();
		ArrayList<String> identifiers = new ArrayList<String>();
		for (Properties p : pl) {
			tableNames.add(p.getProperty("tableName"));
			identifiers.add(p.getProperty("identifiers"));
		}
		System.out.println("" +
				" pl: " + pl.size() + 
				" tn: " + tableNames.size() + 
				" id: " + identifiers.size());
		cd.setTableNames(tableNames.toArray(new String[0]));
		cd.setIdentifiers(identifiers.toArray(new String[0]));
		return cd;
	}
	
	public static void main(String[] args) {
		DatabaseConnection dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/calendarDatabase", "root", "skip".toCharArray());
		DatabaseCommunication dbComm = new DatabaseCommunication(dbConn);
		Update update = new Update(dbComm);
		update.debugging = true;

	}

}
